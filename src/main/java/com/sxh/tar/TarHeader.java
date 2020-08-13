package com.sxh.tar;

import java.io.File;


public class TarHeader {
    public String name;
    public int mode;
    public int userId;
    public int groupId;
    public long size;
    public long modTime;
    public int checkSum;
    public byte linkFlag;
    public String linkName;
    public String magic;
    public String userName;
    public String groupName;
    public int devMajor;
    public int devMinor;
    public String namePrefix;
    
    public static TarHeader createHeader(File file, File root) {
        TarHeader header = new TarHeader();
        header.name = FileUtil.getRelativePath(file, root).replace(File.separator, "/");
        
        
        return header;
    }
}
