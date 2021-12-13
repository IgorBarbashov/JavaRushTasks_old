package com.javarush.task.task31.task3111;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {
    private String partOfName;
    private String partOfContent;
    private int minSize;
    private int maxSize;
    private List<Path> foundFiles = new ArrayList<>();

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public List<Path> getFoundFiles() {
        return this.foundFiles;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] content = Files.readAllBytes(file);
        boolean isFileValid = true;

        if (isFileValid && this.maxSize != 0) {
            if (content.length > this.maxSize) {
                isFileValid = false;
            }
        }

        if (isFileValid && this.minSize != 0) {
            if (content.length < this.minSize) {
                isFileValid = false;
            }
        }

        if (isFileValid && this.partOfName != null) {
            if (!file.getFileName().toString().contains(this.partOfName)) {
                isFileValid = false;
            }
        }

        if (isFileValid && this.partOfContent != null) {
            List<String> textContent = Files.readAllLines(file);
            boolean isContentValid = false;
            for (String line : textContent) {
                if (line.contains(this.partOfContent)) {
                    isContentValid = true;
                }
            }
            if (!isContentValid) {
                isFileValid = false;
            }
        }

        if (isFileValid) {
            this.foundFiles.add(file);
        }

        return super.visitFile(file, attrs);
    }
}
