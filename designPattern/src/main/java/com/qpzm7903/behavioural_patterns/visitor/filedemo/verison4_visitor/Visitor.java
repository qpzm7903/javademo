package com.qpzm7903.behavioural_patterns.visitor.filedemo.verison4_visitor;

public interface Visitor {
    void visit(PptFile pptFile);
    void visit(WordFile wordFile);
    void visit(PdfFile pdfFile);
}
