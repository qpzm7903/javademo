package com.qpzm7903.base20.concept02;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * 背压(Backpressure)的流量控制原理演示
 * 
 * 典型场景：日志处理
 * - 命令式：一次性读取所有日志，可能导致OOM
 * - 响应式：通过背压控制日志处理速率
 * 
 * 背压策略：
 * 1. BUFFER - 缓存多余的数据
 * 2. DROP - 丢弃多余的数据
 * 3. LATEST - 只保留最新的数据
 * 4. ERROR - 出现背压时报错
 */
public class BackpressureDemo {
    
    /**
     * 命令式日志处理
     * 问题：无法控制内存使用
     */
    public void processLogsImperative(String[] logs) {
        for (String log : logs) {
            processLog(log);  // 处理每条日志
        }
    }
    
    /**
     * 响应式日志处理 - BUFFER策略
     * 特点：使用缓冲区存储未处理的数据
     */
    public Flux<String> processLogsWithBuffer(Flux<String> logs) {
        return logs
            .onBackpressureBuffer(1000)  // 设置缓冲区大小
            .publishOn(Schedulers.boundedElastic())
            .doOnNext(this::processLog);
    }
    
    /**
     * 响应式日志处理 - DROP策略
     * 特点：丢弃无法处理的数据
     */
    public Flux<String> processLogsWithDrop(Flux<String> logs) {
        return logs
            .onBackpressureDrop(dropped -> 
                System.out.println("Dropped log: " + dropped))
            .publishOn(Schedulers.boundedElastic())
            .doOnNext(this::processLog);
    }
    
    /**
     * 响应式日志处理 - LATEST策略
     * 特点：只保留最新数据
     */
    public Flux<String> processLogsWithLatest(Flux<String> logs) {
        return logs
            .onBackpressureLatest()
            .publishOn(Schedulers.boundedElastic())
            .doOnNext(this::processLog);
    }
    
    /**
     * 响应式日志处理 - ERROR策略
     * 特点：出现背压时报错
     */
    public Flux<String> processLogsWithError(Flux<String> logs) {
        return logs
            .onBackpressureError()
            .publishOn(Schedulers.boundedElastic())
            .doOnNext(this::processLog);
    }
    
    private void processLog(String log) {
        try {
            // 模拟耗时处理
            Thread.sleep(100);
            System.out.println("Processing log: " + log);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
} 