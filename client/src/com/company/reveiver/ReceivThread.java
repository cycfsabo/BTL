package com.company.reveiver;

import java.io.*;
import java.net.Socket;

public class ReceivThread extends Thread{
    /**
     * Luong nhan du lieu cua Receiver
     * Muc dich: ket noi voi nhieu client nham nhan nhieu partFile cung luc
     *
     * Cac partFile nhan duoc Client luu lai voi dinh dang cu the
     * Sau khi cac partFile da duoc nhan het, ghep cac partFile thanh 1 file bang cach goi Class FileCombine
     *
     */
    private Socket connsock;
    private int partSize = 80000000; //1 partSize = 10 MB

    public ReceivThread(Socket connsock){
        this.connsock = connsock;
    }

    public void run(){
        try {
            InputStream in = this.connsock.getInputStream();
            InputStreamReader inR = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inR);
            OutputStream out = this.connsock.getOutputStream();

            while (true) {
                //8 bytes part number
                String part = String.format("%8s", Integer.toString(this.part())).replace(' ', '0');
                byte[] bytes = part.getBytes();
                //Send 8 bytes part number
                out.write(bytes, 0, 8);
                out.flush();

                //receive part file
                //create filepart by using FileOut class
//                this.synchpart();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void synchpart(){
        //Dong bo part tiep theo - thuc hien sau khi yeu cau part tai ve
        Receiver.partseek += 1;
    }

    private synchronized int part(){
        //Lay so part can nhan
        return  Receiver.partseek;
    }

    private synchronized String getFileDirect(){
        //Lay ten file can tai ve
        return Receiver.fileDirect;
    }
}
