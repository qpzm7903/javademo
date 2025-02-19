package com.qpzm7903.base20.concept03;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Flux与Mono的本质区别演示
 * 
 * 典型场景1：用户查询
 * - Mono：根据ID查询单个用户
 * - Flux：查询符合条件的多个用户
 * 
 * 典型场景2：配置加载
 * - Mono：加载单个配置项
 * - Flux：加载配置列表
 */
public class FluxMonoDifferenceDemo {
    
    /**
     * 命令式：Optional表示可能为空的单个结果
     */
    public Optional<User> findUserByIdImperative(String id) {
        // 模拟数据库查询
        if ("1".equals(id)) {
            return Optional.of(new User("1", "John"));
        }
        return Optional.empty();
    }
    
    /**
     * 响应式：Mono表示0-1个结果
     * 适用场景：
     * 1. 根据唯一标识查询
     * 2. 验证操作（成功/失败）
     * 3. 异步初始化
     */
    public Mono<User> findUserById(String id) {
        return "1".equals(id) 
            ? Mono.just(new User("1", "John"))
            : Mono.empty();
    }
    
    /**
     * 命令式：List表示多个结果
     */
    public List<User> findUsersByNameImperative(String name) {
        // 模拟数据库查询
        return List.of(
            new User("1", "John"),
            new User("2", "John")
        );
    }
    
    /**
     * 响应式：Flux表示0-N个结果
     * 适用场景：
     * 1. 列表查询
     * 2. 流式处理
     * 3. 实时数据
     */
    public Flux<User> findUsersByName(String name) {
        return Flux.just(
            new User("1", "John"),
            new User("2", "John")
        ).filter(user -> user.getName().equals(name));
    }
    
    /**
     * Mono转Flux示例
     * 使用场景：需要统一处理单个和多个结果
     */
    public Flux<User> monoToFlux(Mono<User> mono) {
        return mono.flux();
    }
    
    /**
     * Flux转Mono示例
     * 使用场景：
     * 1. 需要收集所有元素到集合
     * 2. 只关心完成信号
     */
    public Mono<List<User>> fluxToMono(Flux<User> flux) {
        return flux.collectList();
    }
    
    /**
     * 演示Mono的错误处理
     */
    public Mono<User> findUserWithErrorHandling(String id) {
        return findUserById(id)
            .switchIfEmpty(Mono.error(new UserNotFoundException(id)))
            .onErrorResume(e -> Mono.just(new User("0", "Default")));
    }
    
    /**
     * 演示Flux的错误处理
     */
    public Flux<User> findUsersWithErrorHandling(String name) {
        return findUsersByName(name)
            .switchIfEmpty(Flux.error(new UserNotFoundException(name)))
            .onErrorResume(e -> Flux.just(new User("0", "Default")));
    }
}

class User {
    private String id;
    private String name;
    
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String criteria) {
        super("User not found with criteria: " + criteria);
    }
} 