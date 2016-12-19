package com.justwayward.reader.view.chmview;

import com.justwayward.reader.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;

class Inflater {

    // some constants defined by the LZX specification
    public static final int LZX_MIN_MATCH = 2;
    public static final int LZX_MAX_MATCH = 257;
    public static final int LZX_NUM_CHARS = 256;

    public static final int LZX_BLOCKTYPE_VERBATIM = 1;
    public static final int LZX_BLOCKTYPE_ALIGNED = 2;
    public static final int LZX_BLOCKTYPE_UNCOMPRESSED = 3;

    public static final int LZX_ALIGNED_NUM_ELEMENTS = 8; // aligned offset tree #elements
    public static final int LZX_NUM_PRIMARY_LENGTHS = 7; // this one missing from spec!
    public static final int LZX_NUM_SECONDARY_LENGTHS = 249; // length tree #elements

    /*
     * LZX uses what it calls 'position slots' to represent match offsets.
     * What this means is that a small 'position slot' number and a small
     * offset from that slot are encoded instead of one large offset for
     * every match.
     * - position_base is an index to the position slot bases.
     */
    private static final int[] POSITION_BASE = new int[]{
            0, 1, 2, 3, 4, 6, 8, 12, 16, 24, 32, 48, 64, 96, 128, 192,
            256, 384, 512, 768, 1024, 1536, 2048, 3072, 4096, 6144, 8192, 12288, 16384, 24576, 32768, 49152,
            65536, 98304, 131072, 196608, 262144, 393216, 524288, 655360, 786432, 917504, 1048576, 1179648, 1310720, 1441792, 1572864, 1703936,
            1835008, 1966080, 2097152
    };

    /*
      * - extra_bits states how many bits of offset-from-base data is needed.
     */
    private static final byte[] EXTRA_BITS = new byte[]{
            0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6,
            7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14,
            15, 15, 16, 16, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
            17, 17, 17
    };

    private byte[] window;            // the actual decoding window
    private int window_posn;        // current offset within the window
    private int r0, r1, r2;        // for the LRU offset system
    private int main_elements;        // number of main tree elements
    private boolean header_read;    // have we started decoding at all yet?
    private int block_type;            // type of this block
    private int block_length;    // uncompressed length of this block
    private int block_remaining;    // uncompressed bytes still left to decode
    private int frames_read;        // the number of CFDATA blocks processed

    private int intel_filesize;    // magic header value used for transform
    private int intel_curpos;        // current offset in transform space
    private boolean intel_started;        // have we seen any translatable data yet?

    // LZX huffman defines: tweak tablebits as desired
    LZXTree mainTree = new LZXTree(12, LZX_NUM_CHARS + 50 * 8);
    LZXTree lengthTree = new LZXTree(12, LZX_NUM_SECONDARY_LENGTHS + 1);
    LZXTree alignedTree = new LZXTree(7, LZX_ALIGNED_NUM_ELEMENTS);

    /**
     * Don't forget reset at reset intervals
     */
    public Inflater(int windowSize) {
        if (windowSize < (1 << 15) || windowSize > (1 << 21)) {
            throw new IllegalArgumentException("Unsupported window size " + windowSize);
        }

        window = new byte[windowSize];

        int positionSlotNo = 0;
        while (windowSize > 1) {
            windowSize >>= 1;
            positionSlotNo += 2;
        }
        if (positionSlotNo == 40) {
            positionSlotNo = 42;
        } else if (positionSlotNo == 42) {
            positionSlotNo = 50;
        }

        main_elements = LZX_NUM_CHARS + (positionSlotNo << 3);
    }

    public int inflate(boolean reset, InputStream in, byte[] buf) throws DataFormatException, IOException {
        return inflate(reset, in, buf, 0, buf.length);
    }

    /**
     * Uncompresses bytes into specified buffer. Returns actual number of bytes
     * uncompressed. A return value of 0 indicates that needsInput() or
     * needsDictionary() should be called in order to determine if more input
     * data or a preset dictionary is required. In the later case, getAdler()
     * can be used to get the Adler-32 value of the dictionary required.
     */
    public int inflate(boolean reset, InputStream in, byte[] buf, int off, int len) throws DataFormatException, IOException {

        if (reset) {    // reset at reset intervals
            r0 = r1 = r2 = 1;
            header_read = false;
            frames_read = 0;
            block_remaining = 0;
            block_type = -1;    // invalid
            intel_curpos = 0;
            intel_started = false;
            window_posn = 0;

            mainTree.clear();
            lengthTree.clear();
        }

        BitsInputStream bin = new BitsInputStream(in);

        if (!header_read) {
            if (bin.readLE(1) > 0) {
                intel_filesize = (bin.readLE(16) << 16) | bin.readLE(16);    // = 0 if not encoded
                LogUtils.i("Intel filesize = " + intel_filesize);
            }
            header_read = true;
        }

        int togo = len;
        while (togo > 0) {
            if (block_remaining == 0) {
                if (block_type == LZX_BLOCKTYPE_UNCOMPRESSED) {
                    if ((block_length & 1) > 0) // odd
                    {
                        bin.skip(1); // realign to word
                    }
                }
                block_type = bin.readLE(3);
                block_remaining = block_length = bin.readLE(16) << 8 | bin.readLE(8);
                LogUtils.i("Block type = " + block_type + ", length = " + block_length);

                switch (block_type) {
                    case LZX_BLOCKTYPE_ALIGNED: {
                        for (int i = 0; i < alignedTree.max_symbol; i++) {
                            alignedTree.lens[i] = (byte) bin.readLE(3);
                        }
                        alignedTree.makeSymbolTable();
                        // continue to next case ...
                    }
                    case LZX_BLOCKTYPE_VERBATIM: {
                        mainTree.readLengthTable(bin, 0, LZX_NUM_CHARS);
                        mainTree.readLengthTable(bin, LZX_NUM_CHARS, main_elements);
                        mainTree.makeSymbolTable();
                        if (mainTree.lens[0xE8] != 0) // Intel E8 encoding?
                        {
                            intel_started = true;
                        }

                        lengthTree.readLengthTable(bin, 0, LZX_NUM_SECONDARY_LENGTHS);
                        lengthTree.makeSymbolTable();
                        break;
                    }
                    case LZX_BLOCKTYPE_UNCOMPRESSED: {
                        LogUtils.w("LZXC meet LZX_BLOCKTYPE_UNCOMPRESSED");
                        intel_started = true; // because we can't assume otherwise

                        if (bin.ensure(16) > 16) {// get up to 16 pad bits into the buffer
                            bin.bitbuf = 0;    // TODO really want to do this?
                            bin.bitsLeft = 0;
                            r0 = bin.read() + bin.read() << 8;//skip(-2); 	// and align the bitstream! TODO what happens to the bitbuf/bitsLeft?
                        } else {
                            r0 = bin.read32LE();
                        }
                        r1 = bin.read32LE();
                        r2 = bin.read32LE();
                        break;
                    }
                    default:
                        throw new DataFormatException("Unexpected block type " + block_type);
                }
            }

            /* buffer exhaustive check:
             * it's possible to have a file where the next run is less than
			 * 16 bits in size. In this case, the READ_HUFFSYM() macro used
			 * in building the tables will exhaust the buffer, so we should
			 * allow for this, but not allow those accidentally read bits to
			 * be used (so we check that there are at least 16 bits
			 * remaining - in this boundary case they aren't really part of
			 * the compressed data)
			 * 	if (inpos > (endinpos+2) || bitsleft < 16) return DECR_ILLEGALDATA;
			 * @see BitsInputStream#peekUnder();
             */
            int this_run;
            while ((this_run = block_remaining) > 0 && togo > 0) {
                if (this_run > togo) {
                    this_run = togo;
                }
                togo -= this_run;
                block_remaining -= this_run;

                window_posn %= window.length;
                if (window_posn + this_run > window.length) {
                    LogUtils.w("runs can't straddle the window wraparound");
                }
                //throw new DataFormatException("runs can't straddle the window wraparound");

                if (block_type == LZX_BLOCKTYPE_UNCOMPRESSED) {
                    if (this_run > bin.available()) // make sure we can read
                    {
                        throw new DataFormatException("not enough data");
                    }
                    bin.readFully(window, window_posn, this_run);
                    window_posn += this_run;
                } else { // block_type == LZX_BLOCKTYPE_VERBATIM, LZX_BLOCKTYPE_ALIGNED
                    while (this_run > 0) {
                        int main_element = mainTree.readHuffmanSymbol(bin);

                        if (main_element < LZX_NUM_CHARS) { // literal: 0 to LZX_NUM_CHARS - 1
                            window[window_posn++] = (byte) main_element;
                            this_run--;
                        } else { // match: LZX_NUM_CHARS + ((slot<<3) | length_header (3 bits))
                            main_element -= LZX_NUM_CHARS;

                            int match_length = main_element & LZX_NUM_PRIMARY_LENGTHS;
                            if (match_length == LZX_NUM_PRIMARY_LENGTHS) {
                                match_length += lengthTree.readHuffmanSymbol(bin);
                            }
                            match_length += LZX_MIN_MATCH;

                            int match_offset = main_element >> 3;
                            if (match_offset > 2) {
                                // not repeated offset
                                if (block_type == LZX_BLOCKTYPE_VERBATIM) {
                                    if (match_offset != 3) {
                                        byte extra = EXTRA_BITS[match_offset];
                                        match_offset = POSITION_BASE[match_offset] - 2 + bin.readLE(extra);
                                    } else {
                                        match_offset = 1;
                                    }
                                } else if (block_type == LZX_BLOCKTYPE_ALIGNED) {
                                    byte extra = EXTRA_BITS[match_offset];
                                    match_offset = POSITION_BASE[match_offset] - 2;
                                    if (extra > 3) { // verbatim and aligned bits
                                        extra -= 3;
                                        match_offset += (bin.readLE(extra) << 3);
                                        match_offset += alignedTree.readHuffmanSymbol(bin);
                                    } else if (extra == 3) {  // aligned bits only
                                        match_offset += alignedTree.readHuffmanSymbol(bin);
                                    } else if (extra > 0) { // extra == 1, 2;  verbatim bits only
                                        match_offset += bin.readLE(extra);
                                    } else { // extra == 0
                                        match_offset = 1;
                                    }
                                } else {
                                    throw new DataFormatException("Unexpected block type " + block_type);
                                }

                                // update repeated offset LRU queue
                                r2 = r1;
                                r1 = r0;
                                r0 = match_offset;
                            } else if (match_offset == 0) {
                                match_offset = r0;
                            } else if (match_offset == 1) {
                                match_offset = r1;
                                r1 = r0;
                                r0 = match_offset;
                            } else { // match_offset == 2
                                match_offset = r2;
                                r2 = r0;
                                r0 = match_offset;
                            }
//							LogUtils.i("OFF " + match_offset + ": " + r0 + ", " + r1 + ", " + r2
//								+ ", left = " + bin.available() + ", bitbuf = " + Integer.toBinaryString(bin.bitbuf));
//							if ( r0 == 26 && r1 == 13 && r2 == 12) {
//								System.out.println("here");
//							}

                            int runsrc = 0; // move down
                            int rundest = window_posn;
                            this_run -= match_length;

                            // copy any wrapped around source data
                            if (window_posn >= match_offset) {
                                // no wrap
                                runsrc = rundest - match_offset;
                            } else { // wrap around
                                runsrc = rundest + (window.length - match_offset);
                                int copy_length = match_offset - window_posn;
                                if (copy_length < match_length) {
                                    match_length -= copy_length;
                                    window_posn += copy_length;
                                    while (copy_length-- > 0) {
                                        window[rundest++] = window[runsrc++];
                                    }
                                    runsrc = 0;
                                }
                            }
                            window_posn += match_length;

                            // copy match data - no worries about destination wraps
                            while (match_length-- > 0) {
                                window[rundest++] = window[runsrc++];
                            }
                        }
                    }
                }
            }
        }

        if (togo != 0) {
            throw new DataFormatException("should never happens");
        }

        System.arraycopy(window, (window_posn == 0 ? window.length : window_posn) - len, buf, off, len);

        // Intel E8 decoding
        if ((frames_read++ < 32768) && (intel_filesize != 0)) {
            LogUtils.w("LZX Intel E8 decoding: running un-tested code " + intel_filesize);
            if (len <= 6 || !intel_started) {
                intel_curpos += len;
            } else {
                int curpos = intel_curpos;
                intel_curpos += len;
                for (int i = off; i < off + len - 10; ) {
                    if (buf[i++] != 0xE8) {
                        curpos++;
                    } else {
                        int abs_off = (buf[i] & 0xff) | ((buf[i + 1] & 0xff) << 8)
                                | ((buf[i + 2] & 0xff) << 16) | ((buf[i + 3] & 0xff) << 24);
                        if ((abs_off >= -curpos) && (abs_off < intel_filesize)) {
                            int ref_off = (abs_off >= 0) ? abs_off - curpos : abs_off + intel_filesize;
                            buf[i] = (byte) ref_off;
                            buf[i + 1] = (byte) (ref_off >> 8);
                            buf[i + 2] = (byte) (ref_off >> 16);
                            buf[i + 3] = (byte) (ref_off >> 24);
                        }
                        i += 4;
                        curpos += 5;
                    }
                }
            }
        }
        return 0;
    }
}