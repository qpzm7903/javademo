package com.qpzm7903.behavioural_patterns.visitor.filedemo.verison4_visitor;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-06 22:09
 */

public class Compressor implements Visitor {
    public void visit(PdfFile pdfFile) {
        System.out.println("compressor pdf file");
    }

    public void visit(WordFile wordFile) {
        System.out.println("compressor word file");
    }

    public void visit(PptFile pptFile) {
        System.out.println("compressor ppt file");
    }
}
