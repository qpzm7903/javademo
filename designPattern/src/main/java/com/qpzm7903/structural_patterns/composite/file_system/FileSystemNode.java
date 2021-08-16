package com.qpzm7903.structural_patterns.composite.file_system;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-08 07:05
 */

public abstract class FileSystemNode {
    protected String path;

    public FileSystemNode(String path) {
        this.path = path;
    }

    public abstract int countNumOfFiles();

    public abstract long countSizeOfFiles();

    public String getPath() {
        return path;
    }
}
