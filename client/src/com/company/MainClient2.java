package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainClient2 {
    private int port;
    private String ipaddr;

    public MainClient2(String ipaddr, int port){
        this.ipaddr = ipaddr;
        this.port = port;

        String quit = "QUIT";
        String fileName;
        Socket mainCli = null;
        BufferedWriter os = null;
        BufferedReader is = null;
        FileList2 fileList;

        try {
            mainCli = new Socket(ipaddr, port);
            os = new BufferedWriter(new OutputStreamWriter(mainCli.getOutputStream(), "UTF-8"));
            is = new BufferedReader(new InputStreamReader(mainCli.getInputStream()));
        }	catch(UnknownHostException e) {
            System.out.println(e);
        }	catch(IOException e) {
            System.out.println("Counldn't get I/O for the conenect to "+ipaddr);
            return ;
        }

        fileList = new FileList2("./src/com/company");
//        fileList.setFileList();
        String list = fileList.getList();
//        System.out.println("List: "+list);


        try  {
            System.out.println("Length: "+list.length());
            os.write(list);
//			os.newLine();
            os.flush();
        }	catch(UnknownHostException e) {
            e.printStackTrace();
        }	catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("End2");
    }
}
