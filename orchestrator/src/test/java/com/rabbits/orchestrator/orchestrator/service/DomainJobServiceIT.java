package com.rabbits.orchestrator.orchestrator.service;

import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.repository.DomainJobRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {"spring.config.location=classpath:application-properties"})
public class DomainJobServiceIT {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer;

    @BeforeAll
    public void init () {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
                .withReuse(true)
                .withDatabaseName("orchestrator");
    }

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @BeforeAll
    public static void initContainer () {
        postgreSQLContainer = new PostgreSQLContainer<>();
        postgreSQLContainer.withDatabaseName("orchestrator");
        postgreSQLContainer.withUsername("orchestrator");
        postgreSQLContainer.withPassword("orchestrator");
    }

    @Autowired
    private DomainJobService jobService;

    @Autowired
    private DomainJobRepository jobRepository;

    @Test
    public void testAddJobDomain () {
        JobRequest request = new JobRequest("second");
        Optional<JobResponse> response = jobService.addJobDomain(request);

        assertTrue(response.isPresent());
    }
}
