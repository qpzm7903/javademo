package com.qpzm7903.base20.concept17;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;

/**
 * 组合与分解操作演示<br>
 * <p>
 * 典型场景：<br>
 * 1. 多源数据聚合<br>
 * 2. 数据流分组<br>
 * 3. 条件组合<br>
 */
public class CombinationDecompositionDemo {
    private final AtomicInteger operationCount = new AtomicInteger(0);
    
    /**
     * 基础组合示例<br>
     * 使用zip组合多个流
     */
    public Flux<String> basicCombination() {
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<Integer> flux2 = Flux.just(1, 2, 3);
        
        return Flux.zip(flux1, flux2)
                .map(tuple -> {
                    operationCount.incrementAndGet();
                    return tuple.getT1() + tuple.getT2();
                });
    }
    
    /**
     * 合并示例<br>
     * 使用merge合并多个流
     */
    public Flux<String> mergeStreams() {
        Flux<String> flux1 = Flux.just("X", "Y", "Z")
                .delayElements(Duration.ofMillis(100));
        Flux<String> flux2 = Flux.just("1", "2", "3")
                .delayElements(Duration.ofMillis(50));
        
        return Flux.merge(flux1, flux2)
                .doOnNext(s -> operationCount.incrementAndGet());
    }
    
    /**
     * 串联示例<br>
     * 使用concat按顺序连接流
     */
    public Flux<String> concatenateStreams() {
        Flux<String> flux1 = Flux.just("First", "Second");
        Flux<String> flux2 = Flux.just("Third", "Fourth");
        
        return Flux.concat(flux1, flux2)
                .doOnNext(s -> operationCount.incrementAndGet());
    }
    
    /**
     * 分组示例<br>
     * 使用groupBy分组元素
     */
    public Flux<GroupedData> groupElements() {
        return Flux.just(
                        new DataItem("A", 1),
                        new DataItem("B", 1),
                        new DataItem("A", 2),
                        new DataItem("B", 2)
                )
                .groupBy(DataItem::getGroup)
                .map(group -> {
                    operationCount.incrementAndGet();
                    return new GroupedData(group.key(), group);
                });
    }
    
    /**
     * 窗口示例<br>
     * 使用window按大小分割流
     */
    public Flux<List<Integer>> windowElements() {
        return Flux.range(1, 10)
                .window(3)
                .flatMap(window -> {
                    operationCount.incrementAndGet();
                    return window.collectList();
                });
    }
    
    /**
     * 条件组合示例<br>
     * 根据条件组合不同的流
     */
    public Flux<String> conditionalCombination(boolean useFirst) {
        Flux<String> first = Flux.just("First1", "First2");
        Flux<String> second = Flux.just("Second1", "Second2");
        
        return (useFirst ? first : second)
                .doOnNext(s -> operationCount.incrementAndGet());
    }
    
    /**
     * 扁平化示例<br>
     * 使用flatMap展开嵌套流
     */
    public Flux<String> flattenStreams() {
        return Flux.just("A", "B")
                .flatMap(s -> Flux.just(s + "1", s + "2"))
                .doOnNext(s -> operationCount.incrementAndGet());
    }
    
    /**
     * 批处理示例<br>
     * 使用buffer进行批处理
     */
    public Flux<List<Integer>> batchProcessing() {
        return Flux.range(1, 10)
                .buffer(3)
                .doOnNext(batch -> operationCount.incrementAndGet());
    }
    
    // 数据类
    public static class DataItem {
        private final String group;
        private final int value;
        
        public DataItem(String group, int value) {
            this.group = group;
            this.value = value;
        }
        
        public String getGroup() {
            return group;
        }
        
        public int getValue() {
            return value;
        }
    }
    
    public static class GroupedData {
        private final String key;
        private final Flux<DataItem> items;
        
        public GroupedData(String key, Flux<DataItem> items) {
            this.key = key;
            this.items = items;
        }
        
        public String getKey() {
            return key;
        }
        
        public Flux<DataItem> getItems() {
            return items;
        }
    }
    
    // 获取统计信息
    public int getOperationCount() {
        return operationCount.get();
    }
    
    // 重置计数器
    public void resetCounter() {
        operationCount.set(0);
    }
} 