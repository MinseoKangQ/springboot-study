package com.example.exceptionandvalidation.controller;

import com.example.exceptionandvalidation.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

        int a = 10 + age;

        return user;
    }

    @PostMapping("")
    public User post(@Valid @RequestBody User user) {
        System.out.println(user);
        return user;
    }

//    @ExceptionHandler(value = MethodArgumentNotValidException.class) // 특정 메소드의 예외 잡아서 예외 처리
//    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e) {
//        // Response의 Body에 메세지 넘겨줌
//        System.out.println("api controller에서 동작");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//    }

}
