package com.rabbits.orchestrator.orchestrator.mapper;

import com.rabbits.orchestrator.orchestrator.model.JobDomain;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;

public class JobMapper {
    public static JobResponse toJobResponse (JobDomain domain) {
        return new JobResponse(domain.getId(), domain.getMessage());
    }
    public static JobDomain toJobDomain (JobRequest request) {
        return new JobDomain(request.message());
    }
}
