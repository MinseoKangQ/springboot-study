package com.MinseoKangQ.producer.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    private final RabbitTemplate rabbitTemplate;
    private final Queue rabbitQueue;

    public ProducerService(
            @Autowired RabbitTemplate rabbitTemplate,
            @Autowired Queue rabbitQueue
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitQueue = rabbitQueue;
    }

    // 스레드가 꼬이는 상황을 방지하기 위해 두 개의 AtomicInteger 생성
    AtomicInteger dots = new AtomicInteger(0);
    AtomicInteger count = new AtomicInteger(0);

    // 메세지 보내는 메소드
    public void send() {
        StringBuilder builder = new StringBuilder("Hello");
        if (dots.incrementAndGet() == 4) {
            dots.set(1);
        }
        builder.append(".".repeat(dots.get()));
        builder.append(count.incrementAndGet());
        String message = builder.toString();

        rabbitTemplate.convertAndSend(rabbitQueue.getName(), message);
        logger.info("Sent message: {}", message);
    }
}
