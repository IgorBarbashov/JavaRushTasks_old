package com.javarush.task.task31.task3106;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Разархивируем файл
*/

class SequenceEnumeration implements Enumeration<BufferedInputStream> {
    private Queue<BufferedInputStream> inputStreams;

    public SequenceEnumeration(Queue<BufferedInputStream> inputStreams) {
        this.inputStreams = inputStreams;
    }

    @Override
    public boolean hasMoreElements() {
        return !inputStreams.isEmpty();
    }

    @Override
    public BufferedInputStream nextElement() {
        return this.inputStreams.remove();
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        Queue<BufferedInputStream> inputStreams = new LinkedList<>();

        for (int i = 1; i < args.length; i++) {
            inputStreams.add(new BufferedInputStream(new FileInputStream(args[i])));
        }

        Path tempFile = Files.createTempFile("jr-", ".zip");

        try (
                SequenceInputStream inputStream = new SequenceInputStream(new SequenceEnumeration(inputStreams));
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile.toString()));
        ) {
            int symbol;
            while ((symbol = inputStream.read()) != -1) {
                outputStream.write(symbol);
            }
        }

        try (
                ZipInputStream zip = new ZipInputStream(new FileInputStream(tempFile.toString()));
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(args[0]));
        ) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                int symbol;
                while ((symbol = zip.read()) != -1) {
                    outputStream.write(symbol);
                }
                zip.closeEntry();
            }
        }
    }
}
