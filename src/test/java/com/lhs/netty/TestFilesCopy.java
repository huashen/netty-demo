package com.lhs.netty;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * TestFilesCopy
 *
 * 拷贝多级目录
 *
 * @author longhuashen
 * @since 2021-03-31
 */
@Slf4j
public class TestFilesCopy {

    public static void main(String[] args) throws IOException {
        String source = "/Users/longhuashen/Downloads/讲义打包 2";
        String target = "/Users/longhuashen/Downloads/讲义打包 3";

        Files.walk(Paths.get(source)).forEach(path -> {
            try {
                String targetName = path.toString().replace(source, target);

                if (Files.isDirectory(path)) {
                    Files.createDirectory(Paths.get(targetName));
                } else if (Files.isRegularFile(path)) {
                    Files.copy(path, Paths.get(targetName));
                }
            } catch (IOException e) {
                log.error("错误：{}", e);
            }
        });
    }
}
