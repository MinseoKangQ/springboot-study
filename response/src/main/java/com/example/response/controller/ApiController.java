package com.example.response.controller;

import com.example.response.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    // 1 TEXT
    @GetMapping("/text")
    public String text(@RequestParam String account) {
        return account;
    }

    // 2 JSON
    @PostMapping("/json")
    public User json(@RequestBody User user) {
        return user;
    }

    // 3 ResponseEntity
    @PutMapping("/put")
    public ResponseEntity<User> put(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
