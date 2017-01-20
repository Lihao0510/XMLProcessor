package com.lihao;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<String> contentList;
    public static List<String> fileList;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        fileList = new ArrayList<>();
        String filePath = Main.class.getResource("/" + fileName + ".xml").getPath();
        File file = new File(filePath);
        System.out.println(file.getAbsolutePath());
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(file);
            Element root = doc.getRootElement();
            getAllIncludeTag(root);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        System.out.println(fileList);
        getAllFile(fileName);
        for (int i = 0; i < fileList.size(); i++) {
            getAllFileByAppend(fileList.get(i));
        }
    }

    private static void getAllFile(String path){
        String filePath = Main.class.getResource("/" + path + ".xml").getPath();
        String outputPath = "output.txt";
        File file = new File(filePath);
        System.out.println(file.getAbsolutePath());
        SAXReader reader = new SAXReader();
        contentList = new ArrayList<>();
        try {
            Document doc = reader.read(file);
            Element root = doc.getRootElement();
            getAllElement(root);
            FileUtil.writeFile(outputPath, contentList, false);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private static void getAllFileByAppend(String path){
        String filePath = Main.class.getResource("/" + path + ".xml").getPath();
        String outputPath = "output.txt";
        File file = new File(filePath);
        System.out.println(file.getAbsolutePath());
        SAXReader reader = new SAXReader();
        contentList = new ArrayList<>();
        try {
            Document doc = reader.read(file);
            Element root = doc.getRootElement();
            getAllElement(root);
            FileUtil.writeFile(outputPath, contentList, true);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private static void getAllElement(Element root) {
        Iterator it = root.elementIterator();
        while (it.hasNext()){
            Element subElement = (Element) it.next();
            String viewName = subElement.getName();
            String viewID = subElement.attributeValue("id");
            if (viewID != null && viewID.startsWith("@+id/")) {
                System.out.println(viewName.substring(viewName.lastIndexOf(".") + 1) + " ID: R.id." + viewID.substring(5));
                contentList.add("@BindView(R.id." + viewID.substring(5) + ")\n" + viewName.substring(viewName.lastIndexOf(".") + 1) + " " + viewID.substring(5) + ";\n");
            }
            getAllElement(subElement);
        }
    }

    private static void getAllIncludeTag(Element root){
        Iterator it = root.elementIterator();
        while (it.hasNext()){
            Element subElement = (Element) it.next();
            String tagName = subElement.getName();
            if(tagName.equals("include")){
                String layout = subElement.attributeValue("layout");
                fileList.add(layout.substring(layout.lastIndexOf("/") + 1));
            }
        }
    }
}
