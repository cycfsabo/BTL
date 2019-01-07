package com.company.reveiver;

import java.io.*;
import java.net.*;

public class Client {
    /**
     * Class khong con duoc su dung
     * -->Class update: Receiver
     *
     */
    private Socket sockfd = null;
    private DataInputStream input = null;;
    private DataOutputStream out = null;

    public Client(String addr, int port)
    {
        try{
            sockfd = new Socket(addr, port);
            System.out.print("Connected\n");

            input = new DataInputStream(System.in);
            out = new DataOutputStream(sockfd.getOutputStream());
        }
        catch (UnknownHostException u){
            System.out.print(u);
        }
        catch (IOException i){
            System.out.print(i);
        }

        String line = "";

        while (!line.equals("QUIT")){
            try{
                System.out.print("\nClient: ");
                line = input.readLine();
//                System.out.print("\n" + line);
                out.writeUTF(line);
                out.flush();
            }
            catch (IOException i){
                System.out.print(i);
            }
        }

//        try{
//            out.writeUTF("hello server\n");
//            out.flush();
//        }
//        catch (IOException i){
//            System.out.print(i);
//        }

        try{
            input.close();
            out.close();
            sockfd.close();
        }
        catch (IOException i){
            System.out.print(i);
        }
    }
}
