package com.javarush.task.task20.task2003;

import java.io.*;
import java.util.*;

/* 
Знакомство с properties
*/
public class Solution {
    public static Map<String, String> properties = new HashMap<>();

    public void fillInPropertiesMap() {
        try (Scanner in = new Scanner(System.in); FileInputStream fis = new FileInputStream(in.nextLine())) {
            load(fis);
        } catch (Exception e) {
        }
    }

    public void save(OutputStream outputStream) throws Exception {
        Properties prop = new Properties();

        for (Map.Entry<String, String> pair : properties.entrySet()) {
            prop.setProperty(pair.getKey(), pair.getValue());
        }
        prop.store(outputStream, "My comment");
    }

    public void load(InputStream inputStream) throws Exception {
        Properties prop = new Properties();
        prop.load(inputStream);

        for (Map.Entry<Object, Object> prop1 : prop.entrySet()) {
            properties.put((String) prop1.getKey(), (String) prop1.getValue());
        }
    }

    public static void main(String[] args) {

    }
}