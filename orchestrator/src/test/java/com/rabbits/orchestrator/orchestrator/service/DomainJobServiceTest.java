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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DomainJobServiceTest {

    private static final String message = "first job";
    private static final Long id = new Random().nextLong();
    private static final String emptyResultError
            = "The obtained result is empty, but it was expected to contain a value.";
    private static final String idResultError
            = "The obtained result ID does not match the expected ID.";
    private static final String messageResultError
            = "The obtained result message does not match the expected message.";
    private static final String updatedMessage = "updated job";
    private static JobRequest incommingJobRequest;
    private static JobRequest incommingUpdatedJobRequest;
    private static JobDomain outcommingExpectedJobDomain;
    private static JobResponse outcommingExpectedJobResponse;
    private static JobResponse outcommingUpdatedExpectedJobResponse;
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
        incommingJobRequest = new JobRequest(message);
        outcommingExpectedJobDomain = JobMapper.toJobDomain(incommingJobRequest);
        outcommingExpectedJobDomain.setId(id);
        outcommingExpectedJobResponse = JobMapper.toJobResponse(outcommingExpectedJobDomain);
        outcommingUpdatedExpectedJobResponse = new JobResponse(id, updatedMessage);
        incommingUpdatedJobRequest = new JobRequest(updatedMessage);
    }

    @Test
    public void testAddJobDomainTest() {
        when(domainJobRepository.save(any(JobDomain.class))).thenReturn(outcommingExpectedJobDomain);
        Optional<JobResponse> obtainedResult = domainJobService.addJobDomain(incommingJobRequest);
        checkResultIfIsPresentAndHasState(obtainedResult, outcommingExpectedJobResponse);
        verify(domainJobRepository, atLeastOnce()).save(any(JobDomain.class));
    }

    @Test
    public void testFindJobDomainById() {
        Optional<JobDomain> jobDomain = Optional.of(new JobDomain(id, message));
        when(domainJobRepository.findById(id)).thenReturn(jobDomain);
        Optional<JobResponse> obtainedResult = domainJobService.findJobDomain(id);
        checkResultIfIsPresentAndHasState(obtainedResult, outcommingExpectedJobResponse);
        verify(domainJobRepository, atLeastOnce()).findById(id);
    }

    @Test
    public void testUpdateJobDomain() {
        when(domainJobRepository.findById(id)).thenReturn(Optional.of(outcommingExpectedJobDomain));
        when(domainJobRepository.save(any(JobDomain.class))).thenReturn(outcommingExpectedJobDomain);
        Optional<JobResponse> obtainedResult = domainJobService.updateJobDomain(id, incommingUpdatedJobRequest);
        checkResultIfIsPresentAndHasState(obtainedResult, outcommingUpdatedExpectedJobResponse);
        verify(domainJobRepository, atLeastOnce()).save(any(JobDomain.class));
    }

    @Test
    public void testDeleteJobDomainById() {
        Optional<JobDomain> jobDomain = Optional.of(new JobDomain(id, message));
        when(domainJobRepository.findById(id)).thenReturn(jobDomain);
        doNothing().when(domainJobRepository).deleteById(id);
        Optional<JobResponse> obtainedResult = domainJobService.deleteJobDomain(String.valueOf(id));
        checkResultIfIsPresentAndHasState(obtainedResult, outcommingExpectedJobResponse);
        verify(domainJobRepository, atLeastOnce()).deleteById(id);
    }

    @Test
    public void testFindAllJobsDomain() {
        Iterable<JobDomain> jobDomains = Collections.singleton(new JobDomain(id, message));
        when(domainJobRepository.findAll()).thenReturn(jobDomains);
        Iterable<Optional<JobResponse>> obtainedResult = domainJobService.findAllJobsDomain();
        obtainedResult.forEach(job -> checkResultIfIsPresentAndHasState(job, outcommingExpectedJobResponse));
        verify(domainJobRepository, atLeastOnce()).findAll();
    }

    @AfterAll
    public static void cleanup() {
        Mockito.reset(domainJobRepository);
    }
}

