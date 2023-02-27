<h3>01. 실습 내용</h3>

<br>

1) 일반 테스트 코드 작성 (통합 테스트)
2) CRUD 테스트 코드 작성 (단위 테스트)

<br>

---

<br>

<h3>02. main 패키지</h3>

<br>

프로젝트 구조



<br>

https://velog.io/@minseokangq/JUnit 에서 작성한 코드와 일부 다름

- @Component : Spring에서 관리되도록 함
- DollarCaculator를 @Component로 등록하면, 그곳에서 주입받는 MarketApi도 @Component로 등록 해줘야함
- final과 @RequiredArgsConstructor : 생성자 주입 코드 작성하지 않아도 동일하게 외부에서 주입 받음

<br>

Calculator 클래스 작성

```java
package com.example.springcalculator.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Calculator {

    private final ICalculator iCalculator;

    public int sum(int x, int y) {
        this.iCalculator.init();
        return this.iCalculator.sum(x,y);
    }

    public int minus(int x, int y) {
        this.iCalculator.init();
        return this.iCalculator.minus(x,y);
    }

}
```

<br>

DollarCalculator 클래스 작성

```java
package com.example.springcalculator.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DollarCalculator implements ICalculator {

    private int price = 1;
    private final MarketApi marketApi;

    @Override
    public void init() {
        this.price = marketApi.connect();
    }

    @Override
    public int sum(int x, int y) {
        x *= price;
        y *= price;
        return x+y;
    }

    @Override
    public int minus(int x, int y) {
        x *= price;
        y *= price;
        return x-y;
    }
}
```

<br>

ICalculator 인터페이스 작성

```java
package com.example.springcalculator.component;

public interface ICalculator {

    int sum(int x, int y);
    int minus(int x, int y);
    void init();
}
```

<br>

MarketApi 클래스 작성

```java
package com.example.springcalculator.component;

import org.springframework.stereotype.Component;

@Component
public class MarketApi {
    public int connect() {
        return 1100; // 환율 리턴
    }
}
```

<br>

CalculatorApiController 클래스 작성

```java
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
```

<br>

Req, Res 클래스 작성

```java
package com.example.springcalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Req {

    private int x;
    private int y;

}
```

```java
package com.example.springcalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Res {

    private int result;
    private Body response;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Body {
        private String resultCode = "OK";
    }

}
```

---

<br>

<h3>03. test 패키지 - 통합 테스트 (component 패키지)</h3>

<br>

Annotation
- @SpringBootTest : 스프링부트에서 관리되는 모든 Bean이 등록됨
- @MockBean
    - Mokito의 Mock 객체들을 Spring의 ApplicationContext에 넣어줌
    - 동일한 타입의 Bean이 존재하면 MockBean으로 교체
- @Autowired : 생성자, 필드, setter 등의 메서드를 Spring의 의존성 주입 기능에 의해 자동으로 사용하기 위함

<br>

테스트 코드
- Mockito.when(특정 메소드).thenReturn(value);
- Assertions.assertEquals(예상값, 변수);

<br>

DollarCalculatorTest 테스트 코드 작성

```java
package com.example.springcalculator.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class DollarCalculatorTest {

    @MockBean
    private MarketApi marketApi;

    @Autowired
    private DollarCalculator dollarCalculator;

    @Test
    public void dollarCalculatorTest() {
        Mockito.when(marketApi.connect()).thenReturn(3000);

        dollarCalculator.init();
        int sum = dollarCalculator.sum(10, 10);
        int minus = dollarCalculator.minus(10, 10);

        Assertions.assertEquals(60000, sum);
        Assertions.assertEquals(0, minus);
    }
    
}
```

<br>

---

<br>

<h3>04. test 패키지 - 단위 테스트 (controller 패키지)</h3>

<br>

Annotation
- @WebMvcTest(테스트 할 컨트롤러.class) : 오직 Spring MVC components에만 집중하는 Spring MVC 테스트를 진행할 때 사용
- @AutoConfigureWebMvc : 자동 환경설정, 전형적으로 Spring MVC 테스트를 위한 것
- @BeforeEach : 각 테스트 코드가 실행되기 전에 호출

<br>

테스트 코드

- MockMvc 객체
    - @Autowired로 주입받아서 테스트 진행
    - perform 메소드 : Request 수행 및 asserting, expectations과 같은 타입 리턴
    - andExpect 메소드 : 기대값
    - andDo 메소드

- post 메소드 테스트 시
    - ObjectMapper로 객체를 String 타입으로 바꿔주기

<br>

CalCulatorControllerTest 테스트 코드 작성

```java
package com.example.springcalculator.controller;

import com.example.springcalculator.component.Calculator;
import com.example.springcalculator.component.DollarCalculator;
import com.example.springcalculator.component.MarketApi;
import com.example.springcalculator.dto.Req;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CalculatorApiController.class)
@AutoConfigureWebMvc
@Import({Calculator.class, DollarCalculator.class})
public class CalculatorControllerTest {

    @MockBean
    private MarketApi marketApi;

    @Autowired
    private MockMvc mockMvc; // mvc를 mocking으로 테스트

    @BeforeEach
    public void init() {
        Mockito.when(marketApi.connect()).thenReturn(3000);
    }

    @Test
    public void sumGetTest() throws Exception {
        // http://localhost:8080/api/sum
        mockMvc.perform(
                MockMvcRequestBuilders.get("http://localhost:8080/api/sum") // get 메소드 test
                        .queryParam("x", "10")
                        .queryParam("y", "10")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.content().string("60000")
        ).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void minusGetTest() throws Exception {
        // http://localhost:8080/api/minus
        mockMvc.perform(
                MockMvcRequestBuilders.get("http://localhost:8080/api/minus") // get 메소드 test
                        .queryParam("x", "10")
                        .queryParam("y", "10")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.content().string("0")
        ).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void minusPostTest() throws Exception {

        // 요청을 보내기 위한 객체 생성
        Req req = new Req();
        req.setX(10);
        req.setY(10);

        // ObjectMapper 객체를 사용해 RequestBody 부분 String으로 바꿔서 json 형태로
        String json = new ObjectMapper().writeValueAsString(req);

        mockMvc.perform(
                MockMvcRequestBuilders.post("http://localhost:8080/api/minus") // post 메소드 test
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json) // RequestBody
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.result").value("0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.response.resultCode").value("OK")
        )
        .andDo(MockMvcResultHandlers.print());
    }
}
```

<br>

---

<br>

<h3>05. 실행 결과</h3>

<br>

DollarCalculatorTest (통합 테스트)

<br>

CalculatorControllerTest (단위 테스트)

- sumGetTest

- minusGetTest

- minusPostTest

<br>

---

<br>

<h3>06. 정리</h3>

<br>

@Component 사용
- 해당 클래스에서 주입받는 객체가 있으면 주입받는 객체도 @Component로 등록

<br>

외부에서 주입 받기
- 외부에서 주입받는 클래스 final로 선언
- 해당 클래스에 @RequiredArgsConstructor 작성하면 외부에서 주입 받을 수 있음

<br>

Annotation
- @SpringBootTest : 스프링에서 관리하는 모든 Bean이 등록됨
- @Autowired : 생성자, 필드, setter 등의 메서드를 Spring의 의존성 주입 기능에 의해 자동으로 사용하기 위함
- @WebMvcTest(컨트롤러.class) : 오직 Spring MVC components에만 집중하는 Spring MVC 테스트를 작성할 때 사용
- @AutoConfigureWebMvc : 자동 환경설정이라는 뜻, 전형적인 Spring MVC 테스트를 위한 것
- @MockBean : Mokito의 Mock 객체들을 Spring의 ApplicationContext에 넣어줌, 동일한 타입의 Bean이 존재하면 MockBean으로 교체
- @BeforeEach : 각 테스트 코드가 실행되기 전에 메소드 호출

<br>

통합 테스트 코드

```java
Mockito.when(marketApi.connect()).thenReturn(3000);
Assertions.assertEquals(60000, sum);
```

<br>

단위 테스트 코드 - get 메소드 

```java
@Test
    public void sumGetTest() throws Exception {
        // http://localhost:8080/api/sum
        mockMvc.perform( // URI 만들고 Request
                MockMvcRequestBuilders.get("http://localhost:8080/api/sum") // get 메소드 test
                        .queryParam("x", "10")
                        .queryParam("y", "10")
        ).andExpect( // 기댓값
                MockMvcResultMatchers.status().isOk()
        ).andExpect( // 기댓값
                MockMvcResultMatchers.content().string("60000")
        ).andDo(MockMvcResultHandlers.print()); // MvcResult를 세부 정보를 표준 형식으로 출력
    }
```

<br>

단위 테스트 코드 - post 메소드

```java
@Test
    public void minusPostTest() throws Exception {

        // 요청을 보내기 위한 객체 생성
        Req req = new Req();
        req.setX(10);
        req.setY(10);

        // ObjectMapper 객체를 사용해 RequestBody 부분 String으로 바꿔서 json 형태로
        String json = new ObjectMapper().writeValueAsString(req);

        mockMvc.perform( // URI 만들고 Request
                MockMvcRequestBuilders.post("http://localhost:8080/api/minus") // post 메소드 test
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json) // RequestBody
        ).andExpect( // 기댓값
                MockMvcResultMatchers.status().isOk()
        ).andExpect( // 기댓값
                MockMvcResultMatchers.jsonPath("$.result").value("0")
        ).andExpect( // 기댓값
                MockMvcResultMatchers.jsonPath("$.response.resultCode").value("OK")
        )
        .andDo(MockMvcResultHandlers.print()); // MvcResult를 세부 정보를 표준 형식으로 출력
    }
```