package dev.MinseoKangQ.springioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    public int gradeBean() {
        return 10;
    }
}
