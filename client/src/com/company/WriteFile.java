package com.company;

import java.io.*;

public class WriteFile {
    private File file;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;
    private byte[] bytesread;


    public WriteFile(String fileInAddr, String fileOutAddr) throws IOException {
        fileOutputStream = new FileOutputStream(fileOutAddr);
        fileInputStream = new FileInputStream(fileInAddr);
        File file = new File(fileInAddr);

        bytesread = new byte[(int) file.length()];
        fileInputStream.read(bytesread);
        fileOutputStream.write(bytesread);
//        for(byte b: bytesread){
//            char c = (char) b;
//            System.out.print(c);
//        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
