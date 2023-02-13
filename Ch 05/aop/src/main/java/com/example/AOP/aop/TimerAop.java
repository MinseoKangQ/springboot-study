//package com.example.AOP.aop;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StopWatch;
//
//@Aspect // AOP 클래스
//@Component // Spring에서 관리
//public class TimerAop {
//
//    // controller 하위의 모든 메소드에 적용
//    @Pointcut("execution(* com.example.AOP.controller..*.*(..))")
//    private void cut() { }
//
//    // @Timer가 작성된 메소드에만 로깅
//    @Pointcut("@annotation(com.example.AOP.annotation.Timer)")
//    private void enableTimer() { }
//
//    // 시간을 잴 것이기 때문에 전/후가 필요함
//    // before, after로 하면 time을 공유할 수 없기 때문에 @Around 사용
//    @Around("cut() && enableTimer()") // 두 가지 조건을 같이 쓰겠다는 것
//    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        System.out.println("--- TimerAop 클래스의 around 실행 ---");
//
//        // 실행 전
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        Object result = joinPoint.proceed(); // 실질적인 메소드 실행
//
//        // 실행 후
//        stopWatch.stop();
//
//        System.out.println("total time : " + stopWatch.getTotalTimeSeconds());
//
//    }
//}