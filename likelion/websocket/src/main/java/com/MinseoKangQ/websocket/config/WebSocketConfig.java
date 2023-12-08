package com.MinseoKangQ.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트들의 최초 접속 시 엔드포인트 정의
        registry.addEndpoint("/ws/chat"); // 이 경로로 웹 소켓 요청 보냄
        registry.addEndpoint("/ws/chat").withSockJS(); // 웹 소켓을 지원하지 않는 브라우저에 대해 웹 소켓 처럼 동작하도록 함 (Mock 과 유사)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메세지를 클라이언트에게 전달하기 위한 브로커를 생성
        // 클라이언트는 여기서 정의된 destination 에 구독을 하고 메세지를 볼 수 있음
        registry.enableSimpleBroker("/receive-endpoint");
        // 메세지를 받기 위한 엔드포인트
        registry.setApplicationDestinationPrefixes("/send-endpoint");
    }
}
