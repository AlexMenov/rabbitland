package com.rabbits.orchestrator.orchestrator.controller;

import com.rabbits.orchestrator.orchestrator.exception.JobIdIsNotValidException;
import com.rabbits.orchestrator.orchestrator.exception.JobResponseNotFoundException;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.service.DomainJobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(
        path = "/api/orchestrator/jobs/",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class OrchestratorController {

    private final DomainJobService domainJobService;

    private static Long parseIdIfCorrect(String id) {
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new JobIdIsNotValidException();
        }
    }

    private static <T> ResponseEntity<T> createResponse(Optional<T> response) {
        return response
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseThrow(JobResponseNotFoundException::new);
    }

    @PutMapping
    public ResponseEntity<JobResponse> addJobDomain(@Valid @RequestBody JobRequest jobRequest) {
        return createResponse(domainJobService.addJobDomain(jobRequest));
    }

    @GetMapping(path = {"{id}"})
    public ResponseEntity<JobResponse> getJobDomain(@PathVariable String id) {
        return createResponse(domainJobService.findJobDomain(parseIdIfCorrect(id)));
    }

    @PostMapping("{id}")
    public ResponseEntity<JobResponse> updateJobDomain(@PathVariable String id, @Valid @RequestBody JobRequest jobRequest) {
        return createResponse(domainJobService.updateJobDomain(parseIdIfCorrect(id), jobRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<JobResponse> deleteJobDomain(@PathVariable String id) {
        return createResponse(domainJobService.deleteJobDomain(parseIdIfCorrect(id)));
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> findAllJobsDomains() {
        return createResponse(Optional.of(domainJobService.findAllJobsDomain()));
    }
}

