package com.company.sender;

//import java.net.*;

import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {
//    static  final int BUFSIZE = 2048;
    private Socket connSock;
    private byte[] partBytes = new byte[8];

    public  EchoThread(Socket connSock){
        this.connSock = connSock;
    }

    public void run(){
        try {
            InputStream in = this.connSock.getInputStream();
            InputStreamReader inR = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inR);
            OutputStream out = this.connSock.getOutputStream();

            while (true){
                in.read(partBytes);
                String s = new String(partBytes);
                int part = Integer.parseInt(s);

                //call FileDetach and get part
                //send part to receiThread

            }

//            while (true){
//                String fileName = bufferedReader.readLine();
//                if(fileName == null){
//                    break;
//                }
//                else if(fileName.equals("QUIT")){
//                    System.out.println(this.connSock.getInetAddress() +
//                            ":" + this.connSock.getPort() +
//                            " disconnected");
//                    break;
//                }
//
//                String dir = System.getProperty("user.dir");
//                File file = new File(dir + "/file2send/" + fileName);
//                DataOutputStream dataOut = new DataOutputStream(out);
//
//                if(!file.exists()){
//                    long fileSize = 0L;
//                    dataOut.writeLong(fileSize);
//                    continue;
//                }
//                else {
//                    long fileSize = file.length();
//                    dataOut.writeLong(fileSize);
//
//                    FileInputStream fi = new FileInputStream(file);
//                    byte[] data = new byte[BUFSIZE];
//                    int bytesread;
//                    //gui file:
//                    while ((bytesread = fi.read(data)) > 0){
//                        out.write(data, 0, bytesread);
//                        out.flush();
//                    }
//                    fi.close();
//                }
//            }
//            connSock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void synchpart(){
        Sender.partseek += 1;
    }
}
