package com.sxh.tar;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;


public class Tar {
    public static void untar(File tar, String destDir) {
        try (TarInputStream tarInputStream = new TarInputStream(new FileInputStream(tar));) {
            boolean isFinished = false;
            while (!isFinished) {
                TarEntry entry = tarInputStream.getNextEntry();
                if (entry == null) {
                    isFinished = true;
                } else {
                    TarUtil.writeTarEntryToFile(entry, tarInputStream, destDir);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


}
