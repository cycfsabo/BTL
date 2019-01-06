package com.company.reveiver;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

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
    private String fileName;
    private int partSize = 40000000; //1 partSize = 5 MB
    private InputStream in;
    private InputStreamReader inR;
    private BufferedReader bufferedReader;
    private OutputStream out;
    private BufferedWriter bufferedWriter;
    private String folderDirect;
    private int lastPartSize;
    private String newFileName;
    private int partNumber;

    public ReceivThread(Socket connsock, String fileName, String newFileName){
        this.connsock = connsock;
        this.fileName = fileName;
        this.newFileName = newFileName;
        this.folderDirect = this.getFileDirect();
    }

    public void run(){
        try {
            in = this.connsock.getInputStream();
            inR = new InputStreamReader(in);
            bufferedReader = new BufferedReader(inR);
            out = this.connsock.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));


            System.out.println("send file name: ");
            this.shipFileName();
            System.out.println("line 44: file name: " + fileName);

            //get part number
            partNumber = this.getPartNumber();
            lastPartSize = this.getLastPartSize();

            while (this.part() < partNumber) {
                //8 bytes part number
                this.synchpart();

                //try part file
                String part = this.tryPart();
                System.out.println("line 59: " + part);
                byte[] data;
                if(this.part() == partNumber - 1){
                    data = this.getLastPart();
                }
                else{
                    data = this.getPart();
                }

                //Save part
                new FileOut(data, folderDirect + "/" + fileName + "./" + fileName + "." + part);
                this.synchPartReceived();
            }

            if((this.isCombine()==0) && this.isReceivedAll()){
                this.synchCombine();
                new FileCombine(folderDirect + "/" + fileName + "./", newFileName);
            }
            connsock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String tryPart(){
        String part = String.format("%8s", Integer.toString(this.part()-1)).replace(' ', '0');
        try {
            byte[] bytes = part.getBytes();
            //Send 8 bytes part number
            out.write(bytes, 0, 8);
            out.flush();
            System.out.println("line 99: try part: " + part);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return part;
    }

    private byte[] getPart(){
        byte[] bytesread = new byte[partSize];
        try {
            int bytesReceived = 0;
            while (bytesReceived < partSize){
                int bytes = in.read(bytesread, bytesReceived,partSize - bytesReceived);
                bytesReceived = bytesReceived + bytes;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytesread;
    }

    private byte[] getLastPart(){
        byte[] bytesread = new byte[lastPartSize];
        try {
            int bytesReceived = 0;
            while (bytesReceived < lastPartSize){
                int bytes = in.read(bytesread, bytesReceived, lastPartSize - bytesReceived);
                bytesReceived = bytesReceived + bytes;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytesread;
    }

    private int getPartNumber(){
        int partNumber = 0;
        try {
            byte[] partNumberRead = new byte[8];
            in.read(partNumberRead, 0, 8);
            String s = String.format("%8s", new String(partNumberRead)).replace(' ', '0');
            System.out.println("line 99: part string: " + s);
            partNumber = Integer.parseInt(s);
            System.out.println("line 101: part int: " + partNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return partNumber;
    }

    private int getLastPartSize(){
        int lastPartSize = 0;
        try {
            byte[] partNumberRead = new byte[10];
            in.read(partNumberRead, 0, 10);
            String s = String.format("%10s", new String(partNumberRead)).replace(' ', '0');
            System.out.println("line 99: last part size string: " + s);
            lastPartSize = Integer.parseInt(s);
            System.out.println("line 101: last part size int: " + lastPartSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastPartSize;
    }

    private void shipFileName(){
        try {
            bufferedWriter.write(fileName + "\n");
            bufferedWriter.flush();
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
        //Lay link file can tai ve
        return Receiver.fileDirect;
    }

    private synchronized void synchCombine(){
        Receiver.isCombine = 1;
    }

    private synchronized int isCombine(){
        return Receiver.isCombine;
    }

    private synchronized void synchPartReceived(){
        Receiver.partRemain = Receiver.partRemain + 1;
    }

    private synchronized boolean isReceivedAll(){
        return Receiver.partRemain == partNumber;
    }
}
