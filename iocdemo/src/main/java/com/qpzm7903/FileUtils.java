package com.qpzm7903;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-08 21:59
 */

public class FileUtils {
    public static Collection<File> listFiles(File fPkgDir, String[] strings, boolean recursive) {
        try {
            List<File> collect = Files.walk(Paths.get(fPkgDir.getCanonicalPath())).map(Path::toFile).filter(file -> {
                for (String string : strings) {
                    try {
                        if (file.getCanonicalPath().endsWith(string)) {
                            return true;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return false;
            }).collect(Collectors.toList());
            return collect;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
}
