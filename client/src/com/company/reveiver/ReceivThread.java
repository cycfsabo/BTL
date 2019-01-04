package com.company.reveiver;

import java.io.*;
import java.net.Socket;

public class ReceivThread extends Thread{
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

                //receive part file:
                //
//                this.synchpart();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void synchpart(){
        Receiver.partseek += 1;
    }

    private synchronized int part(){
        return  Receiver.partseek;
    }

    private synchronized String getFileDirect(){
        return Receiver.fileDirect;
    }
}
