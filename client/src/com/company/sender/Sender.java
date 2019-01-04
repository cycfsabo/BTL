package com.company.sender;

import java.net.*;
import java.io.*;
import java.util.*;

public class Sender {
    /**
     * Class chuyen biet dung de gui file toi cac client khac nhau
     *
     * Muc dich:
     *      - Tao ra so luong tuong ung voi so client can tai file
     *      - Moi luong co chung nang gui cac partFile tuong ung toi Receiver cua client khac yeu cau
     *
     */
    public static int partseek = 0;
    public Sender(int port){
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Listenning for connections on port " +
                    serverSocket.getLocalPort());
            while (true){
                Socket connection = serverSocket.accept();
                System.out.println("connection established with " +
                        connection);
                Thread echoThread = new EchoThread(connection);
                echoThread.start();
            }
        } catch (SocketException e){
            System.err.println(e);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
