package com.company;

import com.company.reveiver.FileCombine;
import com.company.reveiver.Receiver;
import com.company.sender.FileDetach;
import com.company.sender.FileDetach3;

public class Main3 {
    public static void main(String[] args) {
//        FileList fileList = new FileList();
//        fileList.setFileList();
//        System.out.println(fileList.getFileList());

//        MainClient mainClient = new MainClient();
//
//        String[] ipAddr = mainClient.getListIPToClient().split(" ");
//        System.out.println(ipAddr);
//        for(String s: ipAddr){
//            System.out.println(s);
        String[] s = new String[1];
        s[0] = "192.168.2.185";
//        s[1] = "192.168.2.171";
        new Receiver(s, 8888,  "test.jpg", "newtest4.jpg");
    }
}
