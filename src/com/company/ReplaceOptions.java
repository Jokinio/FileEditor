package com.company;

import jdk.swing.interop.SwingInterOpUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.xml.sax.SAXException;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceOptions extends JFrame {


    HashMap<String, ArrayList<DataForChanges>> keysWithValues = new HashMap <String, ArrayList<DataForChanges>>();

    public ReplaceOptions(JFileChooser chooser, String newFolderName) throws FileNotFoundException {

                File sourceLocation= new File(chooser.getSelectedFile().getPath());
                String user = System.getProperty("user.name");
                File targetDirectory = new File("/Users/"+user+"/Desktop/"+newFolderName);

                try {
                    // copy the folder with files
                    FileUtils.copyDirectory(sourceLocation, targetDirectory);

                    File [] targetFiles = targetDirectory.listFiles();
                    File [] files = sourceLocation.listFiles();

                    for (int i=0;i<files.length;i++) {
                        boolean flag = false;

                        FileInputStream in = new FileInputStream(files[i]);
                       // FileInputStream in = new FileInputStream("src/main/resources/largeFile.xml");


                        String totalStr2="";
                        StringBuilder totalStr =  new StringBuilder("");
                        Scanner sc = new Scanner(in,"UTF-8");

                        XMLParser parser = new XMLParser();
                        //keysWithValues = parser.parsingData("data.xml");
                        File fileName;
                        try {
                            fileName = new File("data.xml");
                            keysWithValues = parser.parsingData(fileName.getAbsolutePath());
                        } catch (Exception ex) {
                            fileName = new File("src/main/resources/data.xml");
                            keysWithValues = parser.parsingData(fileName.getAbsolutePath());
                        }

                        if (targetFiles[i].exists())
                        {
                            targetFiles[i].delete();
                        }
                        // kanei override kai svinei to content, prepei na allaxtei
                        FileWriter fw = new FileWriter(targetFiles[i],true);
                        int counter=0;

                        System.out.println(" mpainei sth While");
                        while (sc.hasNext()) {

                            counter++;
                            totalStr.append( sc.nextLine() + System.lineSeparator());

                            if(counter>=10000000 || (counter<10000000 && !sc.hasNext()))
                            {

                                for (Map.Entry<String, ArrayList<DataForChanges>> entry : keysWithValues.entrySet()) {

                                    String key = entry.getKey();
                                    ArrayList<DataForChanges> value = entry.getValue();
                                    for (int z = 0; z < value.size(); z++) {

                                        System.out.println(key + ", old: " + value.get(z).getOldString() + " new: " + value.get(z).getNewString());
                                        Pattern pattern = Pattern.compile(key);
                                        Matcher matchKey = pattern.matcher(totalStr);
                                        // Matcher matchKey = pattern.matcher(totalStr);
                                        System.out.println();
                                        System.out.println("the key is being searched");

                                        if (matchKey.find()) { // search for key matches
                                            System.out.println("the key has found");
                                            for (int j = 0; j < value.size(); j++) { // if key found, replace all strings

                                                Pattern replace = Pattern.compile(value.get(z).getOldString());
                                                Matcher matcher2 = replace.matcher(totalStr);

                                                String replaceStr = null;
                                                if (value.get(z).getNewString() != null && !(value.get(z).getNewString()).isEmpty()) {
                                                    replaceStr = value.get(z).getNewString();
                                                } else {
                                                    replaceStr = "";
                                                }
                                                totalStr2 = matcher2.replaceAll(replaceStr);

                                                System.out.println("the replace is done");
                                                flag=true;
                                                break;
                                            }
                                        }
                                        else
                                        {
                                            totalStr2= String.valueOf(totalStr);
                                        }
                                    }
                                    if (flag==true)
                                        break;
                                }

                                fw.write(totalStr2);
                                totalStr.setLength(0);
                            }
                        }
                        in.close();
                        fw.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                } catch (XPathExpressionException ex) {
                    ex.printStackTrace();
                } catch (SAXException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Files changed", "RESULT", JOptionPane.INFORMATION_MESSAGE);
            }
        }