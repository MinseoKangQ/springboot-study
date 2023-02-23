package com.example.server.controller;

import com.example.server.dto.Req;
import com.example.server.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController { // Client로부터 들어오는 Controller

//    @GetMapping("/hello")
//    public User hello(@RequestParam String name, @RequestParam int age) {
//        User user = new User();
//        user.setName(name);
//        user.setAge(age);
//        log.info("--- server get method ---");
//        log.info("name : {}, age : {}", name, age);
//        return user;
//    }

    // post
//    @PostMapping("/user/{userId}/name/{userName}")
//    public User post(@RequestBody User user,
//                     @PathVariable int userId,
//                     @PathVariable String userName) {
//        log.info("--- server post method ---");
//        log.info("userId : {}, userName : {}", userId, userName);
//        log.info("client req : {}", user);
//        return user;
//    }

//exchange 확인
//    @PostMapping("/user/{userId}/name/{userName}")
//    public User post(@RequestBody User user,
//                     @PathVariable int userId,
//                     @PathVariable String userName,
//                     @RequestHeader("x-authorization") String authorization,
//                     @RequestHeader("custom-header") String customHeader) {
//        log.info("--- [exchange] server post method ---");
//        log.info("[PathVariable] userId : {}, userName : {}", userId, userName);
//        log.info("[RequestBody] client req : {}", user);
//        log.info("[RequestHeader] authorization : {}, custom : {}", authorization, customHeader);
//        return user;
//    }
//}

    //genericExchange 확인
    @PostMapping("/user/{userId}/name/{userName}")
    public Req<User> post(
            @RequestBody Req<User> user,
            @PathVariable int userId,
            @PathVariable String userName,
            @RequestHeader("x-authorization") String authorization,
            @RequestHeader("custom-header") String customHeader) {

        log.info("--- [genericExchange] server post method ---");
        log.info("userId : {}, userName : {}", userId, userName);
        log.info("authorization : {}, custom : {}", authorization, customHeader);
        log.info("client req : {}", user);

        Req<User> response = new Req<>();
        response.setHeader(new Req.Header());
        response.setResBody(user.getResBody());

        return response;
    }
}