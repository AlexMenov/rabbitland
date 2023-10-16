package com.rabbits.orchestrator.orchestrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbits.orchestrator.orchestrator.model.JobRequestModel;
import com.rabbits.orchestrator.orchestrator.repository.DomainJobRepository;
import com.rabbits.orchestrator.orchestrator.service.DomainJobService;
import com.rabbits.orchestrator.orchestrator.service.DomainJobServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrchestratorController.class)
class OrchestratorControllerTest {

    @MockBean
    @Autowired
    private DomainJobService domainJobService;
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addJobDomain() throws Exception {
        JobRequestModel job = new JobRequestModel();
        job.setMessage("first hello");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/employees")
                        .content(objectMapper.writeValueAsString(job))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void getJobDomain() {
    }

    @Test
    void findAllJobs() {
    }

    @Test
    void updateJobDomain() {
    }

    @Test
    void deleteJobDomain() {
    }
}