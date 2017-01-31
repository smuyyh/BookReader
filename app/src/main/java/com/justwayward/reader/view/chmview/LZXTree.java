package com.justwayward.reader.view.chmview;

import java.io.IOException;

public class LZXTree {

    public static final int LZX_LENTABLE_SAFETY = 64; // allow length table decoding overruns
    public static final int LZX_PRETREE_NUM_ELEMENTS = 20;

    int bits;
    int max_symbol;

    int[] symbols;
    byte[] lens;

    LZXTree(int bits, int max) {
        this.bits = bits;
        this.max_symbol = max;

        symbols = new int[(1 << bits) + (max << 1)];
        lens = new byte[max + LZX_LENTABLE_SAFETY];
    }

    /**
     * This function was coded by David Tritscher. It builds a fast huffman
     * decoding table out of just a canonical huffman code lengths table.
     */
    void makeSymbolTable() throws DataFormatException {
        int bit_num = 1;
        int pos = 0; // the current position in the decode table
        int table_mask = 1 << bits;
        int bit_mask = table_mask >> 1; // don't do 0 length codes
        int next_symbol = bit_mask; // base of allocation for long codes

        // fill entries for codes short enough for a direct mapping
        while (bit_num <= bits) {
            for (int symbol = 0; symbol < max_symbol; symbol++) {
                if (lens[symbol] == bit_num) {
                    int leaf = pos;

                    if ((pos += bit_mask) > table_mask)    // ensure capacity
                        throw new DataFormatException("symbol table overruns");
                    // fill all possible lookups of this symbol with the symbol itself
                    while (leaf < pos) symbols[leaf++] = symbol;
                }
            }
            bit_mask >>= 1;
            bit_num++;
        }

        // if there are any codes longer than table.bits
        if (pos != table_mask) {
            // clear the remainder of the table
            for (int i = pos; i < table_mask; i++) symbols[i] = 0;

            // give ourselves room for codes to grow by up to 16 more bits
            pos <<= 16;
            table_mask <<= 16;
            bit_mask = 1 << 15;

            while (bit_num <= 16) {
                for (int symbol = 0; symbol < max_symbol; symbol++) {
                    if (lens[symbol] == bit_num) {
                        int leaf = pos >> 16;
                        for (int fill = 0; fill < bit_num - bits; fill++) {
                            // if this path hasn't been taken yet, 'allocate' two entries
                            if (symbols[leaf] == 0) {
                                symbols[next_symbol << 1] = 0;
                                symbols[(next_symbol << 1) + 1] = 0;
                                symbols[leaf] = (next_symbol++);
                            }
                            // follow the path and select either left or right for next bit
                            leaf = symbols[leaf] << 1;
                            if (((pos >> (15 - fill)) & 1) > 0) // odd
                                leaf++;
                        }
                        symbols[leaf] = symbol;

                        if ((pos += bit_mask) > table_mask)
                            throw new DataFormatException("symbol table overflow");
                    }
                }
                bit_mask >>= 1;
                bit_num++;
            }
        }

        // full table?
        if (pos == table_mask)
            return;

        // either erroneous table, or all elements are 0 - let's find out.
        for (short sym = 0; sym < max_symbol; sym++)
            if (lens[sym] != 0)
                throw new DataFormatException("erroneous symbol table");
    }

    /**
     * reads in code lengths for symbols
     * first to last in the given table. The code lengths are stored in their
     * own special LZX way.
     */
    void readLengthTable(BitsInputStream bin, int first, int last) throws DataFormatException, IOException {
        LZXTree preTree = new LZXTree(6, LZX_PRETREE_NUM_ELEMENTS);
        for (int i = 0; i < preTree.max_symbol; i++)
            preTree.lens[i] = (byte) bin.readLE(4);
        preTree.makeSymbolTable();

        for (int pos = first; pos < last; ) {
            int symbol = preTree.readHuffmanSymbol(bin);
            if (symbol == 0x11) {
                int pos2 = pos + bin.readLE(4) + 4;
                while (pos < pos2) lens[pos++] = (byte) 0;
            } else if (symbol == 0x12) {
                int pos2 = pos + bin.readLE(5) + 20;
                while (pos < pos2) lens[pos++] = (byte) 0;
            } else if (symbol == 0x13) {
                int pos2 = pos + bin.readLE(1) + 4;
                symbol = lens[pos] - preTree.readHuffmanSymbol(bin);
                if (symbol < 0)
                    symbol += 0x11;
                while (pos < pos2) lens[pos++] = (byte) symbol;
            } else {
                symbol = lens[pos] - symbol;
                if (symbol < 0)
                    symbol += 0x11;
                lens[pos++] = (byte) symbol;
            }
        }
    }

    /**
     * decodes one huffman symbol from the bitstream using the
     * stated table and return it.
     * @throws IOException
     */
    int readHuffmanSymbol(BitsInputStream bin) throws IOException {
        int next = bin.peekUnder(16);

		/* TODO: it's very strange that bin.peek(bits) will raise EOFException,
		 * we have to use peekUnder(bits) here, but how should it happen like this?
		 */
        int symbol = symbols[bin.peekUnder(bits)];
        if (symbol >= max_symbol) {
            int j = 1 << (16 - bits);
            do {
                j >>= 1;
                symbol <<= 1;
                symbol |= (next & j) > 0 ? 1 : 0;
                symbol = symbols[symbol];
            } while (symbol >= max_symbol);
        }
        bin.readLE(lens[symbol]);
        return symbol;
    }

    public void clear() {
        for (int i = 0; i < lens.length; i++)
            lens[i] = 0;
    }
}