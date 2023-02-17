<h3>01. 실습 내용 및 프로젝트 구조</h3>

<br>

실습 내용
- response가 Client 입장에서는 친절하지 않았음
- global exception + Spring validation으로 <b>Client</b>에게 알려주는 실습 진행

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

<h3>02. ApiController 클래스 작성</h3>

<br>

- @Validated를 컨트롤러 단위에 작성 - 검증 작업을 위해서
- @RequestParam에 @Size, @NotNull 작성해주면 매개변수에 대해서도 validation 가능


```java
package com.example.exceptionandvalidation.controller;

import com.example.exceptionandvalidation.dto.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/user")
@Validated
public class ApiController {

    @GetMapping("")
    public User get(
            @Size(min = 2)
            @RequestParam String name,

            @NotNull
            @Min(1)
            @RequestParam Integer age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);

        int a = 10 + age; // 예외 발생

        return user;
    }

    @PostMapping("")
    public User post(@Valid @RequestBody User user) {
        System.out.println(user);
        return user;
    }

}
```

<br>

---

<br>

<h3>03. ApiControllerAdvice 클래스 작성</h3>

<br>

- @RestControllerAdvice에 basePackageClasses 값 작성하여 ApiController에 대해서만 동작하도록 함
- HttpServletRequest에서 getRequestURI()로 요청 주소 받기

```java
package com.example.exceptionandvalidation.advice;

import com.example.exceptionandvalidation.controller.ApiController;
import com.example.exceptionandvalidation.dto.Error;
import com.example.exceptionandvalidation.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestControllerAdvice(basePackageClasses = ApiController.class) // ApiController 클래스에 대해서만 동작
public class ApiControllerAdvice {

    @ExceptionHandler(value = Exception.class) // 모든 예외를 잡아서 예외 처리
    public ResponseEntity exception(Exception e) { // 매개변수로 예외를 받을 수 있음
        System.out.println("--- Exception ---");
        System.out.println(e.getClass().getName());
        // response로 내려주는 부분
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class) // 특정 메소드의 예외 잡아서 예외 처리
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest) {

        System.out.println("--- MethodArgumentNotValidException ---");
        List<Error> errorList = new ArrayList<>();

        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().forEach(error -> {
           FieldError field = (FieldError) error;

           String fieldName = field.getField();
           String message = field.getDefaultMessage();
           String value = field.getRejectedValue().toString();

            Error errormessage = new Error();
            errormessage.setField(fieldName);
            errormessage.setMessage(message);
            errormessage.setInvalidValue(value);

            errorList.add(errormessage);
        });

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");

        // Response의 Body에 메세지 넘겨줌
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(ConstraintViolationException e, HttpServletRequest httpServletRequest) {

        System.out.println("--- ConstraintViolationException ---");

        List<Error> errorList = new ArrayList<>();

        e.getConstraintViolations().forEach(error -> {

            Stream<Path.Node> stream = StreamSupport.stream(error.getPropertyPath().spliterator(), false);
            List<Path.Node> list = stream.collect(Collectors.toList());

            String field = list.get(list.size()-1).getName();
            String message = error.getMessage();
            String invalidValue = error.getMessage().toString();

            Error errormessage = new Error();
            errormessage.setField(field);
            errormessage.setMessage(message);
            errormessage.setInvalidValue(invalidValue);

            errorList.add(errormessage);
        });

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity missingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest httpServletRequest){

        System.out.println(" --- MissingServletRequestParameterException ---");

        List<Error> errorList = new ArrayList<>();

        String fieldName = e.getParameterName();
        String invalidValue = e.getMessage();

        Error errorMessage = new Error();
        errorMessage.setField(fieldName);
        errorMessage.setMessage(e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
```

<br>

---

<br>

<h3>04. User, Error, ErrorResponse 클래스 작성</h3>

<br>

```java
package com.example.exceptionandvalidation.dto;

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

```java
package com.example.exceptionandvalidation.dto;

public class Error {

    private String field;
    private String message;
    private String invalidValue;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInvalidValue() {
        return invalidValue;
    }

    public void setInvalidValue(String invalidValue) {
        this.invalidValue = invalidValue;
    }
}

```

```java
package com.example.exceptionandvalidation.dto;

import java.util.List;

public class ErrorResponse {

    private String message;
    private String resultCode;

    private List<Error> errorList;
    private String statusCode;
    private String requestUrl;
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public List<Error> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<Error> errorList) {
        this.errorList = errorList;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
```

<br>

---

<br>

<h3>05. 실행 결과 및 정리</h3>

<br>

실행 결과

- get 메소드


<br>

- post 메소드

<br>

정리

<table>
    <tr><td>@Valid</td><td>컨트롤러(핸들러)에서만 동작</td></tr>
    <tr><td>@Validated</td><td>컨트롤러 계층이 아닌 다른 계층에서도 유효성 검사</td></tr>
    <tr><td>@RequestParam의 변수</td><td>validation 검증을 위한 annotation 사용 가능</td></tr>
    <tr><td>@RestControllerAdvice</td><td>basePackageClasses 작성으로 특정 클래스에 대해서만 동작</td></tr>
    <tr><td>HttpServletRequest 객체</td><td>getRequestURI() 메소드로 요청 주소를 가져올 수 있음</td></tr>


</table>