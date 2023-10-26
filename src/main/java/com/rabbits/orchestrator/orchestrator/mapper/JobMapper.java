package com.rabbits.orchestrator.orchestrator.mapper;

import com.rabbits.orchestrator.orchestrator.exception.JobRequestIllegalException;
import com.rabbits.orchestrator.orchestrator.exception.JobResponseNotFoundException;
import com.rabbits.orchestrator.orchestrator.model.JobDomain;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;

public class JobMapper {
    public static JobResponse toJobResponse(JobDomain domain) {
        try {
            return new JobResponse(domain.getId(), domain.getMessage());
        } catch (Exception e) {
            throw new JobResponseNotFoundException();
        }
    }

    public static JobDomain toJobDomain(JobRequest request) {
        try {
            return new JobDomain(request.message());
        } catch (Exception e) {
            throw new JobRequestIllegalException();
        }
    }
}
