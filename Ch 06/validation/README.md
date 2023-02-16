<h3>01. Validation</h3>

<br>

개념
- 프로그래밍에 있어서 가장 필요한 부분
- Java에서는 null 값에 대해서 접근하려고 할 때 null pointer exception이 발생
- 이러한 부분을 방지하기 위해 미리 검증하는 과정을 Validation 이라고 함

<br>

특징
- 검증해야 할 값이 많은 경우 코드의 길이가 길어짐
- Service Logic과의 분리 필요
- 코드가 흩어져 있는 경우 어디에서 검증하는지 알기 어려우며, 재사용의 한계 존재
- 구현에 따라 달라질 수 있지만, 검증 Logic이 변경되는 경우 참조하는 클래스에서 Logic이 변경되어야 하는 부분이 발생할 수 있음

<br>

관련 Annotation

<table>
    <tr><td>@Size</td><td>문자 길이 측정</td></tr>
    <tr><td>@NotNull</td><td>null 불가능</td></tr>
    <tr><td>@NotEmpty</td><td>null, "" 불가능</td></tr>
    <tr><td>@NotBlank</td><td>null, "", " " 불가능</td></tr>
    <tr><td>@Past</td><td>과거 날짜</td></tr>
    <tr><td>@PastOrPresent</td><td>오늘이나 과거 날짜</td></tr>
    <tr><td>@Future</td><td>미래 날짜</td></tr>
    <tr><td>@FutureOrPresent</td><td>오늘이나 미래 날짜</td></tr>
    <tr><td>@Pattern</td><td>정규식 적용</td></tr>
    <tr><td>@Max</td><td>최대값</td></tr>
    <tr><td>@Min</td><td>최소값</td></tr>
    <tr><td>@AssertTrue / False</td><td>별도 Logic 적용</td></tr>
    <tr><td>@Valid</td><td>해당 object validation 실행</td></tr>
</table>

<br>

---

<br>

<h3>02. 프로젝트 기본 설정</h3>

<br>

build.gradle에 dependencies 부분에 하위 코드 작성

```java
implementation('org.springframework.boot:spring-boot-starter-validation')
```

<br>

관련 문서
https://beanvalidation.org/2.0-jsr380/

<br>

핸드폰번호 정규식
```java
"^\\d{2,3}-\\d{3,4}-\\d{4}$"
```


<br>

---

<br>

<h3>03. User 클래스 작성</h3>

<br>

```java
package com.example.validation.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class User {

    @NotBlank
    private String name;

    @Max(value = 90)
    private int age;

    @Email
    private String email;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. xxx-xxxx-xxxx")
    private String phoneNumber;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}
```

<br>

---

<br>

<h3>04. ApiController 클래스 작성</h3>

<br>

```java
package com.example.validation.controller;

import com.example.validation.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ApiController {

    @PostMapping("/user")
    public ResponseEntity user(@Valid @RequestBody User user, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) { // Error를 가지고 있다면

            System.out.println("--- 에러 ---");

            // 무슨 Error인지 보기
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();

                sb.append("field : " + field.getField() + "\n");
                sb.append("message : " + message + "\n");
            });

            System.out.println(sb);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
        }

        System.out.println("--- 정상 ---");
        System.out.println(user);
        return ResponseEntity.ok(user);
    }

}
```

<br>

실행 결과

<br>

---

<br>

<h3>05. 정리</h3>

<br>

@Valid 가 붙은 객체는 해당 객체 내의 annotation 검사

BindingResult 사용하면 @Valid에 대한 값이 bindingResult 변수에 들어옴

Annotation의 message로 getDefaultMessage에 들어가는 내용 설정 가능


<br>
