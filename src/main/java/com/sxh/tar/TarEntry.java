package com.sxh.tar;

import java.io.File;
import java.util.Date;

public class TarEntry {
    private TarHeader header;
    private File file;
    
    public TarEntry(byte[] header) {
        this.parseHeader(header);
    }
    public TarEntry(File file,File root) {
       this.file = file;
       this.header = TarHeader.createHeader(file, root);
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
    
    public boolean isDirectory() {
        if (this.file != null)
            return this.file.isDirectory();

        if (header != null) {
            if (header.linkFlag == TarConstants.LF_DIR)
                return true;
        }

        return false;
    }
    
    public void writeEntryHeader(byte[] outbuf) {
        int offset = 0;

        TarUtil.getNameBytes(header.name, outbuf, offset, TarConstants.NAME_LEN);
        offset += TarConstants.NAME_LEN;
        
        TarUtil.getOctalBytes(header.mode, outbuf, offset, TarConstants.MODE_LEN);
        offset += TarConstants.MODE_LEN;
        
        TarUtil.getOctalBytes(header.userId, outbuf, offset, TarConstants.UID_LEN);
        offset += TarConstants.UID_LEN;
        
        TarUtil.getOctalBytes(header.groupId, outbuf, offset, TarConstants.GID_LEN);
        offset += TarConstants.GID_LEN;

        TarUtil.getLongOctalBytes(header.size, outbuf, offset, TarConstants.SIZE_LEN);
        offset += TarConstants.SIZE_LEN;
        
        TarUtil.getLongOctalBytes(header.modTime, outbuf, offset, TarConstants.MODTIME_LEN);
        offset += TarConstants.MODTIME_LEN;

        int csOffset = offset;
        for (int c = 0; c < TarConstants.CHKSUM_LEN; ++c)
            outbuf[offset++] = (byte) ' ';

        outbuf[offset++] = header.linkFlag;

        TarUtil.getNameBytes(header.linkName, outbuf, offset, TarConstants.NAME_LEN);
        offset += TarConstants.NAME_LEN;
        
        TarUtil.getNameBytes(header.magic, outbuf, offset, TarConstants.USTAR_MAGICLEN);
        offset += TarConstants.USTAR_MAGICLEN;
        
        TarUtil.getNameBytes(header.userName, outbuf, offset, TarConstants.USTAR_USER_NAMELEN);
        offset += TarConstants.USTAR_USER_NAMELEN;
        
        TarUtil.getNameBytes(header.groupName, outbuf, offset, TarConstants.USTAR_GROUP_NAMELEN);
        offset += TarConstants.USTAR_GROUP_NAMELEN;
        
        TarUtil.getOctalBytes(header.devMajor, outbuf, offset, TarConstants.USTAR_DEVLEN);
        offset += TarConstants.USTAR_DEVLEN;
        
        TarUtil.getOctalBytes(header.devMinor, outbuf, offset, TarConstants.USTAR_DEVLEN);
        offset += TarConstants.USTAR_DEVLEN;
        
        TarUtil.getNameBytes(header.namePrefix, outbuf, offset, TarConstants.USTAR_FILENAME_PREFIX);
        offset += TarConstants.USTAR_FILENAME_PREFIX;

        for (; offset < outbuf.length;)
            outbuf[offset++] = 0;

        long checkSum = TarUtil.computeCheckSum(outbuf);

        TarUtil.getCheckSumOctalBytes(checkSum, outbuf, csOffset, TarConstants.CHKSUM_LEN);
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

        header.linkFlag = data[offset];
        offset++;

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
