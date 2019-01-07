package com.company.reveiver;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOut {
    /**
     * Mục dich: Tao 1 partFile
     * Input 1: byte[] bytes        - chuoi bytes cua partFile
     * Input 2: String fileDirect   - Dia chi part + "/" + partName
     *
     * Input 1: duoc lay tu chuoi byte den cua socket
     * Input 2: duoc lay tu dinh dang file truyen vao
     */

    private String fileDirect;
    private byte[] byteswrite;

    public FileOut(byte[] byteswrite, String fileDirect){
        this.byteswrite = byteswrite;
        this.fileDirect = fileDirect;
        this.newFile();
    }

    private void newFile(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileDirect);
            fileOutputStream.write(byteswrite);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
