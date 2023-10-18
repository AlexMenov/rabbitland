package com.rabbits.orchestrator.orchestrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbits.orchestrator.orchestrator.mapper.JobMapper;
import com.rabbits.orchestrator.orchestrator.model.JobDomain;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.service.DomainJobService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(OrchestratorController.class)
public class OrchestratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DomainJobService domainJobService;

    @Test
    public void addJobDomainTest() throws Exception {
        JobRequest jobRequest = new JobRequest("first job");
        JobDomain jobDomain = new JobDomain(1L, "first job");
        JobResponse jobResponse = JobMapper.toJobResponse(jobDomain);

        when(domainJobService.addJobDomain(jobRequest)).thenReturn(Optional.of(jobResponse));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/orchestrator/jobs")
                                .content(new ObjectMapper().writeValueAsString(jobRequest))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }
}
