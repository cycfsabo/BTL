package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.company.FileList;

public class MainClient {
    private int port;
    private String ipaddr;

    public MainClient(String ipaddr, int port){
        this.ipaddr = ipaddr;
        this.port = port;

        String quit = "QUIT";
        String fileName;
        Socket mainCli = null;
        BufferedWriter os = null;
        BufferedReader is = null;
        FileList fileList;

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

        fileList = new FileList("./src/com/company");
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
