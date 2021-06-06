package com.qpzm7903.behaviouralpatterns.visitor.filedemo.version1;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-06 21:46
 */

public class PdfFile extends ResourceFile {

    public PdfFile(String filePath) {
        super(filePath);
    }

    public void extract2txt() {
        System.out.println("pdf file to txt");

    }


}
