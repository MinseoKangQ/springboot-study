package com.example.filter.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/api/user/*")
public class GlobalFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 전처리 구간
        // 형 변환
        ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest)request);
        ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse)response);

        // 실행
        chain.doFilter(httpServletRequest, httpServletResponse);

        String url = httpServletRequest.getRequestURI(); // 어떤 주소로 가져왔는지

        // 후처리 구간
        // 응답 받기
        String reqContent = new String(httpServletRequest.getContentAsByteArray());
        log.info("request url : {}, requestBody : {}", url, reqContent);

        String resContent = new String(httpServletResponse.getContentAsByteArray());
        int httpStatus = httpServletResponse.getStatus();

        // 위에서 내용을 빼가기 때문에 이 메소드 호출하여 Client가 제대로 된 응답을 받을 수 있도록 함
        httpServletResponse.copyBodyToResponse();

        log.info("response status : {}, responseBody : {}", httpStatus, resContent);
    }

}
