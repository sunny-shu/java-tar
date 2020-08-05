package com.sxh;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sxh.tar.Tar;

public class TarTest {
    private String testDirPath;
    @Before
    public void init() {
        String tmpDir = System.getProperty("java.io.tmpdir");
        testDirPath = Paths.get(tmpDir, Long.toString(new Random().nextLong())).toString();
        File testDir = new File(testDirPath);
        if(!testDir.exists()) {
            testDir.mkdirs();
        }
    }
    
    @After
    public void clean() {
        File testDir = new File(testDirPath);
        if(!testDir.exists()) {
            testDir.delete();
        }
    }
    
    @Test
    public void unpackTest() throws IOException {
        File in = new File(this.getClass().getResource("/test.tar").getPath());
        Tar.untar(in, testDirPath);
    }
}
