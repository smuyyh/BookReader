package com.justwayward.reader.view.chmview;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LEInputStream extends FilterInputStream {

    public LEInputStream(InputStream in) {
        super(in);
    }

    /**
     * 16-bit little endian unsinged integer
     */
    public int read16() throws IOException {
        int b1 = read();
        int b2 = read();
        if ((b1 | b2) < 0)
            throw new EOFException();
        return (b1 << 0) + (b2 << 8);
    }

    /**
     * 32-bit little endian integer
     */
    public int read32() throws IOException {
        int b1 = read();
        int b2 = read();
        int b3 = read();
        int b4 = read();
        if ((b1 | b2 | b3 | b4) < 0)
            throw new EOFException();
        return ((b1 << 0) + (b2 << 8) + (b3 << 16) + (b4 << 24));
    }

    /**
     * Encoded little endian integer
     */
    public int readENC() throws IOException {
        int r = 0;
        for (; ; ) {
            int b = read();
            if (b < 0) throw new EOFException();
            r = (r << 7) + (b & 0x7f);
            if ((b & 0x80) == 0)
                return r;
        }
    }

    /**
     * 64-bit little endian integer
     */
    public long read64() throws IOException {
        int b1 = read();
        int b2 = read();
        int b3 = read();
        int b4 = read();
        int b5 = read();
        int b6 = read();
        int b7 = read();
        int b8 = read();
        if ((b1 | b2 | b3 | b4 | b5 | b6 | b7 | b8) < 0)
            throw new EOFException();
        return ((b1 << 0) + (b2 << 8) + (b3 << 16) + (b4 << 24)
                + (b5 << 32) + (b6 << 40) + (b7 << 48) + (b8 << 56));
    }

    public String readUTF8(int len) throws IOException {
        byte[] buf = new byte[len];
        readFully(buf);
        return new String(buf, 0, len, "UTF-8");
    }

    public String readUTF16(int len) throws IOException {
        byte[] buf = new byte[len];
        readFully(buf);
        return new String(buf, 0, len, "UTF-16LE");
    }

    public String readGUID() throws IOException {
        return toHexString(read32(), 8) +
                "-" + toHexString(read16(), 4) + "-" + toHexString(read16(), 4) +
                "-" + toHexString(read(), 2) + toHexString(read(), 2) +
                "-" + toHexString(read(), 2) + toHexString(read(), 2) +
                "-" + toHexString(read(), 2) + toHexString(read(), 2) +
                "-" + toHexString(read(), 2) + toHexString(read(), 2);
    }

    private String toHexString(int v, int len) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < len; i++)
            b.append('0');
        b.append(Long.toHexString(v));
        return b.substring(b.length() - len).toUpperCase();
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
}
