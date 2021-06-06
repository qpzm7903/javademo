package com.qpzm7903.behaviouralpatterns.visitor.filedemo.verison4_visitor;

public class Extractor implements Visitor {
    public void visit(PdfFile pdfFile) {
        System.out.println("pdf file to txt");
    }

    public void visit(WordFile wordFile) {
        System.out.println("world file to txt");
    }

    public void visit(PptFile pptFile) {
        System.out.println("ppt file to txt");
    }
}
