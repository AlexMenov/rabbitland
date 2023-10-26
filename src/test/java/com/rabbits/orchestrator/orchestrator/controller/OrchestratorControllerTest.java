package com.rabbits.orchestrator.orchestrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbits.orchestrator.orchestrator.exception.JobIdIsNotValidException;
import com.rabbits.orchestrator.orchestrator.exception.JobRequestIllegalException;
import com.rabbits.orchestrator.orchestrator.exception.JobResponseNotFoundException;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.service.DomainJobService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(OrchestratorController.class)
public class OrchestratorControllerTest {
    public static Long id = 10L;

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DomainJobService domainJobService;

    private void doPositiveMockMvcPerform(MockHttpServletRequestBuilder request, HttpStatus status, String expectedJson) throws Exception {
        mockMvc.perform(request
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(status.value()))
                .andExpect(content().json(expectedJson));
    }

    // positive tests

    private void doNegativeMockMvcPerform(MockHttpServletRequestBuilder request, HttpStatus status) throws Exception {
        mockMvc.perform(request
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(status.value()));
    }

    @Test
    public void testAddJobDomainTest() throws Exception {
        when(domainJobService.addJobDomain(new JobRequest("first job")))
                .thenReturn(new JobResponse(10L, "first job"));
        doPositiveMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.POST, "/api/orchestrator/jobs/")
                        .content(mapper.writeValueAsString(new JobRequest("first job"))),
                HttpStatus.CREATED,
                "{\"id\":10,\"message\":\"first job\"}");
        verify(domainJobService, atMostOnce()).addJobDomain(any(JobRequest.class));
    }

    @Test
    public void testGetJobDomain() throws Exception {
        when(domainJobService.findJobDomain(10L))
                .thenReturn(new JobResponse(10L, "first job"));
        doPositiveMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/api/orchestrator/jobs/10")
                        .content(mapper.writeValueAsString(new JobRequest("first job"))),
                HttpStatus.OK,
                "{\"id\":10,\"message\":\"first job\"}");
        verify(domainJobService, atMostOnce()).findJobDomain(10L);
    }

    @Test
    public void testUpdateJobDomain() throws Exception {
        when(domainJobService.updateJobDomain(10L, new JobRequest("updated job")))
                .thenReturn(new JobResponse(10L, "updated job"));
        doPositiveMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.PUT, "/api/orchestrator/jobs/10")
                        .content(mapper.writeValueAsString(new JobRequest("updated job"))),
                HttpStatus.OK,
                "{\"id\":10,\"message\":\"updated job\"}");
        verify(domainJobService, atMostOnce()).updateJobDomain(eq(10L), any(JobRequest.class));
    }

    @Test
    public void testDeleteJobDomain() throws Exception {
//        when(domainJobService.deleteJobDomain(10L))
//                .thenReturn(new JobResponse(10L, "first job"));
        doPositiveMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.DELETE, "/api/orchestrator/jobs/10"),
                HttpStatus.NO_CONTENT,
                "{\"id\":10,\"message\":\"first job\"}");
        verify(domainJobService, atMostOnce()).deleteJobDomain(10L);
    }

    @Test
    public void testFindAllJobsDomains() throws Exception {
        when(domainJobService.findAllJobsDomain())
                .thenReturn(List.of(new JobResponse(10L, "first job")));
        doPositiveMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/api/orchestrator/jobs/"),
                HttpStatus.OK,
                "[{\"id\":10,\"message\":\"first job\"}]");
        verify(domainJobService, atMostOnce()).findAllJobsDomain();
    }

    // negative tests

    @Tag("negative")
    @Nested
    @DisplayName("тестирование негативных сценариев")
    class NegativeTest {
        @Test
        public void whenIdNotCorrect_thenJobIdIsNotValidException() throws Exception {
            doNegativeMockMvcPerform(
                    MockMvcRequestBuilders
                            .request(HttpMethod.GET, "/api/orchestrator/jobs/L10F"),
                    HttpStatus.BAD_REQUEST);
        }
        @Test
        public void whenJobNotExists_thenJobResponseNotFoundException() throws Exception {
            when(domainJobService.findJobDomain(125L))
                    .thenThrow(JobResponseNotFoundException.class);
            doNegativeMockMvcPerform(
                    MockMvcRequestBuilders
                            .request(HttpMethod.GET, "/api/orchestrator/jobs/125"),
                    HttpStatus.NOT_FOUND);
            verify(domainJobService, atMostOnce()).findJobDomain(125L);
        }
        @Test
        public void whenAddJobRequestNotCorrect_thenJobRequestIllegalException() throws Exception {
            doNegativeMockMvcPerform(
                    MockMvcRequestBuilders
                            .request(HttpMethod.POST, "/api/orchestrator/jobs/")
                            .content(mapper.writeValueAsString(new JobRequest(null))),
                    HttpStatus.BAD_REQUEST);
        }
        @Test
        public void whenUpdateJobRequestNotCorrect_thenJobRequestIllegalException() throws Exception {
            when(domainJobService.updateJobDomain(10L, new JobRequest("first job")))
                    .thenThrow(JobRequestIllegalException.class);
            doNegativeMockMvcPerform(
                    MockMvcRequestBuilders
                            .request(HttpMethod.PUT, "/api/orchestrator/jobs/10")
                            .content(mapper.writeValueAsString(new JobRequest("first job"))),
                    HttpStatus.NOT_FOUND);
            verify(domainJobService, atMostOnce()).updateJobDomain(10L, new JobRequest("first job"));
        }
    }
}

































