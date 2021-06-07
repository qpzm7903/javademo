package com.qpzm7903.behavioural_patterns.visitor.filedemo.version3;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-06 21:45
 */

public abstract class ResourceFile {
    protected String filePath;

    public ResourceFile(String filePath) {
        this.filePath = filePath;
    }

    abstract public void accept(Extractor extractor);

    abstract public void accept(Compressor compressor);
}
