package com.javarush.task.task31.task3112;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/* 
Загрузчик файлов
*/

public class Solution {

    public static void main(String[] args) throws IOException {
        Path passwords = downloadFile("https://javarush.ru/testdata/secretPasswords.txt", Paths.get("./4.JavaCollections/input"));

        for (String line : Files.readAllLines(passwords)) {
            System.out.println(line);
        }
    }

    public static Path downloadFile(String urlString, Path downloadDirectory) throws IOException {
        URL url = new URL(urlString);

        try (InputStream inputStream = url.openStream()) {
            String[] urlArray = urlString.split("/");
            int urlArrayLength = urlArray.length;
            String fileName = urlArray[urlArrayLength - 1];

            Path tempFile = Files.createTempFile("_jr-", "-file");
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            Path targetFile = Files.move(
                    tempFile,
                    Paths.get(downloadDirectory.toString() + "/" + fileName),
                    StandardCopyOption.REPLACE_EXISTING
            );
            return targetFile;
        }
    }
}
