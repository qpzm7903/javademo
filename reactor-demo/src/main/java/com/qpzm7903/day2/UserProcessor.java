package com.qpzm7903.day2;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qpzm7903.day2.model.User;
import com.qpzm7903.day2.model.UserDTO;

import reactor.core.publisher.Flux;

public class UserProcessor {
    private static final Logger logger = LoggerFactory.getLogger(UserProcessor.class);
    
    // 响应式实现：使用Reactor操作符链
    public Flux<UserDTO> processUsersReactive(Flux<User> userFlux) {
        return userFlux
                .filter(user -> {
                    if (user == null) {
                        System.out.println("User cannot be null");
                        throw new NullPointerException("User cannot be null");
                    }
                    if (user.getAge() < 0) {
                        throw new IllegalArgumentException("Age cannot be negative");
                    }
                    return user.getAge() >= 18;
                })
                .map(this::convertToDTO)
                .doOnNext(dto -> System.out.println("Reactive Processing: " + dto))
                .onErrorContinue((a, b) -> logger.error("Reactive Processing: {}", a.getMessage(), a));
    }
    
    // 命令式实现：使用传统的Java集合操作
    public List<UserDTO> processUsersImperative(List<User> users) {
        return users.stream()
                .filter(user -> user.getAge() >= 18)
                .map(this::convertToDTO)
                .peek(dto -> System.out.println("Imperative Processing: " + dto))
                .collect(Collectors.toList());
    }
    
    // 辅助方法：转换User到UserDTO
    private UserDTO convertToDTO(User user) {
        // 邮箱脱敏处理
        String maskedEmail = maskEmail(user.getEmail());
        // 生成展示名称
        String displayName = user.getName() + " (" + user.getAge() + ")";
        
        return new UserDTO(user.getId(), displayName, maskedEmail);
    }
    
    // 邮箱脱敏
    private String maskEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "";
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return email;
        }
        return email.charAt(0) + "***" + email.substring(atIndex);
    }
} 