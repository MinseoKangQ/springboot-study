package com.example.AOP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Base64;

@SpringBootApplication
public class AopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AopApplication.class, args);
		System.out.println("--- main에서 실행 ---");
		System.out.print("steve@gmail.com -> ");
		System.out.print(Base64.getEncoder().encodeToString("steve@gmail.com".getBytes()));
	}

}