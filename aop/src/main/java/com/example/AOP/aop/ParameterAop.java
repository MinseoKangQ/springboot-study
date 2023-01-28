package com.example.AOP.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect // aop로 동작함을 명시
@Component // Spring에서 관리가 되어야 하기 때문
public class ParameterAop {

    @Pointcut("execution(* com.example.AOP.controller..*.*(..))") // ()안에는 어디다가 적용할 지 작성
    private void cut() {
        // input(before)과 output(after)을 확인
    }

    @Before("cut()") // cut이 실행되는 지점의 before에 해당 메소드 실행
    // 메소드 실행 전에 넘어가는 argument가 무엇인지 확인
    public void before(JoinPoint joinPoint) { // JoinPoint = 들어가는 지점에 대한 정보를 가진 객체
        // 메소드 이름 가져오기
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(method.getName()); //


        Object [] args = joinPoint.getArgs(); // 매개변수들의 배열
        for(Object obj : args) {
            System.out.println("type : " + obj.getClass().getSimpleName());
            System.out.println("value : " + obj);
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj") // cut이 실행된 after에 해당 메소드 실행
                                    // returning과 함수의 매개변수 이름 matching 되어야 함
    // 리턴 이후에 반환값이 무엇인지  확인
    public void afterReturn(JoinPoint joinPoint, Object returnObj) {
        System.out.println("return obj");
        System.out.println(returnObj);
    }
}
