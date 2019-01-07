package com.company.sender;

import java.io.*;

public class FileDetach2 {
    /**
     * **Class khong con duoc su dung do hoat dong khong dung
     * --> Class update: FileDetach3
     *
     * Muc dich: tach 1 file thanh nhieu partFile va luu lai trong 1 folder cung ten
     *           cac partFile co do dai bang nhau va duoc dat ten theo dinh dang
     *           dinh dang ten partFile: filename.extension + (String) (%8s) partNumber + ".txt"
     *           -->ex: FileDetach2.java000000000.txt
     *           -->ex: FileDetach2.java000000001.txt
     */
    private int partSize = 800000; //partSize = 10MB = 80000000
//    private boolean folder;
    private File[] listOfFile;
    private FileInputStream fileInputStream;
//    private byte[] bytesread = new byte[partSize];

    public FileDetach2(String fileAddr){
        try {
            File file = new File(fileAddr);          //get file from file address   ex: ./src/com/company/main.java
            String fileParent = file.getParent();    //get file parent name string  ex: ./src/com/company
            String fileName = file.getName();        //get file name string         ex: main.java
//            fileInputStream = new FileInputStream(file);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            new File(fileAddr+"0").mkdirs();


            int partNumber = (int) Math.ceil((float)file.length()/partSize);    //round up ex: Math.ceil(1.01) = 2
            listOfFile = new File[partNumber];

            //detach file binary to separate .txt files
            for (int i = 0; i < partNumber-1; i++) {
                //set name to all part
                String indexName = String.format("%8s", Integer.toString(i)).replace(' ', '0');
//                listOfFile[i] = new File(fileParent + "/" + fileName + "/" + fileName + indexName + ".txt");
                listOfFile[i] = new File(fileAddr+"0/" + fileName + indexName);

                //Detach
                FileOutputStream fileOutputStream = new FileOutputStream(listOfFile[i].getParentFile() +
                        "/"+ listOfFile[i].getName());

                byte[] bytesread = new byte[partSize];

                randomAccessFile.seek(i*partSize);
                randomAccessFile.read(bytesread, 0, partSize);
                fileOutputStream.write(bytesread);

                fileOutputStream.close();
            }

            //detach last part - because last part's size could be lest than partSize
            int i = partNumber - 1;
            int lastPartLength = (int) file.length() - (partNumber-1)*partSize;
            byte[] lastbytes = new byte[lastPartLength];
            String indexName = String.format("%8s", Integer.toString(i)).replace(' ', '0');
//            listOfFile[i] = new File(fileParent + "/" + fileName + "/" + fileName + indexName + ".txt");
            listOfFile[i] = new File(fileAddr+"0/" + fileName + indexName);

            //Detach
            FileOutputStream fileOutputStream = new FileOutputStream(listOfFile[i].getParentFile() +
                    "/"+ listOfFile[i].getName());

            randomAccessFile.seek(i*partSize);
            randomAccessFile.read(lastbytes, 0, lastPartLength);
            fileOutputStream.write(lastbytes);

            fileOutputStream.close();
            randomAccessFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
