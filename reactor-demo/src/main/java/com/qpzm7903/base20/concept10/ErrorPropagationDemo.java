package com.qpzm7903.base20.concept10;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 错误信号传播机制演示<br>
 * <p>
 * 典型场景：<br>
 * 1. 远程服务调用 - 处理网络错误<br>
 * 2. 数据库操作 - 处理连接异常<br>
 * 3. 文件操作 - 处理IO异常<br>
 */
public class ErrorPropagationDemo {
    private final AtomicInteger errorCount = new AtomicInteger(0);
    private final AtomicInteger retryCount = new AtomicInteger(0);
    private final AtomicInteger fallbackCount = new AtomicInteger(0);
    
    /**
     * 基础错误处理<br>
     * 使用onError*操作符处理错误
     */
    public Mono<String> basicErrorHandling(boolean shouldFail) {
        return Mono.defer(() -> {
            if (shouldFail) {
                errorCount.incrementAndGet();
                return Mono.error(new ServiceException("Service failed"));
            }
            return Mono.just("Success");
        });
    }
    
    /**
     * 错误恢复策略<br>
     * 使用onErrorResume提供备选值
     */
    public Mono<String> errorRecovery(boolean shouldFail) {
        return basicErrorHandling(shouldFail)
                .onErrorResume(ServiceException.class, e -> {
                    fallbackCount.incrementAndGet();
                    return Mono.just("Fallback Value");
                });
    }
    
    /**
     * 错误重试策略<br>
     * 使用retryWhen实现高级重试逻辑
     */
    public Mono<String> errorRetry(boolean shouldFail) {
        return basicErrorHandling(shouldFail)
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100))
                        .doBeforeRetry(signal -> {
                            retryCount.incrementAndGet();
                            System.out.println("Retrying... Attempt: " + signal.totalRetries());
                        }));
    }
    
    /**
     * 错误转换策略<br>
     * 使用onErrorMap转换错误类型
     */
    public Mono<String> errorTransformation(boolean shouldFail) {
        return basicErrorHandling(shouldFail)
                .onErrorMap(ServiceException.class, 
                        e -> new BusinessException("Business error: " + e.getMessage()));
    }
    
    /**
     * 条件错误处理<br>
     * 根据错误类型使用不同处理策略
     */
    public Mono<Object> conditionalErrorHandling(Exception error) {
        return Mono.defer(() -> {
            errorCount.incrementAndGet();
            return Mono.error(error);
        }).onErrorResume(e -> {
            if (e instanceof ServiceException) {
                return Mono.just("Service Fallback");
            } else if (e instanceof BusinessException) {
                return Mono.just("Business Fallback");
            }
            return Mono.error(e);
        });
    }
    
    /**
     * 错误恢复链<br>
     * 串联多个错误恢复策略
     */
    public Mono<String> errorRecoveryChain(boolean shouldFail) {
        return basicErrorHandling(shouldFail)
                .onErrorResume(ServiceException.class, e -> {
                    fallbackCount.incrementAndGet();
                    return basicErrorHandling(true)  // 尝试第二次调用
                            .onErrorReturn("Final Fallback");  // 最终降级
                });
    }
    
    /**
     * 并行错误处理<br>
     * 处理并行流中的错误
     */
    public Flux<String> parallelErrorHandling() {
        return Flux.range(0, 5)
                .parallel(2)
                .map(i -> {
                    if (i % 2 == 0) {
                        throw new ServiceException("Error for " + i);
                    }
                    return "Success " + i;
                })
                .sequential()
                .onErrorContinue((e, obj) -> {
                    errorCount.incrementAndGet();
                    System.out.println("Error handled for: " + obj);
                });
    }
    
    // 获取统计信息
    public int getErrorCount() {
        return errorCount.get();
    }
    
    public int getRetryCount() {
        return retryCount.get();
    }
    
    public int getFallbackCount() {
        return fallbackCount.get();
    }
    
    // 重置计数器
    public void resetCounters() {
        errorCount.set(0);
        retryCount.set(0);
        fallbackCount.set(0);
    }
}

class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}

class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
} 