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
        return user; // 200 OK
    }

    // 3 ResponseEntity
    @PutMapping("/response-entity")
    public ResponseEntity<User> put(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(user); // HttpStatus 지정 가능
    }
}