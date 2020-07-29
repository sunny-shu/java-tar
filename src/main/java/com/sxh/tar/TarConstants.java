package com.sxh.tar;

public class TarConstants {
    public static final int EOF_BLOCK = 1024;
    public static final int DATA_BLOCK = 512;
    public static final int HEADER_BLOCK = 512;

    // header
    public static final int NAME_LEN = 100;
    public static final int MODE_LEN = 8;
    public static final int UID_LEN = 8;
    public static final int GID_LEN = 8;
    public static final int SIZE_LEN = 12;
    public static final int MODTIME_LEN = 12;
    public static final int CHKSUM_LEN = 8;

    // link flag enum
    public static final byte LF_OLDNORM = 0;
    public static final byte LF_NORMAL = (byte) '0';
    public static final byte LF_LINK = (byte) '1';
    public static final byte LF_SYMLINK = (byte) '2';
    public static final byte LF_CHR = (byte) '3';
    public static final byte LF_BLK = (byte) '4';
    public static final byte LF_DIR = (byte) '5';
    public static final byte LF_FIFO = (byte) '6';
    public static final byte LF_CONTIG = (byte) '7';

    public static final String USTAR_MAGIC = "ustar"; // POSIX

    public static final int USTAR_MAGICLEN = 8;
    public static final int USTAR_USER_NAMELEN = 32;
    public static final int USTAR_GROUP_NAMELEN = 32;
    public static final int USTAR_DEVLEN = 8;
    public static final int USTAR_FILENAME_PREFIX = 155;
}
