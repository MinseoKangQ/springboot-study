package com.example.client.service;

import com.example.client.dto.Req;
import com.example.client.dto.UserRequest;
import com.example.client.dto.UserResponse;
import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.MediaType;
//import org.springframework.http.RequestEntity;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RestTemplateService { // response 받아오기

    // http://localhost/api/server/hello로 request
//    public UserResponse hello() { // 서버 호출
//        URI uri = UriComponentsBuilder
//                .fromUriString("http://localhost:9090")
//                .path("/api/server/hello")
//                .queryParam("name", "aaaa")
//                .queryParam("age", 99)
//                .encode()
//                .build()
//                .toUri();
//
//        System.out.println("uri : " + uri.toString());
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        // 여기서 get은 '가져오다'가 아니라 'HTTP 메소드의 get'을 뜻함
//        // object 형태로 가져올 것임
////        String result = restTemplate.getForObject(uri, String.class); // uri에 문자열 받을 것
////        return result;
//
//        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);
//
//        System.out.println("--- client get method ---");
//        System.out.println("status code : " + result.getStatusCode());
//        System.out.println("body : " + result.getBody());
//
//        return result.getBody();
//    }


    // http://localhost:9090/api/server/user/{userId}/name/{userName}로 request
//    public UserResponse post() { // 서버 호출
//
//        // URI 만들기
//        URI uri = UriComponentsBuilder
//                .fromUriString("http://localhost:9090")
//                .path("/api/server/user/{userId}/name/{userName}")
//                .encode()
//                .build()
//                .expand(200, "steve")
//                .toUri();
//
//        System.out.println("uri : " + uri);
//
//        // http body를 object로 보내면 object mapper가 json으로 보내서
//        // rest template가 http body 에 json으로 넣어줌
//        UserRequest req = new UserRequest();
//        req.setName("steve");
//        req.setAge(20);
//
//        // 응답 받을 클래스 지정
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<UserResponse> response = restTemplate.postForEntity(uri, req, UserResponse.class);
//
//        System.out.println("--- client get method ---");
//        System.out.println("status code : " + response.getStatusCode());
//        System.out.println("header : " + response.getHeaders());
//        System.out.println("body : " + response.getBody());
//
//        return response.getBody();
//    }
//

    // http://localhost:9090/api/server/user/{userId}/name/{userName}로 request
//    public UserResponse exchange() {
//
//        // URI 만들기
//        URI uri = UriComponentsBuilder
//                .fromUriString("http://localhost:9090")
//                .path("/api/server/user/{userId}/name/{userName}")
//                .encode()
//                .build()
//                .expand(250, "steve")
//                .toUri();
//
//        System.out.println("--- [exchange] client exchange method ---");
//        System.out.println(uri);
//
//        // 전송할 데이터 넣기
//        UserRequest req = new UserRequest();
//        req.setName("steve");
//        req.setAge(25);
//
//        // 요청하기
//        RequestEntity<UserRequest> requestEntity = RequestEntity
//                .post(uri)
//                .contentType(MediaType.APPLICATION_JSON)
//                .header("x-authorization", "abcd")
//                .header("custom-header", "ffff")
//                .body(req);
//
//        // 응답 받을 클래스 지정
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<UserResponse> response = restTemplate.exchange(requestEntity, UserResponse.class);
//
//        return response.getBody();
//
//    }
//
//     http://localhost:9090/api/server/user/{userId}/name/{userName}로 request
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
