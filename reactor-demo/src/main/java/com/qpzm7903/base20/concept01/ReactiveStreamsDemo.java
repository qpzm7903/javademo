package com.qpzm7903.base20.concept01;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.core.publisher.Flux;

/**
 * Reactive Streams规范四要素演示
 * 
 * 典型场景：文件读取
 * - 命令式：一次性读取整个文件到内存
 * - 响应式：按需读取文件内容，通过背压控制内存使用
 */
public class ReactiveStreamsDemo {
    
    /**
     * 命令式文件读取
     */
    public String readFileImperative(String path) {
        // 一次性读取整个文件
        StringBuilder content = new StringBuilder();
        // ... 读取文件逻辑 ...
        return content.toString();
    }
    
    /**
     * 响应式文件读取
     * Publisher: 文件读取器
     * Subscriber: 文件处理器
     * Subscription: 控制读取速度
     * Processor: 可选的文件内容转换
     */
    public Publisher<String> readFileReactive(String path) {
        return Flux.create(sink -> {
            // 创建自定义Publisher
            FileReaderPublisher publisher = new FileReaderPublisher(path);
            // 创建自定义Subscriber
            FileProcessorSubscriber subscriber = new FileProcessorSubscriber();
            // Publisher和Subscriber通过Subscription建立连接
            publisher.subscribe(subscriber);
        });
    }
}

/**
 * 自定义Publisher：文件读取器
 */
class FileReaderPublisher implements Publisher<String> {
    private final String path;
    
    public FileReaderPublisher(String path) {
        this.path = path;
    }
    
    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        // 创建Subscription，控制文件读取速度
        FileReaderSubscription subscription = new FileReaderSubscription(subscriber, path);
        subscriber.onSubscribe(subscription);
    }
}

/**
 * 自定义Subscription：控制文件读取速度
 */
class FileReaderSubscription implements Subscription {
    private final Subscriber<? super String> subscriber;
    private final String path;
    private boolean cancelled = false;
    
    public FileReaderSubscription(Subscriber<? super String> subscriber, String path) {
        this.subscriber = subscriber;
        this.path = path;
    }
    
    @Override
    public void request(long n) {
        if (cancelled) return;
        
        try {
            // 根据请求的数量n，读取相应行数的文件内容
            for (int i = 0; i < n && !cancelled; i++) {
                String line = readNextLine();
                if (line != null) {
                    subscriber.onNext(line);
                } else {
                    subscriber.onComplete();
                    break;
                }
            }
        } catch (Exception e) {
            subscriber.onError(e);
        }
    }
    
    @Override
    public void cancel() {
        this.cancelled = true;
    }
    
    private String readNextLine() {
        // 实际的文件读取逻辑
        return null;
    }
}

/**
 * 自定义Subscriber：文件处理器
 */
class FileProcessorSubscriber implements Subscriber<String> {
    private Subscription subscription;
    
    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        subscription.request(1); // 请求第一行
    }
    
    @Override
    public void onNext(String line) {
        // 处理文件内容
        processLine(line);
        subscription.request(1); // 请求下一行
    }
    
    @Override
    public void onError(Throwable t) {
        // 错误处理
    }
    
    @Override
    public void onComplete() {
        // 处理完成
    }
    
    private void processLine(String line) {
        // 实际的行处理逻辑
    }
} 