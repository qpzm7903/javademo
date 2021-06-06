package com.qpzm7903.behaviouralpatterns.visitor.filedemo.version3;

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
            resourceFile.accept(extractor);
        }

        Compressor compressor = new Compressor();

        for (ResourceFile resourceFile : resourceFiles) {
            resourceFile.accept(compressor);

        }

    }
}
