package com.company.sender;

//import java.net.*;

import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {
    /**
     * Luong gui du lieu tu Sender toi Receiver cua cac Client khac
     * Muc dich: ket noi toi nhieu Client nham gui cho nhieu Client mot luc
     *
     * Voi so part duoc yeu cau tu Receiver, goi den class FileDetach
     * class FileDetach tra lai chuoi bytes tuong ung voi partFile duoc yeu cau
     * Client gui chuoi bytes do den Receiver
     *
     */
//    static  final int BUFSIZE = 2048;
    private Socket connSock;
//    private byte[] partBytes = new byte[8];
    private InputStream in;
    private InputStreamReader inR;
    private BufferedReader bufferedReader;
    private OutputStream out;
    private FileDetach fileDetach;
    private String folderDirect = "./src/Container";

    public  EchoThread(Socket connSock){
        this.connSock = connSock;
    }

    public void run(){
        try {
            in = this.connSock.getInputStream();
            inR = new InputStreamReader(in);
            bufferedReader = new BufferedReader(inR);
            out = this.connSock.getOutputStream();
            System.out.println("echothread");

            //get File name
            String filename = this.getFileName();
            System.out.println(filename);
//            String filename = "/test.jpg";

            //send total part number
            fileDetach = new FileDetach(folderDirect + "/" + filename);
//            fileDetach = new FileDetach(folderDirect + "/" + filename);
            int partNum = fileDetach.getPartNumber();

            //send part Number
            this.shipPartNumber(partNum);
            this.shipLastPartSize();
            int part = 0;
            while (part < partNum){
                //get part number of partFile from receivThread
                part = this.getPart();

                //call FileDetach and get part
                //send part to receivThread
                if(part == (partNum-1)){
                    System.out.println("line 61: last part");
                    this.shipLastPart();
                    break;
                } else {
                    this.shipPart(part);
                }
//                this.shipPart(part);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileName(){
        String fileName = null;
        try{
            fileName = bufferedReader.readLine();
//            fileName = bufferedReader.read(fileName, 0, );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private int getPart(){
        int part = 0;
        try{
            byte[] partBytes = new byte[8];
            in.read(partBytes, 0, 8);
            String s = String.format("%8s", new String(partBytes)).replace(' ', '0');//new String(partBytes);
            System.out.println("line 81: part string: " + s);
            part = Integer.parseInt(s);

            System.out.println("line 84: part int: " + part);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return part;
    }

    private void shipPart(int part) {
        try {
            out.write(fileDetach.getPart(part));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shipLastPart(){
        try{
            out.write(fileDetach.getLastPart());
            out.flush();
//            System.out.println("line 114: " + fileDetach.getLastPart().length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shipPartNumber(int partNum){
        try{
            String partNumber = String.format("%8s", Integer.toString(partNum)).replace(' ', '0');
            byte[] partNumberBytes = partNumber.getBytes();
            out.write(partNumberBytes, 0, 8);
            System.out.println("line 104: PartNumber: " + partNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shipLastPartSize(){
        int lastPartSize = fileDetach.getLastPart().length;
        try{
            String lastSize = String.format("%10s", Integer.toString(lastPartSize)).replace(' ', '0');
            byte[] partNumberBytes = lastSize.getBytes();
            out.write(partNumberBytes, 0, 10);
            System.out.println("line 117: last size: " + lastSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
