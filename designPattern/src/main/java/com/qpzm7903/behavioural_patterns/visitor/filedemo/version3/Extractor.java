package com.qpzm7903.behavioural_patterns.visitor.filedemo.version3;

public class Extractor {
    public void extract2txt(PdfFile pdfFile) {
        System.out.println("pdf file to txt");
    }

    public void extract2txt(WordFile wordFile) {
        System.out.println("world file to txt");
    }

    public void extract2txt(PptFile pptFile) {
        System.out.println("ppt file to txt");
    }
}
