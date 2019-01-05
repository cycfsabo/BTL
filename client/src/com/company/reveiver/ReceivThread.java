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
    private int partSize = 80000; //1 partSize = 10 MB
    private InputStream in;
    private InputStreamReader inR;
    private BufferedReader bufferedReader;
    private OutputStream out;
    private BufferedWriter bufferedWriter;
    private String folderDirect = "./src/Container";
    private int lastPartSize;
    private String newFileName;

    public ReceivThread(Socket connsock, String fileName, String newFileName){
        this.connsock = connsock;
        this.fileName = fileName;
        this.newFileName = newFileName;
    }

    public void run(){
        try {
            in = this.connsock.getInputStream();
            inR = new InputStreamReader(in);
            bufferedReader = new BufferedReader(inR);
            out = this.connsock.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));


            System.out.println("send file name: ");
//            this.shipFileName();

            System.out.println("line 44: file name: " + fileName);

            //get part number
            int partNumber = this.getPartNumber();
            lastPartSize = this.getLastPartSize();

            while (this.part() < partNumber-1) {
                //8 bytes part number
                this.synchpart();

                //try part file
                String part = this.tryPart();
                System.out.println("line 55: " + part);

                System.out.println("get part");
                //receive part file
                byte[] bytesread = this.getPart();

                //Save part
                new FileOut(bytesread, folderDirect + "/" + fileName + "./" + fileName + "." + part);
            }

            System.out.println("asdadsf");

            if(this.com() == 0){
                this.synchpart();
                //try last part file
                String part = this.tryPart();
                System.out.println("line 55: " + part);

                //receive part file
                byte[] bytesread = this.getLastPart();

                //Save part
                System.out.println("213");
                new FileOut(bytesread, folderDirect + "/" + fileName + "./" + fileName + "." + part);
//                System.out.println("dfasd");
                System.out.println("isCombine: " + this.com());
                System.out.println("combine");
                this.isCombine();
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
            in.read(bytesread);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytesread;
    }

    private byte[] getLastPart(){
        byte[] bytesread = new byte[lastPartSize];
        try {
            in.read(bytesread);
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
            bufferedWriter.write(fileName, 0, fileName.length());
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

    private synchronized void isCombine(){
        Receiver.isCombine = 1;
    }

    private synchronized int com(){
        return Receiver.isCombine;
    }
}
