package com.example.AOP.controller;

import com.example.AOP.annotation.Decode;
import com.example.AOP.annotation.Timer;
import com.example.AOP.dto.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestApiController {

//    @GetMapping("/get/{id}")
//    public String get(@PathVariable Long id, @RequestParam String name) {
//        return id + " " + name;
//    }
//
//    @PostMapping("/post")
//    public User post(@RequestBody User user) {
//        return user;
//    }

//    @Timer
//    @DeleteMapping("/delete")
//    public void delete() throws InterruptedException {
//        Thread.sleep(1000 * 2);
//    }
//
    @Decode
    @PutMapping("/put")
    public User put(@RequestBody User user) {
        return user;
    }

}