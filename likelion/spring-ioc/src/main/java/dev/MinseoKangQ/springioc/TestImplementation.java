package dev.MinseoKangQ.springioc;

import org.springframework.stereotype.Component;

// 구현체
@Component
public class TestImplementation implements TestInterface{
    @Override
    public void sayHello() {
        System.out.println("hello, I'm a bean");
    }
}
