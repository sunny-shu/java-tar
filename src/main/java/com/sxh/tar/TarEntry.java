package com.sxh.tar;

public class TarEntry {
    private TarHeader header;

    public void parseHeader(byte[] data) {
        int offset = 0;

        header.name = TarUtil.parseName(data, offset, TarHeader.NAME_LEN);
        offset += TarHeader.NAME_LEN;

        header.mode = (int) TarUtil.parseOctal(data, offset, TarHeader.MODE_LEN);
        offset += TarHeader.MODE_LEN;

        header.userId = (int) TarUtil.parseOctal(data, offset, TarHeader.UID_LEN);
        offset += TarHeader.UID_LEN;

        header.groupId = (int) TarUtil.parseOctal(data, offset, TarHeader.GID_LEN);
        offset += TarHeader.GID_LEN;

        header.size = TarUtil.parseOctal(data, offset, TarHeader.SIZE_LEN);
        offset += TarHeader.SIZE_LEN;

        header.modTime = TarUtil.parseOctal(data, offset, TarHeader.MODTIME_LEN);
        offset += TarHeader.MODTIME_LEN;

        header.checkSum = (int) TarUtil.parseOctal(data, offset, TarHeader.CHKSUM_LEN);
        offset += TarHeader.CHKSUM_LEN;

        header.linkFlag = data[offset++];

        header.linkName = TarUtil.parseName(data, offset, TarHeader.NAME_LEN);
        offset += TarHeader.NAME_LEN;

        header.magic = TarUtil.parseName(data, offset, TarHeader.USTAR_MAGICLEN);
        offset += TarHeader.USTAR_MAGICLEN;

        header.userName = TarUtil.parseName(data, offset, TarHeader.USTAR_USER_NAMELEN);
        offset += TarHeader.USTAR_USER_NAMELEN;

        header.groupName = TarUtil.parseName(data, offset, TarHeader.USTAR_GROUP_NAMELEN);
        offset += TarHeader.USTAR_GROUP_NAMELEN;

        header.devMajor = (int) TarUtil.parseOctal(data, offset, TarHeader.USTAR_DEVLEN);
        offset += TarHeader.USTAR_DEVLEN;

        header.devMinor = (int) TarUtil.parseOctal(data, offset, TarHeader.USTAR_DEVLEN);
        offset += TarHeader.USTAR_DEVLEN;

        header.namePrefix = TarUtil.parseName(data, offset, TarHeader.USTAR_FILENAME_PREFIX);
    }
}
