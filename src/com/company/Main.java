package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    private static JButton firstButton;
    private static JLabel label;
    private static JButton button2;
    private static String newFolderName="";
    private static JFileChooser chooser;
    private static boolean flag = false;
    public static void main(String[] args) throws FileNotFoundException {

        firstButton=new JButton("Make Changes");
        firstButton.setFont(new Font("Serif", Font.PLAIN, 20));


        JFrame frame = new JFrame("File Editor");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(2,1));

        JPanel panel = new JPanel();
        panel.setLayout( new FlowLayout(1, 50, 5) );
        JPanel panel2 = new JPanel(null);


        JLabel label1=new JLabel("  Give the name of the new folder: ");
        JTextField tex1=new JTextField();
        JLabel label2=new JLabel("  Choose the folder with the files: ");

        button2 =new JButton("Choose folder");
        button2.setFont(new Font("Serif", Font.PLAIN, 30));
        button2.setBounds(725,150,250,40);
        label1.setFont(new Font("Serif", Font.PLAIN, 30));
        label1.setBounds(250,50,500,30);
        label2.setFont(new Font("Serif", Font.PLAIN, 30));
        label2.setBounds(250,150,500,30);
        tex1.setFont(new Font("Serif", Font.PLAIN, 30));
        tex1.setBounds(700,50,300,40);

        panel2.add(label1);
        panel2.add(tex1);
        panel2.add(label2);
        panel2.add(button2);


        panel.add(firstButton);
       // frame.getContentPane().add(panel);

        //ImageIcon image = new ImageIcon("src/main/resources/pic.jpg");
        ImageIcon image = new ImageIcon(Main.class.getResource("/src/main/resources/pic.jpg"));

        label=new JLabel(image);
        panel.add(label);


        frame.add(panel);
        frame.add(panel2);
        frame.setSize(1377,860);
        //frame.pack();
        frame.setLocationRelativeTo(null);

        System.out.println();
        System.out.println();


        button2.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {

                flag=false;
                button2.setText("Choose folder");
                chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this folder: " +
                            chooser.getSelectedFile().getName());
                    System.out.println(button2.getText());
                    button2.setText(chooser.getSelectedFile().getName()+" \u2713");
                    flag = true;
                }
            }}
        );

            // button click
            firstButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        newFolderName = tex1.getText();

                        if (flag && !newFolderName.equals(null) && !newFolderName.equals("")) {
                            //JOptionPane.showMessageDialog(null, "New folder name  OR  files(folder) not given", String.valueOf(flag), JOptionPane.INFORMATION_MESSAGE);

                            ReplaceOptions a = new ReplaceOptions(chooser, newFolderName);
                        } else {
                            JOptionPane.showMessageDialog(null, "New folder name  OR  files(folder) not given", "Failed", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            );
    }
}