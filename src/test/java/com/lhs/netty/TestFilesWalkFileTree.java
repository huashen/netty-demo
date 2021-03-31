package com.lhs.netty;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TestFilesWalkFileTree
 *
 * 遍历目录
 *
 * @author longhuashen
 * @since 2021-03-31
 */
public class TestFilesWalkFileTree {

    public static void main(String[] args) throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("==>" + dir);
                dirCount.incrementAndGet();
                return super.postVisitDirectory(dir, exc);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });

        System.out.println(dirCount.get());
        System.out.println(fileCount.get());
    }
}
