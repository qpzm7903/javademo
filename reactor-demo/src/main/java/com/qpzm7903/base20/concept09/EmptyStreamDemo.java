package com.qpzm7903.base20.concept09;

import java.util.Optional;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

/**
 * 空流处理策略演示<br>
 * <p>
 * 典型场景：<br>
 * 1. 缓存查询 - 缓存未命中时返回空<br>
 * 2. 条件查询 - 无匹配结果时返回空<br>
 * 3. 可选配置 - 配置项不存在时返回默认值<br>
 */
public class EmptyStreamDemo {
    
    /**
     * 命令式空值处理<br>
     * 使用Optional处理可能为空的结果
     */
    public Optional<String> findUserImperative(String id) {
        if ("empty".equals(id)) {
            return Optional.empty();
        }
        return Optional.of("User_" + id);
    }
    
    /**
     * 基础空流处理<br>
     * 使用Mono.empty()表示无数据
     */
    public Mono<String> findUser(String id) {
        if ("empty".equals(id)) {
            return Mono.empty();
        }
        return Mono.just("User_" + id);
    }
    
    /**
     * 默认值策略<br>
     * 当流为空时提供默认值
     */
    public Mono<String> findUserWithDefault(String id) {
        return findUser(id)
                .defaultIfEmpty("Default_User");
    }
    
    /**
     * 备选流策略<br>
     * 当主流为空时切换到备选流
     */
    public Mono<String> findUserWithFallback(String id) {
        return findUser(id)
                .switchIfEmpty(findBackupUser(id));
    }
    
    /**
     * 转换空流策略<br>
     * 将空流转换为特定的错误或结果
     */
    public Mono<String> findUserWithTransform(String id) {
        return findUser(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)));
    }
    
    /**
     * 条件空流处理<br>
     * 根据不同条件处理空流
     */
    public Mono<String> findUserWithCondition(String id, boolean useDefault) {
        return findUser(id)
                .transform(mono -> useDefault
                        ? mono.defaultIfEmpty("Default_User")
                        : mono.switchIfEmpty(Mono.error(new UserNotFoundException(id))));
    }
    
    /**
     * 组合空流处理<br>
     * 组合多个可能为空的流
     */
    public Mono<Tuple2<String, String>> findUserAndProfile(String id) {
        return Mono.zip(
                findUser(id).defaultIfEmpty("Unknown_User"),
                findProfile(id).defaultIfEmpty("Default_Profile")
        );
    }
    
    /**
     * 过滤空值<br>
     * 在流中过滤掉空值
     */
    public Mono<String> findUserFiltered(String id) {
        return findUser(id)
                .filter(user -> !user.equals("User_filtered"))
                .switchIfEmpty(Mono.just("Filtered_Default"));
    }
    
    // 辅助方法
    private Mono<String> findBackupUser(String id) {
        return Mono.just("Backup_User_" + id);
    }
    
    private Mono<String> findProfile(String id) {
        if ("empty".equals(id)) {
            return Mono.empty();
        }
        return Mono.just("Profile_" + id);
    }
}

class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("User not found with id: " + id);
    }
} 