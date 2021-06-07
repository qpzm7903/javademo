package com.qpzm7903.behavioural_patterns.visitor.filedemo.version1;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-06 21:46
 */

public class PptFile extends ResourceFile {

    public PptFile(String filePath) {
        super(filePath);
    }

    public void extract2txt() {
        System.out.println("ppt file to txt");

    }


}
