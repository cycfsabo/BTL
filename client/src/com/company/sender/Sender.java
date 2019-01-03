package com.company.sender;

import java.net.*;
import java.io.*;
import java.util.*;

public class Sender {
    public Sender(int port){
        try{
            ServerSocket serverSocket = new ServerSocket(port, 1024);
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
