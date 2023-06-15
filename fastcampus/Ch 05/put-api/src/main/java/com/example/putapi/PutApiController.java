package com.example.putapi;

import com.example.putapi.dto.PostRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PutApiController {

    // response
    @PutMapping("/put")
    public PostRequestDto put(@RequestBody PostRequestDto requestDto) {
        System.out.println(requestDto);
        return requestDto;
    }

    @PutMapping("/put/{userId}")
    public PostRequestDto putPathVariable(@RequestBody PostRequestDto requestDto, @PathVariable(name = "userId") Long id) {
        System.out.println(id);
        return requestDto;
    }

}
