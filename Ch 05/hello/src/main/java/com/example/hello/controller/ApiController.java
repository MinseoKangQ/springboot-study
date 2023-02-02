package com.example.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //  해당 Class는 REST API 처리하는 Controller로 등록
@RequestMapping("/api") // URI를 지정해주는 Annotation
public class ApiController {

    // GET방식으로 요청이 들어오면, 문자열 리턴
    @GetMapping("/hello") // http://localhost:9090/api/hello 로 맵핑
    public String hello() {
        return "hello spring boot!";
    }

}
