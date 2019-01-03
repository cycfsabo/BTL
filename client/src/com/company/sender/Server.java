package com.company.sender;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket sockfd = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private String buffer[] = new String[1024];

    public Server(int port){
        try{
            server = new ServerSocket(port);
            System.out.print("listening...\n");
            sockfd = server.accept();
            System.out.print("connected\n");
            in = new DataInputStream(
                    new BufferedInputStream(sockfd.getInputStream()));

            String line = "";

            while (!line.equals("QUIT")){
                try{
                    line = in.readUTF();
                    System.out.print("\nserver: " + line);
                }
                catch (IOException i){
                    System.out.print(i);
                }
            }
            System.out.print("\n                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                close connection");
            sockfd.close();
            in.close();
        }
        catch (IOException i){
            System.out.print(i);
        }
    }

    private void sender(String fileName){
        File f = new File("file2Send/"+fileName);

    }
}
