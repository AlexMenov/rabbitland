package com.rabbits.orchestrator.orchestrator.service;

import com.rabbits.orchestrator.orchestrator.mapper.JobMapper;
import com.rabbits.orchestrator.orchestrator.model.JobDomain;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.repository.DomainJobRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DomainJobServiceTest {
    private static final Long id = 10L;
    private static JobResponse expectedJobResponse;
    private static  JobDomain expectedJobDomain;
    private static DomainJobService domainJobService;
    private static DomainJobRepository domainJobRepository;

    public static JobRequest jobRequest;
    public static JobRequest updatedJobRequest;
    public static Optional<JobDomain> expectedOptionalJobDomain;
    public static Iterable<JobDomain> listOfExpectedJobDomains;
    public static List<JobResponse> listOfExpectedJobResponses;
    public static JobResponse updatedExpectedJobResponse;

    private static void checkResultIfIsPresentAndHasState(JobResponse obtainedResult, JobResponse expectedResult) {
        assertEquals(expectedResult.id(), obtainedResult.id(), "The obtained result ID does not match the expected ID.");
        assertEquals(expectedResult.message(), obtainedResult.message(), "The obtained result message does not match the expected message.");
    }

    @BeforeAll
    public static void setup() {
        domainJobRepository = Mockito.mock(DomainJobRepository.class);
        domainJobService = new DomainJobService(domainJobRepository);
        expectedJobDomain = new JobDomain(10L, "first message");
        expectedJobResponse = JobMapper.toJobResponse(expectedJobDomain);
        jobRequest = new JobRequest("first job");
        expectedJobDomain = JobMapper.toJobDomain(jobRequest);
        expectedJobDomain.setId(id);
        expectedJobResponse = JobMapper.toJobResponse(expectedJobDomain);
        updatedExpectedJobResponse = new JobResponse(id, "updated job");
        updatedJobRequest = new JobRequest("updated job");
        expectedOptionalJobDomain = Optional.of(new JobDomain(id, "first job"));
        listOfExpectedJobDomains = Collections.singleton(new JobDomain(id, "first job"));
        listOfExpectedJobResponses = List.of(new JobResponse(id, "first job"));
    }

    @AfterAll
    public static void cleanup() {
        Mockito.reset(domainJobRepository);
    }

    @Test
    void testAddJobDomainTest() {
        when(domainJobRepository.save(any(JobDomain.class))).thenReturn(expectedJobDomain);

        JobResponse obtainedResult = domainJobService.addJobDomain(jobRequest);
        checkResultIfIsPresentAndHasState(obtainedResult, expectedJobResponse);

        verify(domainJobRepository, atLeastOnce()).save(any(JobDomain.class));
    }

    @Test
    void testFindJobDomainById() {
        when(domainJobRepository.findById(id)).thenReturn(expectedOptionalJobDomain);

        JobResponse obtainedResult = domainJobService.findJobDomain(id);
        checkResultIfIsPresentAndHasState(obtainedResult, expectedJobResponse);

        verify(domainJobRepository, atLeastOnce()).findById(id);
    }

    @Test
    void testUpdateJobDomain() {
        when(domainJobRepository.findById(id)).thenReturn(Optional.of(expectedJobDomain));
        when(domainJobRepository.save(any(JobDomain.class))).thenReturn(expectedJobDomain);

        JobResponse obtainedResult = domainJobService.updateJobDomain(id, updatedJobRequest);
        checkResultIfIsPresentAndHasState(obtainedResult, updatedExpectedJobResponse);

        verify(domainJobRepository, atLeastOnce()).save(any(JobDomain.class));
    }

    @Test
    void testDeleteJobDomainById() {
        doNothing().when(domainJobRepository).deleteById(10L);

        domainJobService.deleteJobDomain(id);

        verify(domainJobRepository, atLeastOnce()).deleteById(id);
    }

    @Test
    void testFindAllJobsDomain() {
        when(domainJobRepository.findAll()).thenReturn(listOfExpectedJobDomains);

        List<JobResponse> obtainedResult = domainJobService.findAllJobsDomain();
        obtainedResult.forEach(job -> checkResultIfIsPresentAndHasState(job, expectedJobResponse));

        verify(domainJobRepository, atLeastOnce()).findAll();
    }
}

