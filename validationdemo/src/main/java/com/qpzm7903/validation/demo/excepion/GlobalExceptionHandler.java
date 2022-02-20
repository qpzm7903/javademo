package com.qpzm7903.validation.demo.excepion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-02-12-17:16
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, Object> map = new HashMap<>();
        List<String> list = new LinkedList<>();
        result.getFieldErrors().forEach(error -> {
            String field = error.getField();
            Object value = error.getRejectedValue();
            String msg = error.getDefaultMessage();
            list.add(String.format("错误字段 -> %s 错误值 -> %s 原因 -> %s", field, value, msg));
        });
        map.put("msg", list);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
