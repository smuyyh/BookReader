package com.justwayward.reader.view.chmview;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitsInputStream extends FilterInputStream {

    int bitbuf;
    int bitsLeft;

    static final int BUFFER_BITS = 32;

    static final int[] UNSIGNED_MASK = new int[]{
            0, 0x01, 0x03, 0x07, 0x0f, 0x01f, 0x3f, 0x7f, 0xff,
            0x1ff, 0x3ff, 0x7ff, 0xfff, 0x1fff, 0x3fff, 0x7fff, 0xffff,
    };

    public BitsInputStream(InputStream in) {
        super(in);
    }

    /**
     * Read 32-bit little endian int instead of a byte!
     */
    public int read32LE() throws IOException {
        return in.read() + (in.read() << 8) + (in.read() << 16) + (in.read() << 24);
    }

    /**
     * flush n bytes, and reset bitbuf, bitsLeft
     * often used to align the byte array
     * NOTE: n may be negative integer, e.g. -2
     */
    public void skip(int n) throws IOException {
        bitbuf = 0;
        bitsLeft = 0;
        super.skip(n);
    }

    /**
     * Make sure there are at least n (<=16) bits in the buffer,
     * otherwise, read a 16-bit little-endian word from the byte array.
     * returns bitsLeft;
     */
    public int ensure(int n) throws IOException {
        while (bitsLeft < n) {
            // read in two bytes
            int b1 = in.read();
            int b2 = in.read();
            if ((b1 | b2) < 0)
                break;

            bitbuf |= (b1 | (b2 << 8)) << (BUFFER_BITS - 16 - bitsLeft);
            bitsLeft += 16;
        }
        return bitsLeft;
    }

    /**
     * Read no more than 16 bits big endian, bits are arranged as
     * <pre>
     * 00000000 00000000 00000000 00000000, bitsLeft = 0;
     * ensure(1);
     * aaaaaaaa 00000000 00000000 00000000, bitsLeft = 8;
     * read(3) = 00000aaa;
     * aaaaa000 00000000 00000000 00000000, bitsLeft = 5;
     * ensure(16);
     * aaaaabbb bbbbbccc ccccc000 00000000, bitsLeft = 21;
     * read(8) = aaaaabbb;
     * bbbbbccc ccccc000 00000000 00000000, bitsLeft = 13;
     * </pre>
     */
    public int readLE(int n) throws IOException {
        int ret = peek(n);
        bitbuf <<= n;
        bitsLeft -= n;
        return ret;
    }

    /**
     * Peek n bits, may raise EOFException.
     */
    public int peek(int n) throws IOException {
        if (ensure(n) < n)
            throw new EOFException();
        return ((bitbuf >> (BUFFER_BITS - n))) & UNSIGNED_MASK[n];
    }

    /**
     * Peek no more than n bits, so there is no EOFException.
     */
    public int peekUnder(int n) throws IOException {
        ensure(n);
        return ((bitbuf >> (BUFFER_BITS - n))) & UNSIGNED_MASK[n];
    }

    public void readFully(byte b[]) throws IOException {
        readFully(b, 0, b.length);
    }

    public void readFully(byte b[], int off, int len)
            throws IOException {
        for (int n = 0; n < len; ) {
            int count = read(b, off + n, len - n);
            if (count < 0)
                throw new EOFException();
            n += count;
        }
    }

    /**
     * return binary string of bitbuf
     */
    public String toString() {
        String s = "00000000000000000000000000000000" + Long.toBinaryString(bitbuf);
        s = s.substring(s.length() - 32);
        return s.substring(0, 8) + " " + s.substring(8, 16)
                + " " + s.substring(16, 24) + " " + s.substring(24, 32)
                + " " + bitsLeft;
    }
}