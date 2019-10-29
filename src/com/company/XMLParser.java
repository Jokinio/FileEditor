package com.company;

import org.springframework.util.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class XMLParser {

    public XMLParser () {

    }
    public HashMap <String, ArrayList<DataForChanges>> parsingData(String XMLFileName) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document doc = builder.parse(XMLFileName);
        doc.getDocumentElement().normalize();
        XPath xPath = XPathFactory.newInstance().newXPath();
        Node node = doc.getDocumentElement();

        HashMap <String, ArrayList<DataForChanges>> keysWithValues = new HashMap <>();
        ArrayList <DataForChanges> list;

        // Nodelist for all the keys
        NodeList keys = (NodeList) xPath.compile("key").evaluate(node, XPathConstants.NODESET);
        for (int k=0;k<keys.getLength();k++) {

            list = new ArrayList<DataForChanges>();
            String keyValue = keys.item(k).getAttributes().getNamedItem("data").getNodeValue();

            // Nodelist for strings
            NodeList oldStrings = (NodeList) xPath.compile("change").evaluate(keys.item(k), XPathConstants.NODESET);
            for (int i=0;i<oldStrings.getLength();i++) {
                Node newNode = oldStrings.item(i);
                DataForChanges data = new DataForChanges();
                String oldString = (String) xPath.compile("oldString").evaluate(newNode, XPathConstants.STRING);
                if (StringUtils.hasText(oldString)) {
                    data.setOldString(oldString);
                }
                String newString = (String) xPath.compile("newString").evaluate(newNode, XPathConstants.STRING);
                if (StringUtils.hasText(newString)) {
                    data.setNewString(newString);
                }
                list.add(data);
            }

            keysWithValues.put(keyValue,list);
        }

        keysWithValues.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " value of old -->" + entry.getValue().iterator().next().getOldString());
            System.out.println(entry.getKey() + " value of new -->" + entry.getValue().iterator().next().getNewString());
        });

        return keysWithValues;
    }
}
