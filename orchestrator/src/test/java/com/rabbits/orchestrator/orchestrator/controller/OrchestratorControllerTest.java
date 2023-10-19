package com.rabbits.orchestrator.orchestrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.service.DomainJobService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static com.rabbits.orchestrator.orchestrator.TestedData.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(OrchestratorController.class)
public class OrchestratorControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DomainJobService domainJobService;

    private void createMockMvcPerform(MockHttpServletRequestBuilder request, String expectedJson) throws Exception {
        mockMvc.perform(request
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void addJobDomainTest() throws Exception {
        when(domainJobService.addJobDomain(jobRequest)).thenReturn(Optional.of(expectedJobResponse));
        createMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.PUT, orchestratorApiUrl)
                        .content(mapper.writeValueAsString(jobRequest)),
                expectedJson
        );
        verify(domainJobService, atLeastOnce()).addJobDomain(any(JobRequest.class));
    }

    @Test
    public void testGetJobDomain() throws Exception {
        when(domainJobService.findJobDomain(id)).thenReturn(Optional.of(expectedJobResponse));
        createMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.GET, orchestratorApiUrl + id)
                        .content(mapper.writeValueAsString(jobRequest)),
                expectedJson
        );
        verify(domainJobService, atLeastOnce()).findJobDomain(id);
    }

    @Test
    public void testUpdateJobDomain() throws Exception {
        when(domainJobService.updateJobDomain(id, updatedJobRequest)).thenReturn(Optional.of(updatedExpectedJobResponse));
        createMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.POST, orchestratorApiUrl + id)
                        .content(mapper.writeValueAsString(updatedJobRequest)),
                expectedUpdatedJson
        );
        verify(domainJobService, atLeastOnce()).updateJobDomain(eq(id), any(JobRequest.class));
    }

    @Test
    public void testDeleteJobDomain() throws Exception {
        when(domainJobService.deleteJobDomain(id)).thenReturn(Optional.of(expectedJobResponse));
        createMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.DELETE, orchestratorApiUrl + id),
                expectedJson
        );
        verify(domainJobService, atLeastOnce()).deleteJobDomain(id);
    }

    @Test
    public void testFindAllJobsDomains() throws Exception {
        when(domainJobService.findAllJobsDomain()).thenReturn(listOfExpectedJobResponses);
        createMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.GET, orchestratorApiUrl),
                expectedListJson
        );
        verify(domainJobService, atLeastOnce()).findAllJobsDomain();
    }
}

































