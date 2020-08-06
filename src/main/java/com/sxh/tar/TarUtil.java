package com.sxh.tar;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

public class TarUtil {
    public static String parseName(byte[] header, int offset, int length) {
        StringBuffer result = new StringBuffer(length);

        int end = offset + length;
        for (int i = offset; i < end; ++i) {
            if (header[i] == 0)
                break;
            result.append((char) header[i]);
        }

        return result.toString();
    }

    public static void getNameBytes(String name, byte[] buf, int offset, int length) {
        int i;

        for (i = 0; i < length && i < name.length(); ++i) {
            buf[offset + i] = (byte) name.charAt(i);
        }

        for (; i < length; ++i) {
            buf[offset + i] = 0;
        }

        return ;
    }

    public static long parseOctal(byte[] header, int offset, int length) {
        long result = 0;
        boolean stillPadding = true;

        int end = offset + length;
        for (int i = offset; i < end; ++i) {
            if (header[i] == 0)
                break;

            if (header[i] == (byte) ' ' || header[i] == '0') {
                if (stillPadding)
                    continue;

                if (header[i] == (byte) ' ')
                    break;
            }

            stillPadding = false;

            result = ( result << 3 ) + ( header[i] - '0' );
        }

        return result;
    }

    public static void getOctalBytes(long value, byte[] buf, int offset, int length) {
        int idx = length - 1;

        buf[offset + idx] = 0;
        --idx;
        buf[offset + idx] = (byte) ' ';
        --idx;

        if (value == 0) {
            buf[offset + idx] = (byte) '0';
            --idx;
        } else {
            for (long val = value; idx >= 0 && val > 0; --idx) {
                buf[offset + idx] = (byte) ( (byte) '0' + (byte) ( val & 7 ) );
                val = val >> 3;
            }
        }

        for (; idx >= 0; --idx) {
            buf[offset + idx] = (byte) ' ';
        }

        return ;
    }
    
    public static long computeCheckSum(byte[] buf) {
        long sum = 0;

        for (int i = 0; i < buf.length; ++i) {
            sum += 255 & buf[i];
        }

        return sum;
    }

    public static int getCheckSumOctalBytes(long value, byte[] buf, int offset, int length) {
        getOctalBytes( value, buf, offset, length );
        buf[offset + length - 1] = (byte) ' ';
        buf[offset + length - 2] = 0;
        return offset + length;
    }

    public static int getLongOctalBytes(long value, byte[] buf, int offset, int length) {
        byte[] temp = new byte[length + 1];
        getOctalBytes( value, temp, 0, length + 1 );
        System.arraycopy( temp, 0, buf, offset, length );
        return offset + length;
    }
    
    public static void writeTarEntryToFile(TarEntry entry, TarInputStream tarInputStream, String destDir) {
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
