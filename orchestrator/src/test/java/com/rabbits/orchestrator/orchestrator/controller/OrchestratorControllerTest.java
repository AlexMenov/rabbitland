package com.rabbits.orchestrator.orchestrator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbits.orchestrator.orchestrator.mapper.JobMapper;
import com.rabbits.orchestrator.orchestrator.model.JobDomain;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.repository.DomainJobRepository;
import com.rabbits.orchestrator.orchestrator.service.DomainJobService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.expression.Operation;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.google.common.collect.Iterables;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.rabbits.orchestrator.orchestrator.TestedData.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(OrchestratorController.class)
public class OrchestratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DomainJobService domainJobService;



    @Test
    public void addJobDomainTest() throws Exception {
        when(domainJobService.addJobDomain(jobRequest)).thenReturn(Optional.of(expectedJobResponse));
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put(orchestratorApiUrl)
                                .content(new ObjectMapper().writeValueAsString(jobRequest))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":10,\"message\":\"first job\"}"))
                .andReturn();
    }

    @Test
    public void testGetJobDomain() throws Exception {
        when(domainJobService.findJobDomain(id)).thenReturn(Optional.of(expectedJobResponse));
    }

    @Test
    public void testUpdateJobDomain() throws Exception {

        when(domainJobService.updateJobDomain(id, updatedJobRequest)).thenReturn(Optional.of(updatedExpectedJobResponse));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(orchestratorApiUrl + id)
                                .content(new ObjectMapper().writeValueAsString(updatedJobRequest))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":10,\"message\":\"updated job\"}"))
                .andReturn();
    }

    @Test
    public void testDeleteJobDomain() throws Exception {

        when(domainJobService.deleteJobDomain(id)).thenReturn(Optional.of(expectedJobResponse));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete(orchestratorApiUrl + id)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":10,\"message\":\"first job\"}"))
                .andReturn();
    }

    @Test
    public void testFindAllJobsDomains() throws Exception {
        when(domainJobService.findAllJobsDomain()).thenReturn(listOfExpectedJobResponses);
    }
}

































