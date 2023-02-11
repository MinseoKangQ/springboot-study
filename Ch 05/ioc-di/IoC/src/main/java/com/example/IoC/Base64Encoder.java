package com.example.IoC;

import org.springframework.stereotype.Component;
import java.util.Base64;

@Component("base74Encoder")
// Spring에게 객체로 관리해달라는 annotation, 이 객체가 Bean으로 등록됨
public class Base64Encoder implements IEncoder {
    @Override
    public String encode(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes());
    }
}
