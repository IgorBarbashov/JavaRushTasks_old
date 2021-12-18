package com.javarush.task.task31.task3113;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.Scanner;

import static java.lang.Integer.MAX_VALUE;

/* 
Что внутри папки?
*/

public class Solution extends SimpleFileVisitor<Path> {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String pathName = in.nextLine();
        Path rootPath = Paths.get(pathName);

        if (!Files.isDirectory(rootPath)) {
            System.out.println(pathName + " - не папка");
            return;
        }

        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        final Solution fileVisitor = new Solution();
        Files.walkFileTree(rootPath, options, MAX_VALUE, fileVisitor);

        int foldersCount = fileVisitor.getFoldersCount();
        int filesCount = fileVisitor.getFilesCount();
        int bytesCount = fileVisitor.getBytesCount();

        System.out.println("Всего папок - " + (foldersCount - 1));
        System.out.println("Всего файлов - " + filesCount);
        System.out.println("Общий размер - " + bytesCount);
    }

    private int foldersCount;
    private int filesCount;
    private int bytesCount; // общее количество байт, которое хранится в директории

    public int getFoldersCount() { return this.foldersCount; }
    public int getFilesCount() { return this.filesCount; }
    public int getBytesCount() { return this.bytesCount; }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes basicFileAttributes) throws IOException {
        if (Files.isRegularFile(file)) {
            this.filesCount ++;
            this.bytesCount += Files.readAllBytes(file).length;
        }
        return super.visitFile(file, basicFileAttributes);
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (Files.isDirectory(dir)) {
            this.foldersCount++;
        }
        return super.postVisitDirectory(dir, exc);
    }
}
