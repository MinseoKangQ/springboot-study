package com.example.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = Exception.class) // 모든 예외를 잡아서 예외 처리
    public ResponseEntity exception(Exception e) { // 매개변수로 예외를 받을 수 있음
        System.out.println("--- GlobalControllerAdvice 클래스에서 동작 ---");
        System.out.println("--- exception 메소드 ---");
        System.out.println(e.getClass().getName());
        System.out.println(e.getLocalizedMessage());
        // response로 내려주는 부분
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class) // 특정 메소드의 예외 잡아서 예외 처리
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e) {
        System.out.println("--- GlobalControllerAdvice 클래스에서 동작 ---");
        System.out.println("--- methodArgumentNotValidException 메소드 ---");
        // Response의 Body에 메세지 넘겨줌
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}