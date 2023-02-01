package com.example.AOP.aop;

import com.example.AOP.dto.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Aspect // AOP 클래스임을 명시
@Component // Spring에서 관리됨을 명시
public class DecodeAop {

    @Pointcut("execution(* com.example.AOP.controller..*.*(..))")
    private void cut() { }

    @Pointcut("@annotation(com.example.AOP.annotation.Decode)")
    private void enableDecode() { }

    // 전 => Decoding 해서 내보냄
    @Before("cut() && enableDecode()")
    public void before(JoinPoint joinPoint) throws UnsupportedEncodingException {
        // 내가 원하는 argument이면 됨, 특정 변수거나 객체이면 됨
        Object [] args = joinPoint.getArgs();

        for(Object arg : args) {
            if(arg instanceof User) { // 내가 원하는 User라는 클래스가 매칭이 된다면
                User user = User.class.cast(arg); // User라는 클래스로 형 변환
                String base64Email = user.getEmail(); // 기존에 Encoding된 이메일 꺼내고
                String email = new String(Base64.getDecoder().decode(base64Email), "UTF-8"); // Decoding 하고
                user.setEmail(email);// Decoding 된 이메일 넣어주기

                // 실질적인 controller 코드에서는 User를 Decode 하는 코드가 필요 없음
            }
        }

    }

    // 후 => Encoding 해서 내보냄
    @AfterReturning(value = "cut() && enableDecode()", returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj) {
        if(returnObj instanceof User) {
            User user = User.class.cast(returnObj);
            String email = user.getEmail();
            String base64Email = Base64.getEncoder().encodeToString(email.getBytes());
            user.setEmail(base64Email);
        }
    }
}