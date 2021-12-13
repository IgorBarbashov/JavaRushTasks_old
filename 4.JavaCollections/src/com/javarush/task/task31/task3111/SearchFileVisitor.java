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
    public String partOfName;
    private String partOfContent;
    private Integer minSize;
    private Integer maxSize;
    private List<Path> foundFiles = new ArrayList<>();

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public void setMinSize( int minSize) {
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
        byte[] content = Files.readAllBytes(file); // размер файла: content.length
        boolean isFileValid = true;

        if (this.partOfName != null) {
            System.out.println("validate partOfName: " + this.partOfName);
        }

        if (this.partOfContent != null) {
            System.out.println("validate partOfContent: " + this.partOfContent);
        }

        if (this.maxSize != null) {
            if (content.length > this.maxSize) {
                isFileValid = false;
            }
            System.out.println("validate maxSize: " + this.maxSize);
        }
        if (this.minSize != null) {
            if (content.length < this.minSize) {
                isFileValid = false;
            }
            System.out.println("validate minSize: " + this.minSize);
        }

        if (isFileValid) {
            this.foundFiles.add(file);
        }

        return super.visitFile(file, attrs);
    }
}
