package com.company.reveiver;

import java.io.*;

public class FileCombine {
    private File folder;
    private File file;

    public FileCombine(String folderAddr){
        this.folder = new File(folderAddr);
        this.combine();
    }

    private void combine(){
        this.file = new File(this.folder.getName());
        File[] listOfFile = this.folder.listFiles();


        try {
            FileOutputStream out = new FileOutputStream(file);

            for (File index : listOfFile) {
                int bytesread;
                FileInputStream fi = new FileInputStream(index);
                byte[] data = new byte[(int) index.length()]; //1 part k <= 10MB = 80000000 >>> int
                while ((bytesread = fi.read(data)) > 0) {
                    out.write(data, 0, bytesread);
                }
                fi.close();
            }
            out.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void clean(){
        this.folder.delete();
    }
}
