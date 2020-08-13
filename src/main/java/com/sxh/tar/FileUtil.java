package com.sxh.tar;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import jdk.internal.org.objectweb.asm.tree.LdcInsnNode;

public class FileUtil {
    public static void writeTarEntryToFile(TarEntry entry, TarInputStream tarInputStream, String destDir) {
        int count;
        byte data[] = new byte[TarConstants.IO_BUFFER_SIZE];
        File fileToUnpack = Paths.get(destDir, entry.getName()).toFile();

        if (entry.isDirectory()) {

            if (!fileToUnpack.exists()) {
                fileToUnpack.mkdirs();
            }
        }
        try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(fileToUnpack));) {
            while ((count = tarInputStream.read(data)) != -1) {
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static String getRelativePath(File file, File root) {
        Path filePath = file.toPath();
        Path rootPath = root.toPath();
        return filePath.relativize(rootPath).toString();
    }

    public static List<File> listFileRecursively(String path) {
        List<File> ret = new ArrayList<File>();
        LinkedList<File> toSearch = new LinkedList<File>();
        toSearch.add(new File(path));
        while (!toSearch.isEmpty()) {
            File file = toSearch.poll();
            if (file.isFile()) {
                ret.add(file);
            } else {
                File[] list = file.listFiles();
                if (list.length == 0) {
                    ret.add(file);
                } else {
                    for (File item : list) {
                        toSearch.add(item);
                    }
                }
            }
        }
        return ret;
    }
}
