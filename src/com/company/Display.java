package com.company;

import com.company.reveiver.Receiver;
import com.company.sender.Sender;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class Display extends JFrame{
    private JPanel panel1;
    private JTextField searchField;
    private JButton searchButton;
    private JLabel appName;
    private JScrollPane listScrollPane;
    private JTextPane listPane;
    private JButton downloadButton;
    private JList listFile;
    private JButton synchButton;
    private JButton quitButton;
    private JTextField fileToDown;
    private JTextField newFileName;
    private JLabel notification;
    private MainClient mainClient;

    public Display(){
        mainClient = new MainClient("192.168.2.167", 1235);
        new Sender(8888);
        this.setup();
    }

    public void setup(){
        panel1.setVisible(true);
        this.appName.setText("Lam Dao va nhung nguoi ban");
        this.searchField.setText("");
        this.fileToDown.setText("");
        this.notification.setText("");
        this.setListFile(mainClient.getFileListArray());

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newFileName.getText() == null || newFileName.getText().trim().isEmpty()) {
                    notification.setText("Opp! Please try new file name");
                } else {
                    String fileName = fileToDown.getText();
                    String[] ipAddr = mainClient.getListIPToClientArray();
                    String newFileName = fileName;
                    new Receiver(ipAddr, 8888, fileName, newFileName);
                }
            }
        });

        listFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                searchField.setText(listFile.getSelectedValue().toString());
                fileToDown.setText(listFile.getSelectedValue().toString());
                newFileName.setText(listFile.getSelectedValue().toString());
            }
        });

        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });

        synchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
    }

    public void setListFile(String[] fileList){
        Vector list = new Vector<String>(Arrays.asList(fileList));
        listFile.setListData(list);
    }

    public JPanel getPanel(){
        return this.panel1;
    }
}
