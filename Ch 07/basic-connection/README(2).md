<h3>01. 실습 내용</h3>

<br>

Client & Server 의 통신

- Client : 8080 port
- Server : 9090 port
- Client가 Server로 요청하고 응답을 받는 형태

<br>

---

<br>

<h3>02. POST - exchange </h3>

<br>

프로젝트 구조


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
        return restTemplateService.exchange();
    }

}
```
<br>

- dto 패키지의 Req 클래스

```java
package com.example.client.dto;

public class Req<T> {

    private Header header;
    private T resBody;

    public static class Header {

        private String responseCode;

        public String getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(String responseCode) {
            this.responseCode = responseCode;
        }

        @Override
        public String toString() {
            return "Header{" +
                    "responseCode='" + responseCode + '\'' +
                    '}';
        }
        
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public T getResBody() {
        return resBody;
    }

    public void setResBody(T resBody) {
        this.resBody = resBody;
    }

    @Override
    public String toString() {
        return "Req{" +
                "header=" + header +
                ", body=" + resBody +
                '}';
    }
}
```

<br>

- dto 패키지의 UserRequest, UserResponse 클래스 작성

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

- service 패키지의 RestTemplateService 클래스 작성

```java
package com.example.client.service;

import com.example.client.dto.UserRequest;
import com.example.client.dto.UserResponse;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RestTemplateService { // response 받아오기

    // http://localhost:9090/api/server/user/{userId}/name/{userName}로 request
    public UserResponse exchange() {

        // URI 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(250, "steve")
                .toUri();

        System.out.println("--- [exchange] client exchange method ---");
        System.out.println(uri);

        // 전송할 데이터 넣기
        UserRequest req = new UserRequest();
        req.setName("steve");
        req.setAge(25);

        // 요청하기
        RequestEntity<UserRequest> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization", "abcd")
                .header("custom-header", "ffff")
                .body(req);

        // 응답 받을 클래스 지정
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.exchange(requestEntity, UserResponse.class);

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
                     @PathVariable String userName,
                     @RequestHeader("x-authorization") String authorization,
                     @RequestHeader("custom-header") String customHeader) {
        log.info("--- [exchange] server post method ---");
        log.info("[PathVariable] userId : {}, userName : {}", userId, userName);
        log.info("[RequestBody] client req : {}", user);
        log.info("[RequestHeader] authorization : {}, custom : {}", authorization, customHeader);
        return user;
    }

}
```

- dto 패키지의 User 클래스 작성

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

<br>

---

<br>

<h3>03. POST - genericExchange </h3>

<br>

통신 원리


<br>

Client

- controller 패키지의 ApiController 클래스 작성

```java
package com.example.client.controller;

import com.example.client.dto.Req;
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
    public Req<UserResponse> getHello() {
        return restTemplateService.genericExchange();
    }

}
```
<br>

- dto 패키지의 Req, UserResponse, UserRequest 클래스 작성

```java
package com.example.client.dto;

public class Req<T> {

    private Header header;

    private T resBody;

    public static class Header {

        private String responseCode;
        public String getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(String responseCode) {
            this.responseCode = responseCode;
        }

        @Override
        public String toString() {
            return "Header{" +
                    "responseCode='" + responseCode + '\'' +
                    '}';
        }
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public T getResBody() {
        return resBody;
    }

    public void setResBody(T resBody) {
        this.resBody = resBody;
    }

    @Override
    public String toString() {
        return "Req{" +
                "header=" + header +
                ", body=" + resBody +
                '}';
    }
}

```

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

- service 패키지의 RestTemplateService 클래스 작성


```java
package com.example.client.service;

import com.example.client.dto.Req;
import com.example.client.dto.UserRequest;
import com.example.client.dto.UserResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RestTemplateService { // response 받아오기

    // http://localhost:9090/api/server/user/{userId}/name/{userName}로 request
    public Req<UserResponse> genericExchange() {

        //URI 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(123, "a")
                .toUri();

        System.out.println("--- [genericExchange] client genericExchange method ---");
        System.out.println(uri);

        // 전송할 데이터 넣기
        UserRequest userRequest = new UserRequest();
        userRequest.setName("b");
        userRequest.setAge(111);

        // Req에 Header와 ResBody 넣기
        // Req에 대한 Generic Type 넣기
        Req<UserRequest> req = new Req<UserRequest>();
        req.setHeader(new Req.Header());
        req.setResBody(userRequest);

        // 요청하기
        RequestEntity<Req<UserRequest>> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization", "abcd")
                .header("custom-header", "ffff")
                .body(req);

        // 응답 받을 클래스 지정
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Req<UserResponse>> response
                = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Req<UserResponse>>() {
        });

        return response.getBody();
    }
}
```

<br>

Server

- controller 패키지의 ServerApiController 클래스 작성

```java
package com.example.server.controller;

import com.example.server.dto.Req;
import com.example.server.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController { // Client로부터 들어오는 Controller

    @PostMapping("/user/{userId}/name/{userName}")
    public Req<User> post(
            @RequestBody Req<User> user,
            @PathVariable int userId,
            @PathVariable String userName,
            @RequestHeader("x-authorization") String authorization,
            @RequestHeader("custom-header") String customHeader) {

        log.info("--- [genericExchange] server post method ---");
        log.info("userId : {}, userName : {}", userId, userName);
        log.info("authorization : {}, custom : {}", authorization, customHeader);
        log.info("client req : {}", user);

        Req<User> response = new Req<>();
        response.setHeader(new Req.Header());
        response.setResBody(user.getResBody());

        return response;
    }
}
```

<br>

- dto 패키지의 Req, User 클래스 작성

```java
package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Req<T> {

    private Header header;
    private T resBody;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Header {
        private String responseCode;
    }
}
```

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

<br>

---

<br>

<h3>04. 정리</h3>