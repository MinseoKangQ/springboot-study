package com.example.AOP.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimerAop {

    // 컨트롤러 하위의 메소드에
    @Pointcut("execution(* com.example.AOP.controller..*.*(..))")
    private void cut() { }

    // 타이머 어노테이션이 설정된 메소드에만 로깅, 시간을 잴 것이기 때문에 전/후가 필요함
    // before, after로 하면 time을 공유할 수 없기 때문에 @Around 사용
    @Pointcut("@annotation(com.example.AOP.annotation.Timer)")
    private void enableTimer() { }

    @Around("cut() && enableTimer()") // 두 가지 조건을 같이 쓰겠다는 것
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {

        // 실행 전
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed(); // 실질적인 메소드 실행됨

        // 실행 후
        stopWatch.stop();

        System.out.println("total time : " + stopWatch.getTotalTimeSeconds());
    }
}