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

    @Test
    public void minusGetTest() throws Exception {
        // http://localhost:8080/api/minus
        mockMvc.perform( // URI 만들고 Request
                MockMvcRequestBuilders.get("http://localhost:8080/api/minus") // get 메소드 test
                        .queryParam("x", "10")
                        .queryParam("y", "10")
        ).andExpect( // 기댓값
                MockMvcResultMatchers.status().isOk()
        ).andExpect( // 기댓값
                MockMvcResultMatchers.content().string("0")
        ).andDo(MockMvcResultHandlers.print()); // MvcResult를 세부 정보를 표준 형식으로 출력
    }

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
}