<h3>01. 네이버 Open API 이용</h3>

<br>

개발 가이드
- Documents - 서비스 API - 검색
- https://developers.naver.com/docs/serviceapi/search/local/local.md#%EC%A7%80%EC%97%AD

<br>

애플리케이션 등록(API 이용신청)]
- https://developers.naver.com/apps/#/register
- 애플리케이션 이름, 사용 API 작성 후 등록
- 애플리케이션 정보에서 Client ID와 Client Secret는 요청 시 Header에 넣어야 함

<br>

기본적으로 동작하는지 확인
- Talend API에서 https://openapi.naver.com/v1/search/local.json?query=%EC%A3%BC%EC%8B%9D&display=10&start=1&sort=random
- Header 값 넣는 것 잊지 말기

<br>

동작 확인

<br>

---

<br>

<h3>02. @GetMapping으로 API 작성</h3>

<br>

1. URI 만들기
2. RequestEntity로 header값 지정
3. ResponseEntity로 응답 받을 클래스 지정

```java
package com.example.server.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.nio.charset.Charset;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController {

    @GetMapping("/naver")
    public String naver() {

        // 공식 문서의 [파라미터] 부분 참고
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query", "중국집")
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "random")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();

        log.info("--- naver ---");
        log.info("uri : {}", uri);

        // Header를 위함
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "H7549Uas4jQ3lFrDEfGl")
                .header("X-Naver-Client-Secret", "5yxA_JBuAp")
                .build();

        // 응답 받을 클래스 지정
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        return result.getBody();
    }

}
```

<br>

---

<br>

<h3>03. 실행 결과</h3>

<br>

GET 메소드 이므로 Talend API, 웹 브라우저 둘 다 확인 가능

- Talend API 실행 결과


<br>

- 웹 브라우저 실행 결과


<br>

응답이 내려오면 해당 응답이 Json 형태인지 확인 (https://jsonformatter.curiousconcept.com/#)