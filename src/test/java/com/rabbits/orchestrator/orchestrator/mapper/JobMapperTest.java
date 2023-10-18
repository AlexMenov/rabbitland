package com.rabbits.orchestrator.orchestrator.mapper;

import com.rabbits.orchestrator.orchestrator.model.JobDomain;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JobMapperTest {

    private static JobDomain jobDomain;
    private static JobRequest jobRequest;

    @BeforeAll
    public static void init () {
        jobDomain = new JobDomain(1L, "first");
        jobRequest = new JobRequest("first");
    }

    @Test
    public void mapToJobResponseFromJobDomain () {
        JobResponse jobResponse = JobMapper.toJobResponse(jobDomain);

        assertEquals(1L, jobResponse.id());
        assertEquals("first", jobResponse.message());
    }

    @Test
    public void mapToJobDomainFromJobRequest () {
        JobDomain domain = JobMapper.toJobDomain(jobRequest);
        domain.setId(1L);

        assertEquals(1L, domain.getId());
        assertEquals("first", domain.getMessage());
    }

    @AfterAll
    public static void setNullValue () {
        jobDomain = null;
        jobRequest = null;
    }
}
