package com.rabbits.orchestrator.orchestrator.mapper;

import com.rabbits.orchestrator.orchestrator.model.JobDomain;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JobMapperTest {

    private static JobDomain jobDomain;
    private static JobRequest jobRequest;

    private final String message = "first job";
    private final Long id = 1L;

    @BeforeAll
    public void setup() {
        jobDomain = new JobDomain(1L, message);
        jobRequest = new JobRequest(message);
    }

    @Test
    public void mapToJobResponseFromJobDomain() {
        JobResponse jobResponse = JobMapper.toJobResponse(jobDomain);

        assertEquals(id, jobResponse.id());
        assertEquals(message, jobResponse.message());
    }

    @Test
    public void mapToJobDomainFromJobRequest() {
        JobDomain domain = JobMapper.toJobDomain(jobRequest);
        domain.setId(id);

        assertEquals(id, domain.getId());
        assertEquals(message, domain.getMessage());
    }
}
