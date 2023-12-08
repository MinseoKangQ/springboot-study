package com.MinseoKangQ.producer.controller;

import com.MinseoKangQ.producer.model.JobProcess;
import com.MinseoKangQ.producer.service.ProducerService;
import com.MinseoKangQ.producer.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    private final ProducerService producerService;
    private final RedisService redisService;

    public ProducerController(@Autowired ProducerService producerService, @Autowired RedisService redisService) {
        this.producerService = producerService;
        this.redisService = redisService;
    }

    @GetMapping("/")
    public String sendMessage() {
        return producerService.send();
    }

    @GetMapping("{jobId}")
    public JobProcess getResult(@PathVariable("jobId") String jobId) {
        return redisService.retrieveJob(jobId);
    }
}
