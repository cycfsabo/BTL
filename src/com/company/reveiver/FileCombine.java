package com.company.reveiver;

import java.io.*;

public class FileCombine {
    /**
     * Muc dich: ghep cac partFile thanh file hoan chinh, sao do xoa folder
     *
     * Input: String folderAddr
     * --> Lay cac partFile da duoc luu trong 1 folder cu the va ghep lai
     * --> con ton tai 1 van de nho
     *
     * Input: String newFileName
     * --> ten file moi
     * --> file moi duoc tao ra ngay tai vi tri folder chua folder
     */
    private File folder;
    private File file;
    private String newFileName;

    public FileCombine(String folderDirect, String newFileName){
        this.folder = new File(folderDirect);
        this.newFileName = newFileName;
        this.combine(folderDirect);
    }

    private void combine(String folderDirect){
        this.file = new File(this.folder.getParent() + "/" + newFileName);
        File[] listOfFile = this.folder.listFiles();
        String folderName = folder.getName();
        int fileNumber = listOfFile.length;

        try {
            FileOutputStream out = new FileOutputStream(file);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(0);
            long position = 0;

            for(int i = 0; i < fileNumber; i++){
                String indexName = String.format("%8s", Integer.toString(i)).replace(' ', '0');
                File file = new File(folderDirect +"/"+folderName+indexName);
                FileInputStream fileInputStream = new FileInputStream(file);
                System.out.println(file.getName());

                byte[] data = new byte[(int) file.length()];
                fileInputStream.read(data);
                randomAccessFile.write(data, 0, data.length);
                position = position + file.length();
                randomAccessFile.seek(position);
                fileInputStream.close();
//                file.delete();
            }
            out.close();
//            System.out.println(this.clean());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean clean(){
        return this.folder.delete();
    }
}
