package com.MinseoKangQ.consumer.service;

import com.MinseoKangQ.consumer.model.JobProcess;
import com.MinseoKangQ.consumer.model.JobRequest;
import com.MinseoKangQ.consumer.repository.RedisRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "boot.amqp.worker-queue")
public class ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
    private final RedisRepository redisRepository;
    private final Gson gson;

    public ConsumerService(@Autowired RedisRepository redisRepository, @Autowired Gson gson) {
        this.redisRepository = redisRepository;
        this.gson = gson;
    }

    @RabbitHandler // 들어오는 요청 처리
    public void receive(String message) throws InterruptedException {
//        logger.info("Received: {}", message);
        String jobId = "";

        try {

            // 큐에서 데이터 받아옴
            JobRequest newJob = gson.fromJson(message, JobRequest.class);
            jobId = newJob.getJobId();
            logger.info("Received Job: {}", jobId);

            // 받아온 jobId를 기준으로 repository 에 save -> 프로세스 생성
            JobProcess jobProcess = new JobProcess();
            jobProcess.setId(newJob.getJobId());
            jobProcess.setMessage("Job being processed");
            jobProcess.setStatus(1);
            jobProcess.setResult("");
            redisRepository.save(jobProcess);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }

        // 하위 코드는 처리 내용
        // 5초
        Thread.sleep(5000);

        // 업데이트
        JobProcess jobProcess = redisRepository.findById(jobId).get();
        jobProcess.setId(jobId);
        jobProcess.setMessage("Finished");
        jobProcess.setStatus(0);
        jobProcess.setResult("Success");
        redisRepository.save(jobProcess);
        logger.info("Finished Job: {}", jobId);
    }
}
