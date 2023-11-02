package com.rabbits.orchestrator.orchestrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbits.orchestrator.orchestrator.exception.JobRequestIllegalException;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.service.DomainJobService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(OrchestratorController.class)
class OrchestratorControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DomainJobService domainJobService;

    private void doMockMvcPerform(MockHttpServletRequestBuilder request, HttpStatus status, String expectedJson) throws Exception {
        ResultActions resultActions = mockMvc.perform(request
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().is(status.value()));

        if (expectedJson != null) {
            resultActions.andExpect(content().json(expectedJson));
        }
    }


    @Test
    void testAddJobDomainTest() throws Exception {
        when(domainJobService.addJobDomain(new JobRequest("first job")))
                .thenReturn(new JobResponse(10L, "first job"));
        doMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.POST, "/api/orchestrator/jobs/")
                        .content(mapper.writeValueAsString(new JobRequest("first job"))),
                HttpStatus.CREATED,
                "{\"id\":10,\"message\":\"first job\"}");
        verify(domainJobService, atMostOnce()).addJobDomain(any(JobRequest.class));
    }

    @Test
    void testGetJobDomain() throws Exception {
        when(domainJobService.findJobDomain(10L))
                .thenReturn(new JobResponse(10L, "first job"));
        doMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/api/orchestrator/jobs/10")
                        .content(mapper.writeValueAsString(new JobRequest("first job"))),
                HttpStatus.OK,
                "{\"id\":10,\"message\":\"first job\"}");
        verify(domainJobService, atMostOnce()).findJobDomain(10L);
    }

    @Test
    void testUpdateJobDomain() throws Exception {
        when(domainJobService.updateJobDomain(10L, new JobRequest("updated job")))
                .thenReturn(new JobResponse(10L, "updated job"));
        doMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.PUT, "/api/orchestrator/jobs/10")
                        .content(mapper.writeValueAsString(new JobRequest("updated job"))),
                HttpStatus.OK,
                "{\"id\":10,\"message\":\"updated job\"}");
        verify(domainJobService, atMostOnce()).updateJobDomain(eq(10L), any(JobRequest.class));
    }

    @Test
    void testDeleteJobDomain() throws Exception {
        doNothing().when(domainJobService).deleteJobDomain(10L);

        doMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.DELETE, "/api/orchestrator/jobs/10"),
                HttpStatus.NO_CONTENT, null);

        verify(domainJobService, times(1)).deleteJobDomain(10L);
    }

    @Test
    void testFindAllJobsDomains() throws Exception {
        when(domainJobService.findAllJobsDomain())
                .thenReturn(List.of(new JobResponse(10L, "first job")));
        doMockMvcPerform(
                MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/api/orchestrator/jobs/"),
                HttpStatus.OK,
                "[{\"id\":10,\"message\":\"first job\"}]");
        verify(domainJobService, atMostOnce()).findAllJobsDomain();
    }

    @Tag("negative")
    @Nested
    @DisplayName("тестирование негативных сценариев")
    class NegativeTest {
        @Test
        void whenIdNotCorrect_thenJobIdIsNotValidException() throws Exception {
            doMockMvcPerform(
                    MockMvcRequestBuilders
                            .request(HttpMethod.GET, "/api/orchestrator/jobs/L10F"),
                    HttpStatus.BAD_REQUEST, null);
        }

        @Test
        void whenJobNotExists_thenJobResponseNotFoundException() throws Exception {
            when(domainJobService.findJobDomain(125L))
                    .thenThrow(EntityNotFoundException.class);
            doMockMvcPerform(
                    MockMvcRequestBuilders
                            .request(HttpMethod.GET, "/api/orchestrator/jobs/125"),
                    HttpStatus.NOT_FOUND, null);
        }

        @Test
        void whenAddJobRequestNotCorrect_thenJobRequestIllegalException() throws Exception {
            when(domainJobService.addJobDomain(new JobRequest("first job")))
                    .thenThrow(JobRequestIllegalException.class);
            doMockMvcPerform(
                    MockMvcRequestBuilders
                            .request(HttpMethod.POST, "/api/orchestrator/jobs/")
                            .content(mapper.writeValueAsString(new JobRequest(null))),
                    HttpStatus.BAD_REQUEST, null);
        }

        @Test
        void whenUpdateJobRequestNotCorrect_thenJobRequestIllegalException() throws Exception {
            when(domainJobService.updateJobDomain(10L, new JobRequest("first job")))
                    .thenThrow(EntityNotFoundException.class);
            doMockMvcPerform(
                    MockMvcRequestBuilders
                            .request(HttpMethod.PUT, "/api/orchestrator/jobs/10")
                            .content(mapper.writeValueAsString(new JobRequest("first job"))),
                    HttpStatus.NOT_FOUND, null);
        }
    }
}

































