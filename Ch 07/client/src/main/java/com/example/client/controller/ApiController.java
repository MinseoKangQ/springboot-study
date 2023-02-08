package com.example.client.controller;

import com.example.client.dto.Req;
import com.example.client.dto.UserResponse;
import com.example.client.service.RestTempleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ApiController {

    private final RestTempleteService restTempleteService;

    public ApiController(RestTempleteService restTempleteService) {
        this.restTempleteService = restTempleteService;
    }

//    @GetMapping("/hello")
//    public UserResponse getHello() {
//        return restTempleteService.hello();
//    }

//    @GetMapping("/hello")
//    public UserResponse getHello() {
//        return restTempleteService.post();
//    }

//    @GetMapping("/hello")
//    public UserResponse getHello() {
//        return restTempleteService.exchange();
//    }

    @GetMapping("/hello")
    public Req<UserResponse> getHello() {
        return restTempleteService.genericExchange();
    }
}
