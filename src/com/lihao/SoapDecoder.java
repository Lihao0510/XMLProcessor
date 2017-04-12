package com.lihao;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

/**
 * Created by lihao on 2017/4/12.
 */
public class SoapDecoder {

    public static void main(String[] args) {
        String fileName = "sourceXML";
        String filePath = Main.class.getResource("/" + fileName + ".tld").getPath();
        File xmlFile = new File(filePath);
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(xmlFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            System.out.println("public String " + element.getName() + ";");
        }
        System.out.println("");
        for (Element element : elements) {
            System.out.println(element.getName() + " = soapObject.getPropertySafelyAsString(\"" + element.getName() + "\");");
        }


    }

}
