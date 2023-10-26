package com.rabbits.orchestrator.orchestrator.mapper;

import com.rabbits.orchestrator.orchestrator.model.JobDomain;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.service.DomainJobService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.testcontainers.shaded.org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JobMapperTest {
    private static JobDomain jobDomain;
    private static JobRequest jobRequest;
    private final String message = "first job";
    private final Long id = 1L;


    @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
    @Timeout(value = 200L, unit = TimeUnit.MILLISECONDS)
    public void mapToJobResponseFromJobDomain(Long id, String message) {
        JobResponse jobResponse
                = JobMapper.toJobResponse(new JobDomain(message));

        assertEquals(id, jobResponse.id());
        assertEquals(message, jobResponse.message());
    }

    static Stream<Arguments> getArgumentsForMapToJobResponse () {
        return Stream.of(
                Arguments.of(10, "Alex"),
                Arguments.of(null, "Alex"),
                Arguments.of(10, null)
        );
    }

    @BeforeAll
    public void setup() {
        jobDomain = new JobDomain(1L, message);
        jobRequest = new JobRequest(message);
    }


    @Test
    public void mapToJobDomainFromJobRequest() {
        System.out.println();
        JobDomain domain = JobMapper.toJobDomain(jobRequest);
        domain.setId(id);

        assertEquals(id, domain.getId());
        assertEquals(message, domain.getMessage());
    }
}
