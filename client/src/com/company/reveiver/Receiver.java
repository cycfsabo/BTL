package com.company.reveiver;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Receiver {
    /**
     * Class chuyen biet dung de nhan file tu cac client khac nhau
     * Muc dich:
     *      - Tao ra so luon tuong ung voi so client chua file
     *      - Moi luong co chung nang nhan cac partFile cu the do Receiver yeu cau
     *
     */
//    private static final int BUFSIZE = 2048;
    public static String fileDirect;
//    private static Scanner scan = new Scanner(System.in);
//    private int partSize = 80000000; //1 partSize = 10 MB
    public static int partNumber;
    public static int partseek = 0;
    public static int isCombine = 0;
    public static int partRemain = 0;


    public Receiver(String[] ipAddr, int port, String fileName, String newFileName){
        this.partNumber = partNumber;
        this.fileDirect = "./src/Container";
        try{
            File file = new File(fileDirect);
            boolean folder = new File(fileDirect+"/" + fileName + ".").mkdirs();
            for (String index: ipAddr){
                Socket socket = new Socket(index, port);
                Thread receivThread = new ReceivThread(socket, fileName, newFileName);
                receivThread.start();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
