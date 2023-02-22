<h3>01. Interceptor</h3>

<br>

개념

- Filter와 매우 유사한 형태로 존재

- 차이점은 Spring Context에 등록된다는 것

- Spring Context에 등록되기 때문에 스프링에 대한 기능 활용 가능

- AOP와 유사한 기능 제공 가능

<br>

사용

- Filter : 순수한 log 남기기

- Interceptor : 인증

<br>


<br>

---

<br>

<h3>02. 프로젝트 구조</h3>

<br>

contoller 패키지

- PrivateController 클래스 : 아무 권한이 없는 사용자 모두가 들어올 수 있도록 함

- PublicController 클래스 : 아무나 접근할 수 없음, 세션이 인증된 사용자만 접근 가능

<br>

annotation 패키지 

- Auth 어노테이션 : 권한 차이를 만들기 위함

<br>

interceptor 패키지

- AuthInterceptor 클래스 : @Auth 있는지 검사해서 붙어있으면 세션 통과

<br>

config 패키지

- MvcConfig 클래스 : interceptor 등록

<br>

exception 패키지

- AuthException 클래스 : exception 받음

<br>

handler 패키지

- GlobalException 클래스 : AuthException 처리

<br>

---

<br>

<h3>03. PrivateController 클래스 작성</h3>

<br>

- 세션이 인증된 사용자만 접근 가능
- @Auth
- @Slf4j : log 찍기 위해

```java
package com.example.intercepter.controller;

import com.example.intercepter.annotation.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private")
@Auth
@Slf4j
public class PrivateController {

    @GetMapping("/hello")
    public String hello() {
        log.info("private hello controller");
        return "private hello";
    }
}
```

<br>

---

<br>

<h3>04. PublicController 클래스 작성</h3>

<br>

- 아무런 권한이 없는 사용자도 접근 가능

```java
package com.example.intercepter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @GetMapping("/hello")
    public String hello() {
        return "public hello";
    }

}
```

<br>

---

<br>

<h3>05. Auth 어노테이션 작성</h3>

<br>

- 권한 차이를 만들기 위해 annotation 작성 -> PrivateController 클래스에 붙임

```java
package com.example.intercepter.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Auth {
}
```

<br>

---

<br>

<h3>06. AuthInterceptor 클래스 작성</h3>

<br>

- implements HandlerInterceptor

- @Slf4j : log 찍기 위해

- @Component : 스프링에서 관리되어야 함

- preHandle 메소드 오버라이딩 : 권한 확인

- handler : 여러 정보들 가지고 있음

- interceptor에서 return false이면 controller로 넘어갈 수 없기 때문에 return true

- checkAnnotation 메소드

    - javascript, html 등 리소스에 대한 것이면 통과

    - @Auth annotation이 작성되어 있는지 확인

```java
package com.example.intercepter.interceptor;

import com.example.intercepter.annotation.Auth;
import com.example.intercepter.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();

        URI uri = UriComponentsBuilder.fromUriString(request.getRequestURI())
                .query(request.getQueryString())
                        .build()
                        .toUri();

        log.info("request url : {}", url);
        boolean hasAnnotation = checkAnnotation(handler, Auth.class);
        log.info("has annotation : {}", hasAnnotation);

        // 나의 서버는 모두 public으로 동작하는데
        // 단, Auth 권한을 가진 요청에 대해서는 세션, 쿠키 등을 보겠다

        // 권한을 가지고 있으면
        if(hasAnnotation) {
            // 권한 체크
            String query = uri.getQuery();
            if(query.equals("name=steve")){
                return true;
            }
            throw new AuthException(); // 예외 처리
        }
        return true; // false이면 이후 Controller까지 못감
    }

    // annotation의 존재 여부 확인
    private boolean checkAnnotation(Object handler, Class clazz) {

        // javascript, html 등 리소스에 대한 것이면 통과시키기
        if(handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // annotation check
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        if(null != handlerMethod.getMethodAnnotation(clazz) || null != handlerMethod.getBeanType().getAnnotation(clazz)) {
            // Auth annotation이 있을 때는 true
            return true;
        }

         return false;
    }

}
```
<br>

---


<br>

<h3>07. MvcConfig 클래스 작성</h3>

<br>

- @Configuration : Spring Container에서 Bean이 관리될 수 있도록 함
- @RequiredArgsConstructor : final로 선언된 객체 생성하여 생성자에서 주입받을 수 있도록 함
- implements WebMvcConfigurer
- lombok 사용을 위해 AuthInterceptor 클래스를 final로 선언
- addInterceptor로 인증의 과정을 depth 있게 적용 가능(순차적임)

```java
package com.example.intercepter.config;

import com.example.intercepter.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor // final로 선언된 객체들을 생성자에서 주입받을 수 있도록 함
public class MvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/api/private/*"); // authInterceptor 등록
        // . 이후 -> 검사하고 싶은 패턴에 대해 넣을 수 있음
    }
}
```
<br>

---


<br>

<h3>08. AuthException 클래스 작성</h3>

<br>

- exception 받음

```java
package com.example.intercepter.exception;

public class AuthException extends RuntimeException {

}
```
<br>

---


<br>

<h3>09. GlobalException 클래스 작성</h3>

<br>

- @RestControllerAdvice : REST Controller에 대한 Exception 처리
- @ExceptionHandler : 특정 클래스 지정

```java
package com.example.intercepter.handler;

import com.example.intercepter.exception.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity authException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
```

<br>

---


<br>

<h3>10. 실행 결과</h3>



