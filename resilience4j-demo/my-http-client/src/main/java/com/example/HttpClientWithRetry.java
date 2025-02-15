package com.example;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.function.Supplier;

public class HttpClientWithRetry {
    // 传统的 HTTP 客户端，用于执行实际的 HTTP 请求
    private final CloseableHttpClient httpClient;
    // Resilience4j 的重试组件，用于包装和管理重试逻辑
    private final Retry retry;

    public HttpClientWithRetry() {
        this.httpClient = HttpClients.createDefault();
        this.retry = createRetry();
    }

    private Retry createRetry() {
        // 创建重试配置
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)  // 最大重试次数（包括第一次尝试）
                .waitDuration(Duration.ofSeconds(2))  // 重试之间的等待时间
                .retryOnException(e -> e instanceof IOException | e instanceof RuntimeException)  // 指定哪些异常需要重试
                .build();

        // 创建重试注册表，用于管理重试实例
        RetryRegistry registry = RetryRegistry.of(config);
        Retry retry = registry.retry("httpClientRetry");

        // 响应式编程部分：设置事件监听器
        // EventPublisher 使用观察者模式，允许我们监听重试过程中的各种事件
        retry.getEventPublisher()
            // onRetry：当发生重试时触发
            .onRetry(event -> System.out.printf(
                "Retry attempt %d/%d after %dms, due to %s: %s%n",
                event.getNumberOfRetryAttempts(),  // 当前重试次数
                config.getMaxAttempts(),          // 最大重试次数
                event.getWaitInterval().toMillis(),// 等待时间
                event.getLastThrowable().getClass().getSimpleName(),  // 异常类型
                event.getLastThrowable().getMessage()                 // 异常信息
            ))
            // onError：当所有重试都失败时触发
            .onError(event -> System.out.printf(
                "Retry failed after %d attempts, final error: %s: %s%n",
                event.getNumberOfRetryAttempts(),
                event.getLastThrowable().getClass().getSimpleName(),
                event.getLastThrowable().getMessage()
            ))
            // onSuccess：当请求最终成功时触发
            .onSuccess(event -> {
                if (event.getNumberOfRetryAttempts() > 0) {
                    System.out.printf(
                        "Request succeeded after %d retry attempts%n",
                        event.getNumberOfRetryAttempts()
                    );
                }
            });

        return retry;
    }

    public String execute(HttpUriRequest request) throws Exception {
        System.out.printf("Executing request to: %s%n", request.getURI());
        
        // 响应式编程：创建一个可重试的 Supplier
        // Supplier 是一个函数式接口，代表一个可以提供值的操作
        Supplier<String> retryableSupplier = Retry.decorateSupplier(retry, () -> {
            try {
                // 实际执行 HTTP 请求
                HttpResponse response = httpClient.execute(request);
                int statusCode = response.getStatusLine().getStatusCode();
                
                // 检查服务器错误
                if (statusCode >= 500) {
                    throw new RuntimeException("Server error with status code: " + statusCode);
                }
                
                // 将响应体转换为字符串
                return EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                // 包装异常，保持一致的异常处理
                throw new RuntimeException("Failed to execute request: " + e.getMessage(), e);
            }
        });

        // 执行可重试的操作
        // get() 方法会触发实际的 HTTP 请求，如果失败会自动重试
        return retryableSupplier.get();
    }

    public void close() throws IOException {
        httpClient.close();
    }
} 