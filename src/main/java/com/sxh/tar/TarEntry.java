package com.sxh.tar;

import java.util.Date;


public class TarEntry {
    private TarHeader header;
    private long startPos;
    
    public TarEntry(byte[] header) {
        this.parseHeader(header);
    }
    
    public TarHeader getHeader() {
        return header;
    }

    public String getName() {
        String name = header.name;
        if (header.namePrefix != null && !header.namePrefix.toString().equals("")) {
            name = header.namePrefix.toString() + "/" + name;
        }

        return name;
    }

    public void setName(String name) {
        header.name = name;
    }

    public int getUserId() {
        return header.userId;
    }

    public void setUserId(int userId) {
        header.userId = userId;
    }

    public int getGroupId() {
        return header.groupId;
    }

    public void setGroupId(int groupId) {
        header.groupId = groupId;
    }

    public String getUserName() {
        return header.userName;
    }

    public void setUserName(String userName) {
        header.userName = userName;
    }

    public String getGroupName() {
        return header.groupName;
    }

    public void setGroupName(String groupName) {
        header.groupName = groupName;
    }

    public void setIds(int userId, int groupId) {
        this.setUserId(userId);
        this.setGroupId(groupId);
    }

    public void setModTime(long time) {
        header.modTime = time / 1000;
    }

    public void setModTime(Date time) {
        header.modTime = time.getTime() / 1000;
    }

    public Date getModTime() {
        return new Date(header.modTime * 1000);
    }

    public long getSize() {
        return header.size;
    }

    public void setSize(long size) {
        header.size = size;
    }

    public void parseHeader(byte[] data) {
        int offset = 0;

        header.name = TarUtil.parseName(data, offset, TarConstants.NAME_LEN);
        offset += TarConstants.NAME_LEN;

        header.mode = (int) TarUtil.parseOctal(data, offset, TarConstants.MODE_LEN);
        offset += TarConstants.MODE_LEN;

        header.userId = (int) TarUtil.parseOctal(data, offset, TarConstants.UID_LEN);
        offset += TarConstants.UID_LEN;

        header.groupId = (int) TarUtil.parseOctal(data, offset, TarConstants.GID_LEN);
        offset += TarConstants.GID_LEN;

        header.size = TarUtil.parseOctal(data, offset, TarConstants.SIZE_LEN);
        offset += TarConstants.SIZE_LEN;

        header.modTime = TarUtil.parseOctal(data, offset, TarConstants.MODTIME_LEN);
        offset += TarConstants.MODTIME_LEN;

        header.checkSum = (int) TarUtil.parseOctal(data, offset, TarConstants.CHKSUM_LEN);
        offset += TarConstants.CHKSUM_LEN;

        header.linkFlag = data[offset++];

        header.linkName = TarUtil.parseName(data, offset, TarConstants.NAME_LEN);
        offset += TarConstants.NAME_LEN;

        header.magic = TarUtil.parseName(data, offset, TarConstants.USTAR_MAGICLEN);
        offset += TarConstants.USTAR_MAGICLEN;

        header.userName = TarUtil.parseName(data, offset, TarConstants.USTAR_USER_NAMELEN);
        offset += TarConstants.USTAR_USER_NAMELEN;

        header.groupName = TarUtil.parseName(data, offset, TarConstants.USTAR_GROUP_NAMELEN);
        offset += TarConstants.USTAR_GROUP_NAMELEN;

        header.devMajor = (int) TarUtil.parseOctal(data, offset, TarConstants.USTAR_DEVLEN);
        offset += TarConstants.USTAR_DEVLEN;

        header.devMinor = (int) TarUtil.parseOctal(data, offset, TarConstants.USTAR_DEVLEN);
        offset += TarConstants.USTAR_DEVLEN;

        header.namePrefix = TarUtil.parseName(data, offset, TarConstants.USTAR_FILENAME_PREFIX);
    }
}
