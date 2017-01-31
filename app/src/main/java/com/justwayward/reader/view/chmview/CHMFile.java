package com.justwayward.reader.view.chmview;

import com.justwayward.reader.utils.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * CHM 文件结构
 * <p>
 * 1. Header
 * - 1.1 ITSF, 4字节
 * - 1.2 版本信息, 4字节
 * - 1.3 文件头总长度, 4字节
 * - 1.4 固定为1, 4字节
 * - 1.5 时间记录, 4字节
 * - 1.6 windows语言ID标识, 4字节
 * - 1.7 两个GUID, 16字节, 固定为{7C01FD10-7BAA-11D0-9E0C-00A0-C922-E6EC},{7C01FD11-7BAA-11D0-9E0C-00A0-C922-E6EC}
 * - 1.8 两项header section, 每项16字节, 记录着从文件头开始的偏移量和section的长度，各占8个字节
 * - - 1.8.1 header section 0
 * - - - 1.8.1.1 第一双字：0x01fe
 * - - - 1.8.1.2 第三双字为文件大小
 * - - - 1.8.1.3 共占5个双字，其余均为0
 * - - 1.8.2 header section 1
 * - - - 1.8.2.1 第一双字为ITSP
 * - - - 1.8.2.2 第二双字为版本号
 * - - - 1.8.2.3 第三双字为本section长度
 * - - - 1.8.2.4 第四双字为0x0a
 * - - - 1.8.2.5 第五双字值为0x1000，是目录块的大小
 * - - - 1.8.2.6 第六双字是quickref section的“密度”，一般是2
 * - - - 1.8.2.7 第七双字是索引树的深度，1表示没有索引，2表示有一层的PMGI数据块
 * - - - 1.8.2.8 第八双字表示根索引的块号，如果没有索引为-1
 * - - - 1.8.2.9 第九双字是第一个PMGL(listing)的块号
 * - - - 1.8.2.A 第十双字是最后一个PMGL的块号
 * - - - 1.8.2.B 第十一双字是-1
 * - - - 1.8.2.C 第十二双字是目录块的块数
 * - - - 1.8.2.D 第十三双字是windows语言ID标识
 * - 1.9 8个字节的信息，这些在版本2里是没有的
 * <p>
 * 2.
 */
public class CHMFile implements Closeable {

    public static final int CHM_HEADER_LENGTH = 0x60;

    public static final int CHM_DIRECTORY_HEADER_LENGTH = 0x54;

    // header info
    private int version;    // 3, 2
    private int timestamp;
    private int lang;    // Windows Language ID
    private long contentOffset;
    private long fileLength;
    private int chunkSize;
    private int quickRef;
    private int rootIndexChunkNo;
    private int firstPMGLChunkNo;
    private int lastPMGLChunkNo;
    private int totalChunks;

    private long chunkOffset;

    RandomAccessFile fileAccess;

    private Map<String, ListingEntry> entryCache = new TreeMap<String, ListingEntry>();

    // level 1 index, <filename, level 2 chunkNo>
    private List<Map<String, Integer>> indexTree = new ArrayList<Map<String, Integer>>();

    private List<String> resources;

    private String siteMap;

    private Section[] sections = new Section[]{new Section()};

    private String filepath;

    public CHMFile(String filepath) throws IOException, DataFormatException {
        int iTemp;
        fileAccess = new RandomAccessFile(this.filepath = filepath, "r");

        /**
         * Step 1. CHM header
         */
        LEInputStream in = new LEInputStream(createInputStream(0, CHM_HEADER_LENGTH));

        // ITSF
        if (!in.readUTF8(4).equals("ITSF")) {
            throw new DataFormatException("CHM file should start with \"ITSF\"");
        }

        // version
        if ((version = in.read32()) > 3) {
            LogUtils.w("CHM header version unexpected value " + version);
        }

        // header length
        int length = in.read32();

        // value
        iTemp = in.read32(); // -1

        // timestamp
        timestamp = in.read32(); // big-endian DWORD?
        LogUtils.i("CHM timestamp: " + timestamp);

        // windows language id
        lang = in.read32();
        LogUtils.i("CHM ITSF language: " + WindowsLanguageID.getLocale(lang));

        // GUID
        String strTmp = in.readGUID();    //.equals("7C01FD10-7BAA-11D0-9E0C-00A0-C922-E6EC");
        strTmp = in.readGUID();    //.equals("7C01FD11-7BAA-11D0-9E0C-00A0-C922-E6EC");

        // header section
        long off0 = in.read64();
        long len0 = in.read64();
        long off1 = in.read64();
        long len1 = in.read64();

        // if the header length is really 0x60, read the final QWORD, or the content should be immediate after header section 1
        contentOffset = (length >= CHM_HEADER_LENGTH) ? in.read64() : (off1 + len1);
        LogUtils.i("CHM content offset " + contentOffset);

        /* Step 1.1 (Optional)  CHM header section 0 */
        in = new LEInputStream(createInputStream(off0, (int) len0)); // len0 can't exceed 32-bit
        iTemp = in.read32(); // 0x01FE;
        iTemp = in.read32(); // 0;
        if ((fileLength = in.read64()) != fileAccess.length()) {
            LogUtils.w("CHM file may be corrupted, expect file length " + fileLength);
        }
        iTemp = in.read32(); // 0;
        iTemp = in.read32(); // 0;

        /**
         *  Step 1.2 CHM header section 1: directory index header
         */
        in = new LEInputStream(createInputStream(off1, CHM_DIRECTORY_HEADER_LENGTH));

        if (!in.readUTF8(4).equals("ITSP")) {
            throw new DataFormatException("CHM directory header should start with \"ITSP\"");
        }

        iTemp = in.read32(); // version
        chunkOffset = off1 + in.read32(); // = 0x54
        iTemp = in.read32(); // = 0x0a
        chunkSize = in.read32();    // 0x1000
        quickRef = 1 + (1 << in.read32());    // = 1 + (1 << quickRefDensity )
        for (int i = in.read32(); i > 1; i--) { // depth of index tree, 1: no index, 2: one level of PMGI chunks
            indexTree.add(new TreeMap<String, Integer>());
        }

        rootIndexChunkNo = in.read32();    // chunk number of root, -1: none
        firstPMGLChunkNo = in.read32();    // chunk number of first PMGL
        lastPMGLChunkNo = in.read32();     // chunk number of last PMGL

        iTemp = in.read32(); // = -1
        totalChunks = in.read32(); // chunk counts
        int lang2 = in.read32(); // language code
        LogUtils.i("CHM ITSP language " + WindowsLanguageID.getLocale(lang2));

        strTmp = in.readGUID(); //.equals("5D02926A-212E-11D0-9DF9-00A0-C922-E6EC"))
        iTemp = in.read32(); // = x54
        iTemp = in.read32(); // = -1
        iTemp = in.read32(); // = -1
        iTemp = in.read32(); // = -1

        if (chunkSize * totalChunks + CHM_DIRECTORY_HEADER_LENGTH != len1) {
            throw new DataFormatException("CHM directory list chunks size mismatch");
        }

        /**
         *  Step 2. CHM name list: content sections
         */
        in = new LEInputStream(
                getResourceAsStream("::DataSpace/NameList"));

        iTemp = in.read16(); // length in 16-bit-word, = in.length() / 2
        sections = new Section[in.read16()];
        for (int i = 0; i < sections.length; i++) {
            String name = in.readUTF16(in.read16() << 1);
            if ("Uncompressed".equals(name)) {
                sections[i] = new Section();
            } else if ("MSCompressed".equals(name)) {
                sections[i] = new LZXCSection();
            } else {
                throw new DataFormatException("Unknown content section " + name);
            }
            iTemp = in.read16(); // = null
        }
    }

    /**
     * Read len bytes from file beginning from offset. Since it's really a
     * ByteArrayInputStream, close() operation is optional
     */
    private synchronized InputStream createInputStream(long offset, int len) throws IOException {
        fileAccess.seek(offset);
        byte[] b = new byte[len]; // TODO performance?
        fileAccess.readFully(b);
        return new ByteArrayInputStream(b);
    }

    /**
     * Resovle entry by name, using cache and index
     */
    private ListingEntry resolveEntry(String name) throws IOException {
//        if (rootIndexChunkNo < 0 && resources == null) // no index
//        {
        list(); // force cache fill
        //}
        ListingEntry entry = entryCache.get(name);
        if (entry != null) {
            return entry;
        }

        //error
//        if (rootIndexChunkNo >= 0 && resources == null) {
//            entry = resolveIndexedEntry(name, rootIndexChunkNo, 0);
//        }
//
//        if (entry == null) {// ugly
//            entry = resolveIndexedEntry(name.toLowerCase(), rootIndexChunkNo, 0);
//            LogUtils.w("Resolved using lowercase name " + name);
//        }

        if (entry == null) {
            throw new FileNotFoundException(filepath + "#" + name);
        }

        return entry;
    }

    /**
     * listing chunks have filename/offset entries sorted by filename
     * alphabetically index chunks have filename/listingchunk# entries,
     * specifying the first filename of each listing chunk. NOTE: this code will
     * crack when there is no index at all (rootIndexChunkNo == -1), so at
     * processDirectoryIndex() method, we have already cached all resource
     * names. however, this code will still crack, when resolving a not-at-all
     * existing resource.
     */
    private synchronized ListingEntry resolveIndexedEntry(String name, int chunkNo, int level) throws IOException {

        if (chunkNo < 0) {
            throw new IllegalArgumentException("chunkNo < 0");
        }

        if (level < indexTree.size()) {    // no more than indexTreeDepth
            // process the index chunk
            Map<String, Integer> index = indexTree.get(level);

            if (index.isEmpty()) {    // load it from the file
                LEInputStream in = new LEInputStream(
                        createInputStream(chunkOffset + rootIndexChunkNo * chunkSize, chunkSize));
                if (!in.readUTF8(4).equals("PMGI")) {
                    throw new DataFormatException("Index Chunk magic mismatch, should be 'PMGI'");
                }
                int freeSpace = in.read32(); // Length of free space and/or quickref area at end of directory chunk
                // directory index entries, sorted by filename (case insensitive)
                while (in.available() > freeSpace) {
                    index.put(in.readUTF8(in.readENC()), in.readENC());
                }
                LogUtils.i("Index L" + level + indexTree);
            }

            chunkNo = -1;
            String lastKey = "";
            for (Entry<String, Integer> item : index.entrySet()) {
                if (name.compareTo(item.getKey()) < 0) {
                    if (level + 1 == indexTree.size() // it's the last index
                            && entryCache.containsKey(lastKey)) // if the first entry is cached
                    {
                        return entryCache.get(name); // it should be in the cache, too
                    }
                    break; // we found its chunk, break anyway
                }
                lastKey = item.getKey();
                chunkNo = item.getValue();
            }
            return resolveIndexedEntry(name, chunkNo, level + 1);
        } else { // process the listing chunk, and cache entries in the whole chunk
            LEInputStream in = new LEInputStream(
                    createInputStream(chunkOffset + chunkNo * chunkSize, chunkSize));
            if (!in.readUTF8(4).equals("PMGL")) {
                throw new DataFormatException("Listing Chunk magic mismatch, should be 'PMGL'");
            }
            int freeSpace = in.read32(); // Length of free space and/or quickref area at end of directory chunk
            in.read32(); // = 0;
            in.read32(); // previousChunk #
            in.read32(); // nextChunk #
            while (in.available() > freeSpace) {
                ListingEntry entry = new ListingEntry(in);
                entryCache.put(entry.name, entry);
            }
            /* The quickref area is written backwards from the end of the chunk. One quickref entry
             * exists for every n entries in the file,
			 * where n is calculated as 1 + (1 << quickref density). So for density = 2, n = 5.
				chunkSize-0002: WORD     Number of entries in the chunk
				chunkSize-0004: WORD     Offset of entry n from entry 0
				chunkSize-0008: WORD     Offset of entry 2n from entry 0
				chunkSize-000C: WORD     Offset of entry 3n from entry 0
					LogUtils.i("resources.size() = " + resources.size());
					if ( (in.available() & 1) >0 ) // align to word
						in.skip(1);
					while (in.available() > 0)
						LogUtils.i("chunk " + i + ": " + in.read16());
             */
            return entryCache.get(name);
        }
    }

    /**
     * Get an InputStream object for the named resource in the CHM.
     */
    public InputStream getResourceAsStream(String name) throws IOException {
        name = name.toLowerCase();
        if (name == null || name.length() == 0) {
            name = getSiteMap();
            if (name == null) return null;
        }
        ListingEntry entry = resolveEntry(name);
        if (entry == null) {
            throw new FileNotFoundException(filepath + "#" + name);
        }
        Section section = sections[entry.section];
        return section.resolveInputStream(entry.offset, entry.length);
    }

    /**
     * Get the name of the resources in the CHM. Caches perform better when
     * iterate the CHM using order of this returned list.
     *
     * @see #resolveIndexedEntry
     * chunk will be read twice, one in resolveIndexEntry, one here, fix it!
     */
    public synchronized List<String> list() throws IOException {
        if (resources == null) {
            // find resources in all listing chunks
            resources = new ArrayList<String>();
            for (int i = firstPMGLChunkNo; i < totalChunks; i++) {
                LEInputStream in = new LEInputStream(
                        createInputStream(chunkOffset + i * chunkSize, chunkSize));
                if (!in.readUTF8(4).equals("PMGL")) {
                    continue;
                    //throw new DataFormatException("Listing Chunk magic mismatch, should be 'PMGL'");
                }
                int freeSpace = in.read32(); // Length of free space and/or quickref area at end of directory chunk
                in.read32(); // = 0;
                in.read32(); // previousChunk #
                in.read32(); // nextChunk #
                while (in.available() > freeSpace) {
                    ListingEntry entry = new ListingEntry(in);
                    entryCache.put(entry.name, entry);
                    if (entry.name.charAt(0) == '/') {
                        resources.add(entry.name);
                        if (entry.name.endsWith(".hhc")) { // .hhc entry is the navigation file
                            siteMap = entry.name;
                            LogUtils.i("CHM sitemap " + siteMap);
                        }
                    }
                }
            }
            resources = Collections.unmodifiableList(resources); // protect the list, since the reference will be
        }

        return resources;
    }

    /**
     * The sitemap file, usually the .hhc file.
     */
    public String getSiteMap() throws IOException {
        if (resources == null) {
            list();
        }
        return siteMap;
    }

    /**
     * After close, the object can not be used any more.
     */
    public void close() throws IOException {
        entryCache = null;
        sections = null;
        resources = null;
        if (fileAccess != null) {
            fileAccess.close();
            fileAccess = null;
        }
    }

    protected void finalize() throws IOException {
        close();
    }

    class Section {

        public InputStream resolveInputStream(long off, int len) throws IOException {
            return createInputStream(contentOffset + off, len);
        }
    }

    class LZXCSection extends Section {

        long compressedLength;
        long uncompressedLength;
        int blockSize;
        int resetInterval;
        long[] addressTable;
        int windowSize;
        long sectionOffset;

        LRUCache<Integer, byte[][]> cachedBlocks;

        public LZXCSection() throws IOException, DataFormatException {
            // control data
            LEInputStream in = new LEInputStream(
                    getResourceAsStream("::DataSpace/Storage/MSCompressed/ControlData"));
            in.read32(); // words following LZXC
            if (!in.readUTF8(4).equals("LZXC")) {
                throw new DataFormatException("Must be in LZX Compression");
            }

            in.read32(); // <=2, version
            resetInterval = in.read32(); // huffman reset interval for blocks
            windowSize = in.read32() * 0x8000;    // usu. 0x10, windows size in 0x8000-byte blocks
            int cacheSize = in.read32();    // unknown, 0, 1, 2
            LogUtils.i("LZX cache size " + cacheSize);
            cachedBlocks = new LRUCache<Integer, byte[][]>((1 + cacheSize) << 2);
            in.read32(); // = 0

            // reset table
            in = new LEInputStream(
                    getResourceAsStream("::DataSpace/Storage/MSCompressed/Transform/"
                            + "{7FC28940-9D31-11D0-9B27-00A0C91E9C7C}/InstanceData/ResetTable"));
            int version = in.read32();
            if (version != 2) {
                LogUtils.w("LZXC version unknown " + version);
            }
            addressTable = new long[in.read32()];
            in.read32(); // = 8; size of table entry
            in.read32(); // = 0x28, header length
            uncompressedLength = in.read64();
            compressedLength = in.read64();
            blockSize = (int) in.read64(); // 0x8000, do not support blockSize larger than 32-bit integer
            for (int i = 0; i < addressTable.length; i++) {
                addressTable[i] = in.read64();
            }
            // init cache
//			cachedBlocks = new byte[resetInterval][blockSize];
//			cachedResetBlockNo = -1;

            ListingEntry entry = entryCache.get("::DataSpace/Storage/MSCompressed/Content".toLowerCase());
            if (entry == null) {
                throw new DataFormatException("LZXC missing content");
            }
            if (compressedLength != entry.length) {
                throw new DataFormatException("LZXC content corrupted");
            }
            sectionOffset = contentOffset + entry.offset;
        }

        @Override
        public InputStream resolveInputStream(final long off, final int len) throws IOException {
            // the input stream !
            return new InputStream() {

                int startBlockNo = (int) (off / blockSize);
                int startOffset = (int) (off % blockSize);
                int endBlockNo = (int) ((off + len) / blockSize);
                int endOffset = (int) ((off + len) % blockSize);
                // actually start at reset intervals
                int blockNo = startBlockNo - startBlockNo % resetInterval;

                Inflater inflater = new Inflater(windowSize);

                byte[] buf;
                int pos;
                int bytesLeft;

                @Override
                public int available() throws IOException {
                    return bytesLeft; // not non-blocking available
                }

                @Override
                public void close() throws IOException {
                    inflater = null;
                }

                /**
                 * Read the blockNo block, called when bytesLeft == 0
                 */
                private void readBlock() throws IOException {
                    if (blockNo > endBlockNo) {
                        throw new EOFException();
                    }

                    int cachedNo = blockNo / resetInterval;
                    synchronized (cachedBlocks) {
                        byte[][] cache = cachedBlocks.get(cachedNo);
                        if (cache == null) {
                            if ((cache = cachedBlocks.prune()) == null) // try reuse old caches
                            {
                                cache = new byte[resetInterval][blockSize];
                            }
                            int resetBlockNo = blockNo - blockNo % resetInterval;
                            for (int i = 0; i < cache.length && resetBlockNo + i < addressTable.length; i++) {
                                int blockNo = resetBlockNo + i;
                                int len = (int) ((blockNo + 1 < addressTable.length)
                                        ? (addressTable[blockNo + 1] - addressTable[blockNo])
                                        : (compressedLength - addressTable[blockNo]));
                                LogUtils.i("readBlock " + blockNo + ": " + (sectionOffset + addressTable[blockNo]) + "+ " + len);
                                inflater.inflate(i == 0, // reset flag
                                        createInputStream(sectionOffset + addressTable[blockNo], len),
                                        cache[i]); // here is the heart
                            }
                            cachedBlocks.put(cachedNo, cache);
                        }
                        if (buf == null) // allocate the buffer
                        {
                            buf = new byte[blockSize];
                        }
                        System.arraycopy(cache[blockNo % cache.length], 0, buf, 0, buf.length);
                    }

                    // the start block has special pos value
                    pos = (blockNo == startBlockNo) ? startOffset : 0;
                    // the end block has special length
                    bytesLeft = (blockNo < startBlockNo) ? 0
                            : ((blockNo < endBlockNo) ? blockSize : endOffset);
                    bytesLeft -= pos;

                    blockNo++;
                }

                @Override
                public int read(byte[] b, int off, int len) throws IOException, DataFormatException {

                    if ((bytesLeft <= 0) && (blockNo > endBlockNo)) {
                        return -1;    // no more data
                    }

                    while (bytesLeft <= 0) {
                        readBlock(); // re-charge
                    }
                    int togo = Math.min(bytesLeft, len);
                    System.arraycopy(buf, pos, b, off, togo);
                    pos += togo;
                    bytesLeft -= togo;

                    return togo;
                }

                @Override
                public int read() throws IOException {
                    byte[] b = new byte[1];
                    return (read(b) == 1) ? b[0] & 0xff : -1;
                }

                @Override
                public long skip(long n) throws IOException {
                    LogUtils.w("LZX skip happens: " + pos + "+ " + n);
                    pos += n;    // TODO n chould be negative, so do boundary checks!
                    return n;
                }
            };
        }
    }

    class ListingEntry {

        String name;
        int section;
        long offset;
        int length;

        public ListingEntry(LEInputStream in) throws IOException {
            name = in.readUTF8(in.readENC()).toLowerCase();
            section = in.readENC();
            offset = in.readENC();
            length = in.readENC();
        }

        public String toString() {
            return name + " @" + section + ": " + offset + " + " + length;
        }
    }

    public static void main(String[] argv) throws Exception {
        if (argv.length == 0) {
            System.err.println("usage: java " + CHMFile.class.getName() + " <chm file name> (file)*");
            System.exit(1);
        }

        CHMFile chm = new CHMFile(argv[0]);
        if (argv.length == 1) {
            for (String file : chm.list()) {
                System.out.println(file);
            }
        } else {
            byte[] buf = new byte[1024];
            for (int i = 1; i < argv.length; i++) {
                InputStream in = chm.getResourceAsStream(argv[i]);
                int c = 0;
                while ((c = in.read(buf)) >= 0) {
                    System.out.print(new String(buf, 0, c));
                }
            }
        }
        chm.close();
    }
}