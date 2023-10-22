package com.rabbits.orchestrator.orchestrator;

import com.rabbits.orchestrator.orchestrator.mapper.JobMapper;
import com.rabbits.orchestrator.orchestrator.model.JobDomain;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TestedData {
    public static final String message = "first job";
    public static final Long id = 10L;
    public static final String emptyResultError
            = "The obtained result is empty, but it was expected to contain a value.";
    public static final String idResultError
            = "The obtained result ID does not match the expected ID.";
    public static final String messageResultError
            = "The obtained result message does not match the expected message.";
    public static final String updatedMessage = "updated job";
    public static final String orchestratorApiUrl = "/api/orchestrator/jobs/";
    public static final String expectedJson = "{\"id\":10,\"message\":\"first job\"}";
    public static final String expectedUpdatedJson = "{\"id\":10,\"message\":\"updated job\"}";
    public static final String expectedListJson = "[{\"id\":10,\"message\":\"first job\"}]";
    public static final JobRequest jobRequest;
    public static final JobRequest updatedJobRequest;
    public static final JobDomain expectedJobDomain;
    public static final Optional<JobDomain> expectedOptionalJobDomain;
    public static final Iterable<JobDomain> listOfExpectedJobDomains;
    public static final List<JobResponse> listOfExpectedJobResponses;
    public static final JobResponse expectedJobResponse;
    public static final JobResponse updatedExpectedJobResponse;

    static {
        jobRequest = new JobRequest(message);
        expectedJobDomain = JobMapper.toJobDomain(jobRequest);
        expectedJobDomain.setId(id);
        expectedJobResponse = JobMapper.toJobResponse(expectedJobDomain);
        updatedExpectedJobResponse = new JobResponse(id, updatedMessage);
        updatedJobRequest = new JobRequest(updatedMessage);
        expectedOptionalJobDomain = Optional.of(new JobDomain(id, message));
        listOfExpectedJobDomains = Collections.singleton(new JobDomain(id, message));
        listOfExpectedJobResponses = List.of(new JobResponse(id, message));
    }
}
