package com.company;


import com.company.reveiver.*;
import com.company.sender.FileDetach;
import com.company.sender.FileDetach2;
import com.company.sender.FileDetach3;

public class Main {

    public static void main(String[] args) {
//        FileDetach detach = new FileDetach("./src/com/company/test.jpg");
//        FileOut fileOut = new FileOut(detach.getLastPart(), "./src/com/company/new.jpg");

        new FileDetach3("./src/com/company/test.jpg");
        new FileCombine("./src/com/company/test.jpg.", "newtest.jpg");

//        new Sender(8888);
//        String[] s = new String[1];
//        s[0] = "127.0.0.1";
//        new Receiver(s, 8888, "test.jpg");
	// write your code here
//        new FileDetach2("./src/com/company/test.jpg");
//        new FileCombine("./src/com/company/test.jpg0", "newTest.jpg");


//        int part = 190;
//        String s = String.format("%8s", Integer.toString(part)).replace(' ', '0');
//        byte[] b = s.getBytes();
//        String s1 = new String(b);
//        int i = Integer.parseInt(s1);
//        System.out.println(i);
    }
}
