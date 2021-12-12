package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.IOException;
import java.util.*;

/* 
Находим все файлы
*/

public class Solution {
    public static List<String> getFileTree(String root) throws IOException {
        Queue<File> folders = new LinkedList<>();
        folders.add(new File(root));
        List<String> filesList = new ArrayList<>();

        while (folders.size() != 0) {
            File folder = folders.remove();

            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    folders.add(file);
                } else {
                    filesList.add(file.getPath());
                }
            }

        }

        return filesList;
    }

    public static void main(String[] args) throws IOException {
        String root = "./";
        List<String> list = getFileTree(root);
    }
}
