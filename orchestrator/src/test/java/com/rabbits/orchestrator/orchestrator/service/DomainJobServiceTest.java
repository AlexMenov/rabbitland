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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DomainJobServiceTest {

    private static final String message = "first job";
    private static final Long id = 10L;
    private static final String emptyResultError
            = "The obtained result is empty, but it was expected to contain a value.";
    private static final String idResultError
            = "The obtained result ID does not match the expected ID.";
    private static final String messageResultError
            = "The obtained result message does not match the expected message.";
    private static final String updatedMessage = "updated job";
    private static JobRequest jobRequest;
    private static JobRequest updatedJobRequest;
    private static JobDomain expectedJobDomain;
    private static Optional<JobDomain> expectedOptionalJobDomain;
    private static Iterable<JobDomain> listOfExpectedJobDomains;
    private static JobResponse expectedJobResponse;
    private static JobResponse updatedExpectedJobResponse;
    private static DomainJobService domainJobService;
    private static DomainJobRepository domainJobRepository;

    private static void checkResultIfIsPresentAndHasState(Optional<JobResponse> obtainedResult, JobResponse expectedResult) {
        obtainedResult.ifPresentOrElse(
                result -> {
                    assertEquals(expectedResult.id(), result.id(), idResultError);
                    assertEquals(expectedResult.message(), result.message(), messageResultError);
                },
                () -> fail(emptyResultError)
        );
    }

    @BeforeAll
    public static void setup() {
        domainJobRepository = Mockito.mock(DomainJobRepository.class);
        domainJobService = new DomainJobService(domainJobRepository);
        jobRequest = new JobRequest(message);
        expectedJobDomain = JobMapper.toJobDomain(jobRequest);
        expectedJobDomain.setId(id);
        expectedJobResponse = JobMapper.toJobResponse(expectedJobDomain);
        updatedExpectedJobResponse = new JobResponse(id, updatedMessage);
        updatedJobRequest = new JobRequest(updatedMessage);
        expectedOptionalJobDomain = Optional.of(new JobDomain(id, message));
        listOfExpectedJobDomains = Collections.singleton(new JobDomain(id, message));
    }

    @AfterAll
    public static void cleanup() {
        Mockito.reset(domainJobRepository);
    }

    @Test
    public void testAddJobDomainTest() {
        when(domainJobRepository.save(any(JobDomain.class))).thenReturn(expectedJobDomain);
        Optional<JobResponse> obtainedResult = domainJobService.addJobDomain(jobRequest);
        checkResultIfIsPresentAndHasState(obtainedResult, expectedJobResponse);
        verify(domainJobRepository, atLeastOnce()).save(any(JobDomain.class));
    }

    @Test
    public void testFindJobDomainById() {
        when(domainJobRepository.findById(id)).thenReturn(expectedOptionalJobDomain);
        Optional<JobResponse> obtainedResult = domainJobService.findJobDomain(id);
        checkResultIfIsPresentAndHasState(obtainedResult, expectedJobResponse);
        verify(domainJobRepository, atLeastOnce()).findById(id);
    }

    @Test
    public void testUpdateJobDomain() {
        when(domainJobRepository.findById(id)).thenReturn(Optional.of(expectedJobDomain));
        when(domainJobRepository.save(any(JobDomain.class))).thenReturn(expectedJobDomain);
        Optional<JobResponse> obtainedResult = domainJobService.updateJobDomain(id, updatedJobRequest);
        checkResultIfIsPresentAndHasState(obtainedResult, updatedExpectedJobResponse);
        verify(domainJobRepository, atLeastOnce()).save(any(JobDomain.class));
    }

    @Test
    public void testDeleteJobDomainById() {
        when(domainJobRepository.findById(id)).thenReturn(expectedOptionalJobDomain);
        doNothing().when(domainJobRepository).deleteById(id);
        Optional<JobResponse> obtainedResult = domainJobService.deleteJobDomain(String.valueOf(id));
        checkResultIfIsPresentAndHasState(obtainedResult, expectedJobResponse);
        verify(domainJobRepository, atLeastOnce()).deleteById(id);
    }

    @Test
    public void testFindAllJobsDomain() {
        when(domainJobRepository.findAll()).thenReturn(listOfExpectedJobDomains);
        Iterable<Optional<JobResponse>> obtainedResult = domainJobService.findAllJobsDomain();
        obtainedResult.forEach(job -> checkResultIfIsPresentAndHasState(job, expectedJobResponse));
        verify(domainJobRepository, atLeastOnce()).findAll();
    }
}

