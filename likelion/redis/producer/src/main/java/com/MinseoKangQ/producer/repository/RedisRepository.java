package com.MinseoKangQ.producer.repository;

import com.MinseoKangQ.producer.model.JobProcess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository // Redis 사용을 위한 repository
public interface RedisRepository extends CrudRepository<JobProcess, String> {
}
