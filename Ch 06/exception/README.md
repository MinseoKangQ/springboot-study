<h3>01. Exception 개념</h3>

<br>

Client에게 오류를 보여주지는 않음을 해결해주기 위함

@RestControllerAdvice : White Label 페이지나 Error 페이지 처리 집합

@ExceptionHandler : 특정 Controller의 예외처리, 모든 에러를 한쪽에서 모두 처리


<br>

---

<br>

<h3>02. 프로젝트 구조</h3>

<br>

validation 사용을 위해 build.gradle dependencies에 다음 코드 추가

```java
implementation 'org.springframework.boot:spring-boot-starter-validation
```

<br>

프로젝트 구조


<br>

---

<br>

<h3>03. ApiController 클래스 작성</h3>

<br>

- @RequestParam(required = false)
- valitation 검사할 클래스 앞에 @Valid
- 클래스 내에 @ExceptionHandler

```java
package com.example.exception.controller;

import com.example.exception.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class ApiController {

    @GetMapping("")
    public User get(@RequestParam(required = false) String name, @RequestParam(required = false) Integer age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);

        int a = 10 + age;

        return user;
    }

    @PostMapping("")
    public User post(@Valid @RequestBody User user) {
        System.out.println(user);
        return user;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class) // 특정 메소드의 예외 잡아서 예외 처리
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e) {
        // Response의 Body에 메세지 넘겨줌
        System.out.println("--- ApiController 클래스에서 동작 ---");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
```
<br>

---

<br>

<h3>04. User 클래스 작성</h3>

<br>

```java
package com.example.exception.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

    @NotEmpty
    @Size(min = 1, max = 10)
    private String name;

    @Min(1)
    @NotNull
    private Integer age;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
}
```

<br>

---

<br>

<h3>05. GlobalControllerAdvice 클래스 작성</h3>

<br>

- @RestControllerAdvice
- @ExceptionHandler(value = Exception.class)

<br>

```java
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
```
<br>

---

<br>

<h3>06. 실행 결과 및 정리</h3>

<br>

실행 결과

get 메소드


<br>

post 메소드

- GlobalControllerAdvice에 예외를 처리하는 메소드가 작성되어 있음

- 하지만 ApiController에 @ExceptionHandler가 붙은 메소드가 존재

- 따라서 에러 메세지가 ApiController에서 발생해야 함





<br>

정리
<table>
    <tr><td>@RestControllerAdvice</td><td>클래스에 적용, 여러가지 Controller가 생겨도 모든 에러를 잡아줌</td></tr>
    <tr><td>@ExceptionHandler</td><td>메소드에 적용</td></tr>
    <tr><td>RequestParam(required = false)</td><td>변수가 필수 입력값은 아니라는 것, 값 안 들어오면 null 들어옴, 값이 없어도 실행됨</td></tr>
</table>