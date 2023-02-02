package com.example.AOP.controller;

import com.example.AOP.annotation.Decode;
import com.example.AOP.annotation.Timer;
import com.example.AOP.dto.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @GetMapping("/get/{id}")
    public String get(@PathVariable Long id, @RequestParam String name) {
        // 밑의 sout 부분을 aop로 출력할 수 있음 (외부에서 바라봄)
//        System.out.println("get method");
//        System.out.println("get method : " + id);
//        System.out.println("get method : " + name);
        return id + " " + name;
    }

    @PostMapping("/post")
    public User post(@RequestBody User user) {
        // 밑의 sout 부분을 aop로 출력할 수 있음 (외부에서 바라봄)
//        System.out.println("post method : " + user);
        return user;
    }

    @Timer
    @DeleteMapping("/delete")
    public void delete() throws InterruptedException {
        Thread.sleep(1000 * 2);
    }

    @Decode
    @PutMapping("/put")
    public User put(@RequestBody User user) {
        System.out.println("put method");
        System.out.println(user);
        return user;
    }

}