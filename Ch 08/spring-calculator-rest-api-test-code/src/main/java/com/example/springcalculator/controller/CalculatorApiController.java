package com.example.springcalculator.controller;

import com.example.springcalculator.component.Calculator;
import com.example.springcalculator.dto.Req;
import com.example.springcalculator.dto.Res;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalculatorApiController {

    private final Calculator calculator;

    @GetMapping("/sum") // - Get 테스트
    public int sum(@RequestParam int x, @RequestParam int y) {
        return calculator.sum(x,y);
    }

    @GetMapping("/minus") // - Get 테스트
    public int minus(@RequestParam int x, @RequestParam int y) {
        return calculator.minus(x,y);
    }

    @PostMapping("/minus") // - Post 테스트
    public Res minus(@RequestBody Req req) {

        int result = calculator.minus(req.getX(), req.getY());

        Res res = new Res();
        res.setResult(result);
        res.setResponse(new Res.Body());
        return res;
    }

}