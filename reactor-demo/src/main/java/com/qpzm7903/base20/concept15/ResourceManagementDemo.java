package com.qpzm7903.base20.concept15;

import java.io.Closeable;
import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * 资源管理与清理演示<br>
 * <p>
 * 典型场景：<br>
 * 1. 数据库连接管理<br>
 * 2. 文件句柄管理<br>
 * 3. 网络连接管理<br>
 */
public class ResourceManagementDemo {
    private final AtomicInteger resourcesCreated = new AtomicInteger(0);
    private final AtomicInteger resourcesClosed = new AtomicInteger(0);
    
    /**
     * 模拟资源类<br>
     * 用于演示资源的创建和清理
     */
    public class Resource implements Closeable {
        private final String name;
        private boolean closed = false;
        
        public Resource(String name) {
            this.name = name;
            resourcesCreated.incrementAndGet();
            System.out.println("Resource created: " + name);
        }
        
        public String getData() {
            if (closed) {
                throw new IllegalStateException("Resource is closed: " + name);
            }
            return "Data from " + name;
        }
        
        @Override
        public void close() {
            if (!closed) {
                closed = true;
                resourcesClosed.incrementAndGet();
                System.out.println("Resource closed: " + name);
            }
        }
        
        public boolean isClosed() {
            return closed;
        }
    }
    
    /**
     * 基础资源管理示例<br>
     * 使用using操作符管理资源
     */
    public Mono<String> basicResourceManagement() {
        return Mono.using(
                () -> new Resource("Basic"),  // 资源创建
                resource -> Mono.just(resource.getData()),  // 资源使用
                Resource::close  // 资源清理
        );
    }
    
    /**
     * 延迟资源清理示例<br>
     * 演示延迟清理资源
     */
    public Mono<String> deferredCleanup() {
        return Mono.using(
                () -> new Resource("Deferred"),
                resource -> Mono.just(resource.getData()),
                Resource::close,
                false  // 延迟清理
        );
    }
    
    /**
     * 多资源管理示例<br>
     * 管理多个相关资源
     */
    public Flux<String> multipleResources() {
        return Flux.using(
                () -> new Resource("Multiple"),
                resource -> Flux.range(1, 3)
                        .map(i -> resource.getData() + " #" + i),
                Resource::close
        );
    }
    
    /**
     * 条件资源清理示例<br>
     * 根据条件决定是否清理资源
     */
    public Mono<String> conditionalCleanup(boolean shouldCleanup) {
        Resource resource = new Resource("Conditional");
        return Mono.just(resource.getData())
                .doFinally(signal -> {
                    if (shouldCleanup) {
                        resource.close();
                    }
                });
    }
    
    /**
     * 异步资源管理示例<br>
     * 在异步环境中管理资源
     */
    public Mono<String> asyncResourceManagement() {
        return Mono.using(
                () -> new Resource("Async"),
                resource -> Mono.just(resource.getData())
                        .subscribeOn(Schedulers.boundedElastic()),
                Resource::close
        );
    }
    
    /**
     * 资源池管理示例<br>
     * 管理资源池中的资源
     */
    public Flux<String> resourcePool(int poolSize) {
        return Flux.range(1, poolSize)
                .map(i -> new Resource("Pool-" + i))
                .flatMap(resource -> Mono.just(resource.getData())
                        .doFinally(signal -> resource.close()));
    }
    
    /**
     * 错误处理资源管理示例<br>
     * 处理资源使用中的错误
     */
    public Mono<String> errorHandlingResource() {
        return Mono.using(
                () -> new Resource("Error"),
                resource -> {
                    if (Math.random() < 0.5) {
                        return Mono.error(new RuntimeException("Random error"));
                    }
                    return Mono.just(resource.getData());
                },
                Resource::close
        ).onErrorResume(e -> Mono.just("Error handled"));
    }
    
    /**
     * 资源重用示例<br>
     * 在多个操作中重用资源
     */
    public Flux<String> resourceReuse() {
        Resource resource = new Resource("Reuse");
        return Flux.range(1, 3)
                .map(i -> resource.getData() + " #" + i)
                .doFinally(signal -> resource.close());
    }
    
    // 获取统计信息
    public int getResourcesCreated() {
        return resourcesCreated.get();
    }
    
    public int getResourcesClosed() {
        return resourcesClosed.get();
    }
    
    // 重置计数器
    public void resetCounters() {
        resourcesCreated.set(0);
        resourcesClosed.set(0);
    }
} 