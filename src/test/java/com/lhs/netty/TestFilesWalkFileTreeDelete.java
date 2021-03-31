package com.lhs.netty;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * TestFilesWalkFileTreeDelete
 *
 * 删除多级目录
 *
 * @author longhuashen
 * @since 2021-03-31
 */
public class TestFilesWalkFileTreeDelete {

    public static void main(String[] args) throws IOException {
        Files.walkFileTree(Paths.get("/Users/longhuashen/Downloads/LeetCode刷题手册 2"), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }
}
