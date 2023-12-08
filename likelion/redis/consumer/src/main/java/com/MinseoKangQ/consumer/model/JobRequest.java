package com.MinseoKangQ.consumer.model;

import java.io.Serializable;

// 요청이 들어옴을 나타내기 위한 객체
public class JobRequest implements Serializable {
    private String jobId;

    public JobRequest() {
    }

    public JobRequest(String jobId) {
        this.jobId = jobId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "JobRequest{" +
                "jobId='" + jobId + '\'' +
                '}';
    }
}
