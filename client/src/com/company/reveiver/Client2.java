package com.company.reveiver;

import java.io.*;
import java.net.*;

public class Client2 {
    private static Socket clientSocket = null;
    private static PrintStream os = null;
    private static DataInputStream is = null;

    private static BufferedReader inputLine = null;
    private static boolean closed = false;

    public static void main(String[]args) {

        int portNumber = 8888;

        String host = "localhost";

        if(args.length < 2 ) {
         //   System.out.print();
        }


    }
}
