package com.MinseoKangQ.consumer.repository;

import com.MinseoKangQ.consumer.model.JobProcess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<JobProcess, String> {
}
