package com.lihao;

import java.io.*;

/**
 * Created by lihao on 2017/3/2.
 */
public class FileGenerator {

    public static void main(String[] args) {
        int count = 0;
        System.out.println("开始转换");
        System.out.println("——————————————————————————————————————————————————");
        StringBuilder input = new StringBuilder();
        String filePath = Main.class.getResource("/source.txt").getPath();
        FileInputStream inputStream;
        BufferedReader reader;
        try {
            inputStream = new FileInputStream(filePath);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                input.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = input.toString().replaceAll(" ", "") + " ";
        char[] inputArray = result.toCharArray();
        for (int i = 0; i < inputArray.length - 1; i++) {
            if (inputArray[i] == '{') {
                if (inputArray[i + 1] == '}' || inputArray[i + 1] == ';') {
                    System.out.print(inputArray[i]);
                    continue;
                } else {
                    count++;
                    System.out.print(inputArray[i]);
                    System.out.print("\n");
                    for (int j = 0; j < count; j++) {
                        System.out.print("\t");
                    }
                }
            } else if (inputArray[i] == ';') {
                if (inputArray[i - 1] == '}' && inputArray[i - 2] != '{') {
                    count--;
                } else
                    System.out.print(inputArray[i]);
                System.out.print("\n");
                for (int j = 0; j < count; j++) {
                    System.out.print("\t");
                }
            } else if (inputArray[i] == '}') {
                if (inputArray[i + 1] == ';') {
                    System.out.print(inputArray[i]);
                } else {
                    System.out.print("\n");
                    for (int j = 0; j < count; j++) {
                        System.out.print("\t");
                    }
                    System.out.print(inputArray[i]);
                }
            } else {
                System.out.print(inputArray[i]);
            }
        }
    }
}
