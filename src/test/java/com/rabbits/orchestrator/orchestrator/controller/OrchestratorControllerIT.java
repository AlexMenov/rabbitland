package com.rabbits.orchestrator.orchestrator.controller;

import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.repository.DomainJobRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class OrchestratorControllerIT {
    @Container
    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:latest")
                    .withUsername("test")
                    .withDatabaseName("test")
                    .withPassword("test");
    public String URL;
    @LocalServerPort
    private Integer port;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DomainJobRepository repository;

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @BeforeAll
    public static void setUp() {
        container.start();
    }

    @AfterAll
    public static void setDown() {
        container.close();
    }

    static Stream<Arguments> getArgumentsForJobRequest() {
        return Stream.of(
                Arguments.of(new JobRequest("first job")),
                Arguments.of(new JobRequest("second job")),
                Arguments.of(new JobRequest("third job")),
                Arguments.of(new JobRequest("fourth job")),
                Arguments.of(new JobRequest("fifth job"))
        );
    }

    @Test
    void contextLoads() {
    }

    @BeforeEach
    public void set() {
        URL = "http://localhost:" + port + "/api/orchestrator/jobs/";
    }

    @AfterEach
    public void clear() {
        repository.deleteAll();
    }

    @ParameterizedTest
    @DisplayName("checking how jobs are added upon request")
    @MethodSource("getArgumentsForJobRequest")
    public void shouldAddJobDomain(JobRequest request) {
        ResponseEntity<JobResponse> result = restTemplate.postForEntity(URL, request, JobResponse.class);

        assertAll(
                () -> {
                    assertNotNull(Objects.requireNonNull(result.getBody()).id());
                    assertNotNull(result.getBody().message());
                    assertEquals(request.message(), result.getBody().message());
                    assertEquals(CREATED, result.getStatusCode());
                }
        );
    }

    @ParameterizedTest
    @DisplayName("checking the availability of jobs on request by id")
    @MethodSource("getArgumentsForJobRequest")
    public void shouldFindJobDomainCorrectly(JobRequest request) {
        ResponseEntity<JobResponse> result = restTemplate.postForEntity(URL, request, JobResponse.class);

        ResponseEntity<JobResponse> finalResult = restTemplate.getForEntity(URL + Objects.requireNonNull(result.getBody()).id(), JobResponse.class);

        assertAll(
                () -> {
                    assertNotNull(Objects.requireNonNull(finalResult.getBody()).id());
                    assertNotNull(finalResult.getBody().message());
                    assertEquals(request.message(), finalResult.getBody().message());
                    assertEquals(OK, finalResult.getStatusCode());
                }
        );
    }

    @ParameterizedTest
    @DisplayName("checking if jobs are updated correctly upon request")
    @MethodSource("getArgumentsForJobRequest")
    public void shouldServiceUpdateJobDomain(JobRequest request) {
        ResponseEntity<JobResponse> result = restTemplate.postForEntity(URL, request, JobResponse.class);

        HttpEntity<JobRequest> httpEntity = new HttpEntity<>(new JobRequest(request.message() + " updated"));

        ResponseEntity<JobResponse> finalResult
                = restTemplate.exchange(URL + Objects.requireNonNull(result.getBody()).id(), PUT, httpEntity, JobResponse.class);

        assertAll(
                () -> {
                    assertNotNull(finalResult);
                    assertNotNull(Objects.requireNonNull(finalResult.getBody()).id());
                    assertNotNull(finalResult.getBody().message());
                    assertEquals(finalResult.getBody().message(), request.message() + " updated");
                    assertEquals(finalResult.getBody().id(), Objects.requireNonNull(result.getBody()).id());
                    assertEquals(OK, finalResult.getStatusCode());
                }
        );
    }

    @ParameterizedTest
    @DisplayName("checking that all jobs are returned correctly")
    @MethodSource("getArgumentsForJobRequest")
    public void shouldServiceFindAllJobDomains(JobRequest request) {
        ResponseEntity<JobResponse> result = restTemplate.postForEntity(URL, request, JobResponse.class);
        ResponseEntity<JobResponse[]> finalResult = restTemplate.getForEntity(URL, JobResponse[].class);
        assertEquals(OK, finalResult.getStatusCode()); // equals result
        assertEquals(1, Objects.requireNonNull(finalResult.getBody()).length);
        Stream<JobResponse> stream = Arrays.stream(Objects.requireNonNull(finalResult.getBody()));
//        stream.filter(job -> job.equals(result.getBody())).count();
        stream
                .forEach(job -> assertAll(
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
    public void shouldServiceDeleteJobDomain(JobRequest request) {
        ResponseEntity<JobResponse> result = restTemplate.postForEntity(URL, request, JobResponse.class);
        ResponseEntity<Void> deleteResponse
                = restTemplate.exchange(URL + Objects.requireNonNull(result.getBody()).id(), DELETE, null, Void.class);

        assertEquals(NO_CONTENT, deleteResponse.getStatusCode());
    }

}



