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
                    writeFile(entry, tarInputStream, destDir);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static void writeFile(TarEntry entry, TarInputStream tarInputStream, String destDir) {
        int count;
        byte data[] = new byte[TarConstants.IO_BUFFER_SIZE];
        File fileToUnpack = Paths.get(destDir, entry.getName()).toFile();
        
        if (entry.isDirectory()) {
            
            if(!fileToUnpack.exists()) {
                fileToUnpack.mkdirs();
            }
        }
        try( BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(fileToUnpack));) {
            while ((count = tarInputStream.read(data)) != -1) {
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
