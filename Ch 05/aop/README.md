<h3>AOP(Aspect Oriented Programming)</h3>

<br>

- 관점 지향 프로그래밍
    - Spring Application은 대부분 MVC
    - 웹 어플리케이션에서는 Web Layer / Business Layer /  Data Layer
    - <b><u>AOP는 메소드나 특정 구역에서 반복되는 로직들을 한곳에 몰아서 코딩할 수 있도록 함</u></b>

<br>  

- Web Layer
    - REST API 제공, Client 중심 로직 적용
    - ex) Response 내려주기, HTTP status 바꾸기

<br>

- Business Layer
    - 내부 정책에 따른 Logic 개발, 주로 해당 부분 개발
    - ex) Service 제공

<br>

- Data Layer
    - 데이터 베이스 및 외부와의 연동 처리
    - ex) Repository와 연결

<br>

- 관련 주요 Annotation

<table>
    <tr><td>Annotation</td><td>의미</td></tr>
    <tr><td>@Aspect</td><td>자바에서 널리 사용하는 AOP 프레임워크에 해당, AOP를 정의하는 Class에 할당</td></tr>
    <tr><td>@Pointcut</td><td>기능을 어디에 적용시킬지(ex. 메소드, Annotation 등) 지점을 설정</td></tr>
    <tr><td>@Before</td><td>메소드 실행하기 이전</td></tr>
    <tr><td>@After</td><td>메소드 성공적으로 실행 후, 예외가 발생 되어도 실행</td></tr>
    <tr><td>@AfterReturning</td><td>메소드 호출 성공 실행 시 (Not Throws)</td></tr>
    <tr><td>@AfterThrowing</td><td>메소드 호출 실패 예외 발생 (Throws)</td></tr>
    <tr><td>@Around</td><td>Before / After 모두 제어 (ex. Time 공유 필요 시)</td></tr>
</table>

<br>

---

<h3>AOP 실무</h3>

<br> 

- AOP 사용하려면 build.gradle에 dependencies 추가 필요
    ```java 
    implementation 'org.springframework.boot:spring-boot-starter-aop'
    ```

<br>

- 포트 변경하고 싶으면 [ resources ] - [ application.properties ] 에 포트 등록
    ```java
    server.port=9090
    ```

<br>

- dto 패키지 내에 User 클래스 작성
    - id, pw, email 멤버 변수 생성
    - Getter, Setter 작성 및 toString 오버라이딩

<br>

- controller 패키지 내에 RestApiController 클래스 작성
    ```java
    @RestController
    @RequestMapping("/api")
    public class RestApiController { ... }
    ```
<br>

---

<br>

- GET 메소드 작성

    ```java
    @GetMapping("/get/{id}")
    public String get(@PathVariable Long id, @RequestParam String name) {
        // 밑의 sout 부분을 aop로 출력할 수 있음 (외부에서 바라봄)
        System.out.println("get method");
        System.out.println("get method : " + id);
        System.out.println("get method : " + name);
        return id + " " + name;
    }
    ```

    - Talend API에서
        <table>
        <tr><td>GET</td><td>http://localhost:8080/api/get/100?name=steve</td></tr>
        </table>

<br>

---

<br>

- POST 메소드 작성

    ```java
    @PostMapping("/post")
    public User post(@RequestBody User user) {
        // 밑의 sout 부분을 aop로 출력할 수 있음 (외부에서 바라봄)
        System.out.println("post mehtod : " + user);
        return user;
    }
    ```

    - Talend API에서
     <table>
     <tr><td>POST</td><td>http://localhost:8080/api/post</td></tr>
     <tr><td>BODY</td><td>{ "id" : "steve", "pw" : "1234", "email" : "steve@gamil.com"}</td></tr>
     </table>

<br>

---

<br>

<h3>Annotation 정의하고, 해당 Annotation이 설정된 메소드만 기록할 수 있도록 실습</h3>

<br>

<b><u>@Bean / @Component / @Configuration</u></b>

- @Bean : 클래스에 사용(X), 메소드에 사용(O)

- @Component : 클래스에 사용(O), 메소드에 사용(X)

- @Configuration : 하나의 클래스에 여러 Bean 등록

<br>

---

<br>

<b><u>aop 패키지에 ParameterAop 클래스 작성</u></b>

```java
package com.example.AOP.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect // AOP 클래스임을 명시
@Component // Spring에서 관리됨을 명시
public class ParameterAop {

    // controller 하위의 모든 메소드에 적용
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
        System.out.println(method.getName());


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

```

<br>

---

<br>

<b><u>aop 패키지에 TimerAop 클래스 작성 (특정 메소드의 실행 시간을 출력)</b></u>
    
```java
package com.example.AOP.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect // AOP 클래스임을 명시
@Component // Spring에서 관리됨을 명시
public class TimerAop {

    // 컨트롤러 하위의 메소드에 로깅
    @Pointcut("execution(* com.example.AOP.controller..*.*(..))")
    private void cut() { }

    // PointCut 외에 타이머로써 동작할 수 있게 Annotation 하나 더 등록
    // 타이머 어노테이션이 설정된 메소드에만 로깅
    // 시간을 잴 것이기 때문에 전/후가 필요함
    // @Before, @After 사용 시 시간을 공유할 수 없기 때문에 @Around 사용
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
```

<br>

<b><u>annotation 패키지에 Timer annotation 작성</u></b>

```java
package com.example.AOP.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME) // 런타임에 사용할 것임을 명시
public @interface Timer { }
```

<br>

<b><u>DELETE 메소드 작성</u></b>
```java
@Timer
@DeleteMapping("/delete")
public void delete() throws InterruptedException {
    Thread.sleep(1000 * 2);
}
```

<br>

Talend API에서
<table>
<tr><td>DELETE</td><td>http://localhost:8080/api/delete</td></tr>
</table>

<br>

---

<br>

<h3>값의 변환 실습 (암호화, 복호화)</h3>

<br>

<b><u>aop 패키지에 DecodeAop 클래스 작성 (전 - Decoding, 후 - Encoding )</b></u>
    
```java
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
```

<br>

<b><u>annotation 패키지에 Decode annotation 작성</u></b>

```java
package com.example.AOP.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME) // 런타임에 사용할 것임을 명시
public @interface Decode { }
```

<br>

<b><u>PUT 메소드 작성</u></b>
```java
@Decode
@PutMapping("/put")
public User put(@RequestBody User user) {
    System.out.println("put method");
    System.out.println(user);
    return user;
}
```

<br>

Talend API에서
<table>
<tr><td>PUT</td><td>http://localhost:8080/api/put</td></tr>
<tr><td>BODY</td><td>{ "id" : "steve", "pw" : "1234", "email" : "c3RldmVAZ21haWwuY29t" }</td></tr>
</table>

<br>