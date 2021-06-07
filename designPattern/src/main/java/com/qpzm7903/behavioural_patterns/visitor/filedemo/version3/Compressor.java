package com.qpzm7903.behavioural_patterns.visitor.filedemo.version3;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-06 22:09
 */

public class Compressor {
    public void compressor(PdfFile pdfFile) {
        System.out.println("compressor pdf file");
    }

    public void compressor(WordFile wordFile) {
        System.out.println("compressor word file");
    }

    public void compressor(PptFile pptFile) {
        System.out.println("compressor ppt file");
    }
}
