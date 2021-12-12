package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.*;

/* 
Проход по дереву файлов
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        final int MAX_FILE_SIZE = 50;

        String inputPathArg = args[0];
        File originInputPath = new File(inputPathArg);
        if (!FileUtils.isExist(originInputPath)) {
            throw new FileNotFoundException("Input path not found");
        }

        String outputFileArg = args[1];
        File originResultFile = new File(outputFileArg);
        File originResultPath = new File(originResultFile.getParent());
        if (!FileUtils.isExist(originResultPath)) {
            originResultPath.mkdir();
        }
        if (!FileUtils.isExist(originResultFile)) {
            originResultFile.createNewFile();
        }

        String resultFileName = "allFilesContent.txt";
        File resultFile = new File(originResultPath + "/" + resultFileName);
        if (FileUtils.isExist(resultFile)) {
            resultFile.delete();
        }

        FileUtils.renameFile(originResultFile, resultFile);

        PriorityQueue<File> pathsQueue = new PriorityQueue<>();
        pathsQueue.add(originInputPath);
        List<File> inputFilesList = new ArrayList<>();
        while (pathsQueue.size() != 0) {
            getFolderFilesList(pathsQueue.remove(), inputFilesList, pathsQueue, MAX_FILE_SIZE);
        }

        if (inputFilesList.size() > 0) {
            Collections.sort(inputFilesList, new FileComparator());
            try (FileWriter writer = new FileWriter(resultFile)) {
                int symbol;
                for (File file : inputFilesList) {
                    try (FileReader reader = new FileReader(file);) {
                        while ((symbol = reader.read()) != -1) {
                            writer.write(symbol);
                        }
                        writer.write("\n");
                    }
                }
            }
        }
    }

    static void getFolderFilesList(File path, List<File> filesList, Queue<File> pathsQueue, int maxFileSize) {
        for (File file : path.listFiles()) {
            if (file.isDirectory()) {
                pathsQueue.add(file);
            }
            else if (file.length() <= maxFileSize) {
                filesList.add(file);
            }
        }
    }
}

class FileComparator implements Comparator<File> {

    @Override
    public int compare(File file1, File file2) {
        return file1.getName().compareTo(file2.getName());
    }
}
