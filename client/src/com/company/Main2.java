package com.company;

import com.company.reveiver.Receiver;
import com.company.sender.Sender;

public class Main2 {
    public static void main(String[] args) {
        new Sender(8888);
        new MainClient("192.168.2.167", 1235);

//        new Receiver()
//        new Sender(8888);
//        String[] s = new String[1];
//        s[0] = "192.168.2.185";
//        new Receiver(s, 8888,  "test.jpg", "newtest2.jpg");
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
