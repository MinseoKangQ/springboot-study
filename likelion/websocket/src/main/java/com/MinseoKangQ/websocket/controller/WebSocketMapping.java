package com.MinseoKangQ.websocket.controller;

import com.MinseoKangQ.websocket.model.ChatMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketMapping {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketMapping.class);
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketMapping(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/ws/chat")
    public void sendChat(ChatMessage chatMessage) {
        logger.info(chatMessage.toString());
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        chatMessage.setTime(time);
        simpMessagingTemplate.convertAndSend(String.format("/receive-endpoint/%s", chatMessage.getRoomId()),
                chatMessage
        );
    }


}
