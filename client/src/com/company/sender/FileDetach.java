package com.company.sender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileDetach {
    private int partSize = 800; //partSize = 10MB = 80000000
    private RandomAccessFile randomAccessFile;
    private int partNumber;
    private File file;

    public FileDetach(String fileAddr){
        try {
            new File(fileAddr).mkdirs();
            file = new File(fileAddr);               //get file from file address   ex: ./src/com/company/main.java
//            String fileParent = file.getParent();    //get file parent name string  ex: ./src/com/company
//            String fileName = file.getName();        //get file name string         ex: main.java
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public byte[] getPart(int part){
        byte[] bytesread = new byte[partSize];
        try {
            randomAccessFile.seek((long)partSize*part);
            randomAccessFile.read(bytesread, 0, partSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytesread;
    }

    public byte[] getLastPart(){
        long partLength = file.length() - partSize*(this.getPartNumber()-1);
        byte[] bytesread = new byte[(int) partLength];

        try {
            randomAccessFile.seek(partSize*(this.getPartNumber()-1));
//            randomAccessFile.seek(0);
            randomAccessFile.read(bytesread, 0, (int) partLength);
//            System.out.println(bytesread + " " + bytesread.length);
//            String s = new String(bytesread, 0, bytesread.length);
//            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bytesread;
    }

    public int getPartNumber(){
        partNumber = (int) Math.ceil((float)file.length()/partSize);    //round up ex: Math.ceil(1.01) = 2
        return partNumber;
    }
}
