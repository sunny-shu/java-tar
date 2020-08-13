package com.sxh.tar;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class Tar {
    public static void tar(String path, String tar) {
        List<File> toPack = FileUtil.listFileRecursively(path);
        File pathF = new File(path);
        File root;
        if (pathF.isFile()) {
            root = pathF.getParentFile();
        } else {
            root = pathF;
        }

        for (File file : toPack) {
            TarEntry entry = new TarEntry(file, root);
        }
    }

    public static void untar(File tar, String destDir) {
        try (TarInputStream tarInputStream = new TarInputStream(new FileInputStream(tar));) {
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
