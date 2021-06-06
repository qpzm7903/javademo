package com.qpzm7903.behaviouralpatterns.visitor.filedemo.version2;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-06 21:49
 */

public class App {
    public static void main(String[] args) {
        List<ResourceFile> resourceFiles = new ArrayList<>();
        resourceFiles.add(new PdfFile("a.pdf"));
        resourceFiles.add(new WordFile("b.word"));
        resourceFiles.add(new PptFile("c.ppt"));
        Extractor extractor = new Extractor();

        for (ResourceFile resourceFile : resourceFiles) {
            // 希望根据不同的类型调用不同的方法
            // 但是实际的类型是在运行是决定的
            // 所以这里会编译报错
//            extractor.extract2txt(resourceFile);

            // 这里强制的类型转换比较硬编码，也不好扩展。
            if (resourceFile instanceof PdfFile) {
                extractor.extract2txt((PdfFile) resourceFile);
                continue;
            }
            if (resourceFile instanceof WordFile) {
                extractor.extract2txt((WordFile) resourceFile);
                continue;
            }

            if (resourceFile instanceof PptFile) {
                extractor.extract2txt((PptFile) resourceFile);
                continue;
            }


        }

    }
}
