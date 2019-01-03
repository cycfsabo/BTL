package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFile {
    private File file;
    private FileInputStream fileInputStream;
    byte[] bytesread;

    public ReadFile(String fileAddr) throws IOException {
        file = new File(fileAddr);
        fileInputStream = new FileInputStream(fileAddr);
        bytesread = new byte[(int) file.length()];
        fileInputStream.read(bytesread);
        for(byte b: bytesread){
            char c = (char) b;
            System.out.print(c);
        }
        fileInputStream.close();
    }
}
