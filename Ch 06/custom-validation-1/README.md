<h3>01. Custom Validation - 1 실습 구조</h3>

<br>


<br>

---

<br>

<h3>02. YearMonth Annotation 작성</h3>

<br>

- Custom Annotation 만들기
- @Email의 정의에 들어가서 Annotation과 메서드 참조
- pattern 메소드 작성

<br>

```java
package com.example.customvalidation.annotation;

import com.example.customvalidation.validator.YearMonthValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {YearMonthValidator.class}) // 검증 클래스 지정
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE }) // Java compiler 가 annotation 이 어디에 적용될지 결정
@Retention(RUNTIME) // 어노테이션의 유지 범위(실행하는 동안)

public @interface YearMonth {

    String message() default "yyyyMM 형식에 맞지 않습니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String pattern() default "yyyyMMdd";
    
}
```
<br>

---

<br>

<h3>03. YearMonthValidator Class 작성</h3>

<br>

- 검증 클래스 만들기
- implements ConstraintValidator
- 메소드 오버라이딩

<br>

```java
package com.example.customvalidation.validator;

import com.example.customvalidation.annotation.YearMonth;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class YearMonthValidator implements ConstraintValidator<YearMonth, String> {

    private String pattern;

    // 초기화 했을 때 값 지정
    @Override
    public void initialize(YearMonth constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // 패턴이 잘 지정되어 있는지
        try {
            LocalDate localDate = LocalDate.parse(value + "01", DateTimeFormatter.ofPattern(this.pattern));
        } catch(Exception e) {
            return false;
        }
        return true;
    }
}
```

<br>

---

<br>

<h3>04. User 클래스 작성</h3>

<br>

- Custom Annotation을 필드에 등록

<br>

```java
package com.example.customvalidation.dto;

import com.example.customvalidation.annotation.YearMonth;
import javax.validation.constraints.*;

public class User {
    @NotBlank
    private String name;

    @Max(value = 90)
    private int age;

    @Email
    private String email;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. xxx-xxxx-xxxx")
    private String phoneNumber;

    @YearMonth
    private String reqYearMonth; // 형식은 yyyyMM

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

    public String getReqYearMonth() {
        return reqYearMonth;
    }

    public void setReqYearMonth(String reqYearMonth) {
        this.reqYearMonth = reqYearMonth;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", reqYearMonth='" + reqYearMonth + '\'' +
                '}';
    }
}
```

<br>

---

<br>

<h3>05. ApiController 클래스 작성</h3>

<br>

- 객체 앞에 @Valid 추가

<br>

```java
package com.example.customvalidation.controller;

import com.example.customvalidation.dto.User;
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

---

<br>

<h3>06. 실행 결과 및 정리</h3>

<br>

실행결과


<br>

정리

- Custom Annotation 만들기
- 검증 클래스 만들기
- Custom Annotation을 필드에 등록
- 객체 앞에 @Valid 추가

<br>

---

<br>

참고

@Target : https://sanghye.tistory.com/39