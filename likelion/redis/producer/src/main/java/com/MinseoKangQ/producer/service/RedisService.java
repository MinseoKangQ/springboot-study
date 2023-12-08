package com.MinseoKangQ.producer.service;

import com.MinseoKangQ.producer.model.JobProcess;
import com.MinseoKangQ.producer.repository.RedisRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RedisService {

    private final RedisRepository redisRepository;

    public RedisService(@Autowired RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    // 성공 여부
    public JobProcess retrieveJob(String jobId) {
        Optional<JobProcess> jobProcess = this.redisRepository.findById(jobId);
        if (jobProcess.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return jobProcess.get();
    }
}
