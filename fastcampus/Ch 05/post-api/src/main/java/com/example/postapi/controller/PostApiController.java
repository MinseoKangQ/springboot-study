package com.example.postapi.controller;

import com.example.postapi.dto.PostRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostApiController {

    // 1)
    @PostMapping("/post")
    public void post(@RequestBody Map<String, Object> requestData) {
        requestData.forEach((key, value) -> {
            System.out.println("key : " + key);
            System.out.println("value : " + value);
        });
    }

    // 2)
    @PostMapping("/post-request-dto")
    public void postRequestDtoFun(@RequestBody PostRequestDto postRequestDto) {
        System.out.println(postRequestDto);
    }

}
