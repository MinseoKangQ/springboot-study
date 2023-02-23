<h3>01. 실습 내용</h3>

<br>

Client & Server 의 통신

- Client : 8080 port
- Server : 9090 port
- Client가 Server로 요청하고 응답을 받는 형태

<br>

---

<br>

<h3>02. GET - basic </h3>

<br>

통신 원리


<br>

Client

- controller 패키지의 ApiController 클래스 작성

```java
package com.example.client.controller;

import com.example.client.dto.UserResponse;
import com.example.client.service.RestTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ApiController {

    private final RestTemplateService restTemplateService;

    public ApiController(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @GetMapping("/hello")
    public UserResponse getHello() {
        return restTemplateService.hello();
    }
}
```

<br>

- dto 패키지의 UserResponse 클래스 작성

```java
package com.example.client.dto;

public class UserResponse {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

<br>

- service 패키지의 RestTemplateService 클래스 작성

```java
package com.example.client.service;

import com.example.client.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@Service
public class RestTemplateService { // response 받아오기

    // http://localhost/api/server/hello로 request
    public UserResponse hello() { // 서버 호출

        // URI 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/hello")
                .queryParam("name", "aaaa")
                .queryParam("age", 99)
                .encode()
                .build()
                .toUri();

        System.out.println("uri : " + uri.toString());

        // 응답 받을 클래스 지정
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);

        System.out.println("--- client get method ---");
        System.out.println("status code : " + result.getStatusCode());
        System.out.println("body : " + result.getBody());

        return result.getBody();
    }
}
```

<br>

Server

- controller 패키지의 ServerApiController 클래스 작성

```java
package com.example.server.controller;

import com.example.server.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController { // Client로부터 들어오는 Controller

    @GetMapping("/hello")
    public User hello(@RequestParam String name, @RequestParam int age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        log.info("--- server get method ---");
        log.info("name : {}, age : {}", name, age);
        return user;
    }
}
```

<br>

- dto 클래스의 User 클래스 작성 (lombok 활용)

```java
package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private int age;
}
```

<br>

실행 결과

- Talend API


- Client


- Server


---

<br>

<h3>03. POST - basic</h3>

<br>

통신 원리

<br>

Client

- controller 패키지의 ApiController 클래스 작성

```java
package com.example.client.controller;

import com.example.client.dto.UserResponse;
import com.example.client.service.RestTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ApiController {

    private final RestTemplateService restTemplateService;

    public ApiController(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @GetMapping("/hello")
    public UserResponse getHello() {
        return restTemplateService.post();
    }
}
```

<br>

- dto 클래스의 UserRequest, UserResponse 클래스 작성

```java
package com.example.client.dto;

public class UserRequest {

    private String name;
    private int age;

    @Override
    public String toString() {
        return "UserResponse{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

```java
package com.example.client.dto;

public class UserResponse {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

<br>

- Service 패키지의 RestTemplateService 클래스 작성

```java
package com.example.client.service;

import com.example.client.dto.UserRequest;
import com.example.client.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@Service
public class RestTemplateService { // response 받아오기

    // http://localhost:9090/api/server/user/{userId}/name/{userName}로 request
    public UserResponse post() { // 서버 호출

        // URI 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(200, "steve")
                .toUri();

        System.out.println("uri : " + uri);

        // http body를 object로 보내면 object mapper가 json으로 보내서
        // rest template가 http body 에 json으로 넣어줌
        UserRequest req = new UserRequest();
        req.setName("steve");
        req.setAge(20);

        // 응답 받을 클래스 지정
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.postForEntity(uri, req, UserResponse.class);

        System.out.println("--- client get method ---");
        System.out.println("status code : " + response.getStatusCode());
        System.out.println("header : " + response.getHeaders());
        System.out.println("body : " + response.getBody());

        return response.getBody();
    }
}
```

<br>

Server

- controller 패키지의 ServerApiController 클래스 작성 

```java
package com.example.server.controller;

import com.example.server.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController { // Client로부터 들어오는 Controller

    @PostMapping("/user/{userId}/name/{userName}")
    public User post(@RequestBody User user,
                     @PathVariable int userId,
                     @PathVariable String userName) {

        log.info("--- server post method ---");
        log.info("userId : {}, userName : {}", userId, userName);
        log.info("client req : {}", user);
        return user;
    }   
}
```

<br>

- dto 패키지의 User 클래스 작성 (lombok 활용)

```java
package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private int age;
}

```

<br>

실행 결과

- Talend API


- Client


- Server