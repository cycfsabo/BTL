package com.company.sender;

import com.company.reveiver.FileOut;

import java.io.File;

public class FileDetach3 {
    /**
     * Muc dich: thu nghiem tach file va luu thanh cac partFile voi ten duoc dinh dang san
     * --> phuc vu cho viec test class FileCombine
     * --> class duoc su dung chinh thuc: FileDetach
     *
     * @param fileDirect
     * Input: String fileDirect
     * --> vi tri file can tach
     *
     */
    public FileDetach3(String fileDirect){
        File file = new File(fileDirect);
        String fileName = file.getName();
//        String fileParent = file.getParent();

        FileDetach detach = new FileDetach(fileDirect);
        int partNumber = detach.getPartNumber();
        new File(fileDirect+".").mkdirs();

        for (int i = 0; i< partNumber-1; i++){
            String indexName = String.format("%8s", Integer.toString(i)).replace(' ', '0');
            new FileOut(detach.getPart(i), fileDirect + "./" + fileName + "." + indexName);
        }
        String indexName = String.format("%8s", Integer.toString(partNumber-1)).replace(' ', '0');
        new FileOut(detach.getLastPart(), fileDirect + "./" + fileName + "." + indexName);
    }
}
