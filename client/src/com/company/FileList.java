package com.company;

import java.io.File;

public class FileList {
    private File folder;
    private File[] listOfFile;
    private String folderAddr;
    private String list = new String("");
    //	private static String source = "./src/Client";

    public FileList(String folderAddr){
        this.folderAddr = folderAddr;
        this.folder = new File(folderAddr);
        this.setFileList();
    }

    public void setFileList() {
        listOfFile = folder.listFiles();

//        String workingDir = System.getProperty("user.dir");
//        File fileList = new File(workingDir+"/src/Client");
//        File[] file = fileList.listFiles();
//        numFile = file.length;
        for (File index : listOfFile) {
            list = list.concat(index.getName().concat(" "));
        }
//        System.out.print(list);
    }

    public File[] getFileList() {
        return listOfFile;
    }

    public String getList(){
        return list;
    }

    public String getListLength(){
        //Get list length and convert to 8 bit binary
        String s = Integer.toBinaryString(this.getList().length());
        return String.format("%8s", s).replace(' ', '0');
    }

    public int getNumFile() {
        return listOfFile.length;
    }
}
