package com.MinseoKangQ.producer.service;

import com.MinseoKangQ.producer.model.JobRequest;
import com.google.gson.Gson;
import java.util.UUID;
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
    private final Gson gson;

    public ProducerService(
            @Autowired RabbitTemplate rabbitTemplate,
            @Autowired Queue rabbitQueue,
            @Autowired Gson gson
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitQueue = rabbitQueue;
        this.gson = gson;
    }


    // 메세지 보내는 메소드
    public String send() {
        JobRequest jobRequest = new JobRequest(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(rabbitQueue.getName(), gson.toJson(jobRequest));
        logger.info("Sent Job: {}", jobRequest.getJobId());
        return jobRequest.getJobId(); // 조회 시 key 필요
    }
}
