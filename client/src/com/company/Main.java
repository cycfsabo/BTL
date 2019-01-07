package com.company;


import com.company.reveiver.*;
import com.company.sender.FileDetach;
import com.company.sender.FileDetach2;
import com.company.sender.FileDetach3;
import com.company.sender.Sender;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setContentPane(new Display().getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
//        frame.pack();
        frame.setVisible(true);


//        FileDetach detach = new FileDetach("./src/com/company/test.jpg");
//        FileOut fileOut = new FileOut(detach.getLastPart(), "./src/com/company/new.jpg");

//        new FileDetach3("./src/com/company/test.mp4");
//        new FileCombine("./src/com/company/test.mp4.", "newtest.mp4");

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
