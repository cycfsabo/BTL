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


    public Receiver(String[] ipAddr, int port, String fileDirect){
        this.partNumber = partNumber;
        this.fileDirect = fileDirect;
        try{
//            File file = new File(fileDirect);
            boolean folder = new File(fileDirect).mkdirs();
            for (String index: ipAddr){
                Socket socket = new Socket(index, port);
                Thread receivThread = new ReceivThread(socket);
                receivThread.start();
            }
//            socket = new Socket(ipAddr, port);
//            OutputStream out = socket.getOutputStream();
//            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
//            InputStream in = socket.getInputStream();
//
//            while (true){
//                System.out.print("Input file name: ");
//                String fileName = scan.nextLine();
//
//                if(fileName.isEmpty()){
//                    System.out.println("File name couldn't be empty!");
//                    continue;
//                }
//                else{
//                    String tmp = fileName + "\n";
//                    bufferedWriter.write(tmp);
//                    bufferedWriter.flush();
//                    if(fileName.equals("QUIT")){
//                        break;
//                    }
//
//                    DataInputStream dataInputStream = new DataInputStream(in);
//                    long fileSize = dataInputStream.readLong();
//
//                    if(fileSize == 0){
//                        System.out.println("File not found");
//                        continue;
//                    }
//                    else {
//                        String dir = System.getProperty("user.dir");
//                        OutputStream newFile = new FileOutputStream(dir + "/" + fileName);
//                        byte[] data = new byte[BUFSIZE];
//                        int bytesread;
//                        long byteReceived = 0;
//
//                        // nhan file
//                        while (byteReceived < fileSize && (bytesread = in.read(data)) > 0){
//                            newFile.write(data, 0, bytesread);
//                            byteReceived += bytesread;
//                        }
//                        newFile.close();
//                        System.out.println(fileName + " received");
//                    }
//                }
//                socket.close();
//            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
