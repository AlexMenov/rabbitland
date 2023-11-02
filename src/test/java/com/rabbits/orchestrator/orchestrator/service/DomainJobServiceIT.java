package com.rabbits.orchestrator.orchestrator.service;

import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Testcontainers
public class DomainJobServiceIT {

    @Container
    public static PostgreSQLContainer<?> container
            = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");
    @Autowired
    private DomainJobService service;

    @BeforeAll
    public static void setUp() {
        container.start();
    }

    @AfterAll
    public static void setDown() {
        container.close();
    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }


    @Nested
    @DisplayName("------ Valid data testing scenarios  ╯°□°）╯ ------")
    class PositiveScenarios {
        static Stream<Arguments> getArgumentsForJobRequest() {
            return Stream.of(
                    Arguments.of(new JobRequest("first job")),
                    Arguments.of(new JobRequest("second job")),
                    Arguments.of(new JobRequest("third job")),
                    Arguments.of(new JobRequest("forth job")),
                    Arguments.of(new JobRequest("fifth job"))
            );
        }

        @ParameterizedTest
        @DisplayName("checking how jobs are added upon request")
        @MethodSource("getArgumentsForJobRequest")
        void shouldAddJobDomainCorrectly(JobRequest request) {
            JobResponse result = service.addJobDomain(request);
            assertAll(
                    () -> {
                        assertNotNull(result);
                        assertNotNull(result.id());
                        assertNotNull(result.message());
                        assertEquals(result.message(), request.message());
                    }
            );
        }

        @ParameterizedTest
        @DisplayName("checking the availability of jobs on request by id")
        @MethodSource("getArgumentsForJobRequest")
        void shouldFindJobDomainCorrectly(JobRequest request) {
            JobResponse jobResponse = service.addJobDomain(request);
            JobResponse result = service.findJobDomain(jobResponse.id());
            assertAll(
                    () -> {
                        assertNotNull(result);
                        assertNotNull(result.id());
                        assertNotNull(result.message());
                        assertEquals(result.message(), jobResponse.message());
                        assertEquals(result.id(), jobResponse.id());
                    }
            );
        }

        @ParameterizedTest
        @DisplayName("checking if jobs are updated correctly upon request")
        @MethodSource("getArgumentsForJobRequest")
        void shouldServiceUpdateJobDomain(JobRequest request) {
            JobResponse jobResponse = service.addJobDomain(request);
            JobResponse result = service.updateJobDomain(jobResponse.id(), new JobRequest(request.message() + " updated"));
            assertAll(
                    () -> {
                        assertNotNull(result);
                        assertNotNull(result.id());
                        assertNotNull(result.message());
                        assertEquals(result.message(), request.message() + " updated");
                        assertEquals(result.id(), jobResponse.id());
                    }
            );
        }

        @ParameterizedTest
        @DisplayName("checking that all jobs are returned correctly")
        @MethodSource("getArgumentsForJobRequest")
        void shouldServiceFindAllJobDomains(JobRequest request) {
            service.addJobDomain(request);
            List<JobResponse> result = service.findAllJobsDomain();
            result.forEach(job -> assertAll(
                    () -> {
                        assertNotNull(job);
                        assertNotNull(job.id());
                        assertNotNull(job.message());
                    }
            ));
        }

        @ParameterizedTest
        @DisplayName("checking the correctness of job deletion by id")
        @MethodSource("getArgumentsForJobRequest")
        void shouldServiceDeleteJobDomain(JobRequest request) {
            JobResponse response = service.addJobDomain(request);
            Long jobId = response.id();

            service.deleteJobDomain(jobId);

            assertThrows(EntityNotFoundException.class, () -> service.findJobDomain(jobId));
        }
    }
}
