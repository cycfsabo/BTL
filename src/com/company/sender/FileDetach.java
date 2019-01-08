package com.company.sender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileDetach {
    /**
     * Muc dich: lay ra mot phan (partFile) cua 1 file
     * moi partFile la mot chuoi bytes co do dai bang partSize (partSize co the cai dat)
     * cac partFile co do dai bang nhau (= partSize)
     * partFile cuoi cung co the co do dai nho hon partSize
     *
     * Input: ten File can detach
     *
     * - getPart(int part):
     *      Lay ra chuoi bytes tuong ung voi part (khong dung cho part cuoi)
     *      Input: int part             - so thu tu part can lay ra
     *      Output: byte[] bytesread    - chuoi bytes tuong ung voi part
     *
     * - getLastPart(int part):
     *      Tuong tu nhu getPart nhung tra lai partFile cuoi cung
     */
    private int partSize = 4000; //partSize = 5MB = 40000000
    private RandomAccessFile randomAccessFile;
    private int partNumber;
    private File file;

    public FileDetach(String fileDirect){
        try {
//            new File(fileAddr).mkdirs();
            file = new File(fileDirect);               //get file from file address   ex: ./src/com/company/main.java
//            String fileParent = file.getParent();    //get file parent name string  ex: ./src/com/company
//            String fileName = file.getName();        //get file name string         ex: main.java
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public byte[] getPart(int part){
        /**
         * tra lai chuoi bytes tuong ung voi so part tuong ung
         * khong ap dung voi part cuoi do do dai co the khac partSize
         *
         */
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
        /**
         * tra lai chuoi bytes tuong ung voi Part cuoi cung
         *
         */
        long partLength = file.length() - partSize*(this.getPartNumber()-1);
        byte[] bytesread = new byte[(int) partLength];

        try {
            randomAccessFile.seek(partSize*(this.getPartNumber()-1));
            randomAccessFile.read(bytesread, 0, (int) partLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bytesread;
    }

    public int getPartNumber(){
        /**
         * tra lai so partFile co the chia tu File
         *
         */
        partNumber = (int) Math.ceil((float)file.length()/partSize);    //round up ex: Math.ceil(1.01) = 2
        return partNumber;
    }
}
