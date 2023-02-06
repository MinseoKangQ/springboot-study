package com.example.intercepter.interceptor;

import com.example.intercepter.annotation.Auth;
import com.example.intercepter.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();

        URI uri = UriComponentsBuilder.fromUriString(request.getRequestURI())
                .query(request.getQueryString())
                        .build()
                        .toUri();

        log.info("request url : {}", url);
        boolean hasAnnotation = checkAnnotation(handler, Auth.class);
        log.info("has annotation : {}", hasAnnotation);

        // 나의 서버는 모두 public으로 동작하는데
        // 단, Auth 권한을 가진 요청에 대해서는 세션, 쿠키 등을 보겠다

        // 권한을 가지고 있으면
        if(hasAnnotation) {
            // 권한 체크
            String query = uri.getQuery();
            if(query.equals("name=steve")){
                return true;
            }
            throw new AuthException();
        }
        return true;
        // false이면 이후 Controller까지 못감
    }

    // annotation이 달려있는지 확인
    private boolean checkAnnotation(Object handler, Class clazz) {

        // resource javascript, html 등에 대한 것이면 통과시키기
        if(handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // annotation check
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        if(null != handlerMethod.getMethodAnnotation(clazz) || null != handlerMethod.getBeanType().getAnnotation(clazz)) {
            // Auth annotation이 있을 때는 true
            return true;
        }

         return false;
    }

}
