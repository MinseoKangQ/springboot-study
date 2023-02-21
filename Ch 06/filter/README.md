<h3>01. Filter 개념</h3>

<br>

개념

- Web Application에서 관리되는 영역
- Spring Boot Framework에서 Client로부터 오는 요청/응답에 대해 최초/최종 단계의 위치에 존재
- 이를 통해 요청/응답의 정보를 변경할 수 있음
- Spring에 의해 데이터가 변환되기 전의 순수한 Client의 요쳥/응답 값 확인 가능

<br>

특징
- 유일하게 ServletRequest, ServletResponse 객체를 변환할 수 있음

<br>

사용
- request/respon의 Logging
- 인증과 관련된 Logic 처리
- 이를 선/후 처리하여 Service business logic과 분리

<br>

---

<br>

<h3>02. 프로젝트 구조</h3>

<br>


<br>

Lombok 사용을 위해 build.gradle의 dependencies에 다음 코드 추가

```java
compileOnly 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok'
```

<br>

---

<br>

<h3>03. User 클래스 작성</h3>

<br>

- lombok 사용

```java
package com.example.filter.dto;

import lombok.*;

@Data // Getter, Setter 등
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 전체 생성자
public class User {
    private String name;
    private int age;
}
```

<br>

---

<br>

<h3>03. ApiController 클래스 작성</h3>

<br>

- @Slf4j : log 찍기 위함

```java
package com.example.filter.controller;

import com.example.filter.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class ApiController {

    @PostMapping("")
    public User user(@RequestBody User user) {
        log.info("User : {}", user);
        return user;
    }

}
```

<br>

---

<br>

<h3>04. ApiUserController 클래스 작성</h3>

<br>

- @Slf4j : log 찍기 위함

```java
package com.example.filter.controller;

import com.example.filter.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/temp")
public class ApiUserController {

    @PostMapping("")
    public User user(@RequestBody User user) {
        log.info("Temp : {}", user);
        return user;
    }

}
```

<br>

---

<br>

<h3>05. GlobalFilter 클래스 작성</h3>

<br>

- Filter 클래스를 implements 받아 doFilter 메소드 overriding
- @Slf4j : log 찍기 위함
- @WebFilter : 특정 url에 Filter 걸기
- 전처리에서의 HttpServletRequest/HttpServletResponse 형변환 : Filter 단에서 response/request 변형 가능
- chain.doFilter(req, res) : 기준으로 전처리/후처리 이루어짐
- ContentCachingRequestWrapper 클래스의 copyBodyToResponse 메소드 : Client가 제대로 된 응답을 받을 수 있도록 꺼낸 내용을 Body에 Copy


```java
package com.example.filter.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/api/user/*") // 특정 url에 Filter 걸기
public class GlobalFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 전처리 구간
        // 형 변환
        ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest)request);
        ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse)response);

        // 실행
        chain.doFilter(httpServletRequest, httpServletResponse);

        // 어떤 주소로 가져왔는지
        String url = httpServletRequest.getRequestURI();

        // 후처리 구간
        // 응답 받기
        String reqContent = new String(httpServletRequest.getContentAsByteArray());
        String resContent = new String(httpServletResponse.getContentAsByteArray());
        int httpStatus = httpServletResponse.getStatus();

        // 위에서 내용을 빼가기 때문에 이 메소드 호출하여 Client가 제대로 된 응답을 받을 수 있도록 함
        httpServletResponse.copyBodyToResponse();

        log.info("request url : {}, requestBody : {}", url, reqContent);
        log.info("response status : {}, responseBody : {}", httpStatus, resContent);
    }

}
```

<br>

---

<br>

<h3>06. FilterApplication 클래스(Main) 작성</h3>

<br>

- @ServletComponentScan : 특정 url에 Filter 걸기

```java
package com.example.filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan 특정 url에 Filter 걸기
public class FilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilterApplication.class, args);
	}

}
```

<br>

---

<br>

<h3>07. 실행 결과 및 정리</h3>

<br>

http://localhost:8080/api/user 실행 결과

- Filter에서 지정한 log가 찍혀야 함




<br>

http://localhost:8080/api/tmep 실행 결과

- Filter에서 지정한 log가 찍히면 안됨



<br>

lombok 관련 정리

<table>
    <tr><td>@Data</td><td>Getter, Setter, toString, equals, hashCode 등</td></tr>
    <tr><td>@NoArgsConstructor</td><td>기본 생성자</td></tr>
    <tr><td>@AllArgsConstructor</td><td>전체 생성자</td></tr>
    
</table>

<br>

Filter 관련 정리

<table>
    <tr><td>@Slf4j</td><td>log.info로 로그 찍기</td></tr>
    <tr><td>implements Filter</td><td>doFilter 메소드 오버라이딩</td></tr>
    <tr><td>chain.doFilter(req, res)</td><td>Filter가 실행되는 부분</td></tr>
    <tr><td>전처리 구간의 형변환</td><td>Filter 단이라 가능</td></tr>
    <tr><td>ContentCachingRequestWrapper 클래스의 copyBodyToResponse 메소드</td><td>Client가 제대로 된 응답을 받을 수 있도록 꺼낸 내용을 Body에 Copy</td></tr>
    <tr><td>@ServletComponentScan</td><td>Main - 특정 url에 Filter 걸기</td></tr>
    <tr><td>@WebFilter(urlPatterns = "")</td><td>Filter - 특정 url에 Filter 걸기</td></tr>
    <tr><td>핵심은 doFilter 이후의 후처리</td><td>log 찍기 + copyBody</td></tr>
</table>