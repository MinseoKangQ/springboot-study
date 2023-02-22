package com.example.intercepter.config;

import com.example.intercepter.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Spring Container에서 Bean 관리
@RequiredArgsConstructor // final로 선언된 객체들을 생성자에서 주입받을 수 있도록 함
public class MvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/api/private/*"); // authInterceptor 등록
        // . 이후 -> 검사하고 싶은 패턴에 대해 넣을 수 있음
    }
}