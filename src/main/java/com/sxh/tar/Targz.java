package com.sxh.tar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.zip.DeflaterInputStream;

public class Targz {
    public static void untar(File tar,String destDir) throws FileNotFoundException {
        DeflaterInputStream inputStream = new DeflaterInputStream(new FileInputStream(tar));
        try (TarInputStream tarInputStream = new TarInputStream(inputStream);) {
            boolean isFinished = false;
            while (!isFinished) {
                TarEntry entry = tarInputStream.getNextEntry();
                if (entry == null) {
                    isFinished = true;
                } else {
                    FileUtil.writeTarEntryToFile(entry, tarInputStream, destDir);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
