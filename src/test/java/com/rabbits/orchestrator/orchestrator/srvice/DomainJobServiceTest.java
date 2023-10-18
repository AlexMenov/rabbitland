package com.rabbits.orchestrator.orchestrator.srvice;

import com.rabbits.orchestrator.orchestrator.mapper.JobMapper;
import com.rabbits.orchestrator.orchestrator.model.JobDomain;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.repository.DomainJobRepository;
import com.rabbits.orchestrator.orchestrator.service.DomainJobService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DomainJobServiceTest {

    @Mock
    private DomainJobRepository domainJobRepository;

    private DomainJobService domainJobService;

    @BeforeEach
    public void setup() {
        domainJobService = new DomainJobService(domainJobRepository);
    }

    @Test
    public void creatingJobSuccess () {
        JobRequest request = new JobRequest("first");
        Optional<JobResponse> jobResponse = domainJobService.findJobDomain(1L);
        domainJobRepository.save(JobMapper.toJobDomain(request));
    }

    @Test
    public void addJobDomainTest() {
        JobRequest jobRequest = new JobRequest("first job");
        JobDomain jobDomain = JobMapper.toJobDomain(jobRequest);
        jobDomain.setId(1L);

        when(domainJobRepository.save(any(JobDomain.class))).thenReturn(jobDomain);

        Optional<JobResponse> result = domainJobService.addJobDomain(jobRequest);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().id());
        assertEquals("first job", result.get().message());
    }
}

