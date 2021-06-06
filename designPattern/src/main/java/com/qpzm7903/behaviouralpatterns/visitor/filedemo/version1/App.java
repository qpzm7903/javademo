package com.qpzm7903.behaviouralpatterns.visitor.filedemo.version1;

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

        resourceFiles.forEach(ResourceFile::extract2txt);

    }
}
