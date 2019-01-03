package com.company.sender;

import java.io.*;

public class FileDetach3 {
    private int partSize = 80; //partSize = 10MB = 80000000
//    private boolean folder;
    private File[] listOfFile;
    private FileInputStream fileInputStream;
//    private byte[] bytesread = new byte[partSize];

    public FileDetach3(String fileAddr){
        try {
            boolean folder = new File(fileAddr).mkdirs();
            File file = new File(fileAddr);          //get file from file address   ex: ./src/com/company/main.java
            String fileParent = file.getParent();    //get file parent name string  ex: ./src/com/company
            String fileName = file.getName();        //get file name string         ex: main.java
            fileInputStream = new FileInputStream(file);

            int partNumber = (int) Math.ceil(file.length()/partSize);    //round up ex: Math.ceil(1.01) = 2
            listOfFile = new File[partNumber];

            //detach file binary to separate .txt files
            for (int i = 0; i < partNumber-1; i++) {
                //set name to all part
                String indexName = String.format("%8s", Integer.toString(i)).replace(' ', '0');
                listOfFile[i] = new File(fileParent + "/" + fileName + indexName + ".txt");

                //Detach
                FileOutputStream fileOutputStream = new FileOutputStream(listOfFile[i].getParentFile() +
                        "/"+ listOfFile[i].getName());

                byte[] bytesread = new byte[partSize];

                fileInputStream.read(bytesread, partSize*i, partSize);
                fileOutputStream.write(bytesread);
                fileOutputStream.close();
            }

            //detach last part - because last part's size could be lest than partSize
            int lastPartLength = (int) file.length() - (partNumber-1)*partSize;
            byte[] lastbytes = new byte[lastPartLength];
            fileInputStream.read(lastbytes, partSize*(partNumber-1), lastPartLength);
            FileOutputStream fileOutputStream = new FileOutputStream(listOfFile[partNumber-1].getParentFile() +
                    "/"+ listOfFile[partNumber-1].getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
