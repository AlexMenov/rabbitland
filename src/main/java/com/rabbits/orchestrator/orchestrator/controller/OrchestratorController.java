package com.rabbits.orchestrator.orchestrator.controller;

import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.service.DomainJobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(
        path = "/api/orchestrator/jobs/",
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class OrchestratorController {

    private final DomainJobService domainJobService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<JobResponse> addJobDomain(@Valid @RequestBody JobRequest jobRequest) {
        return status(CREATED).body(domainJobService.addJobDomain(jobRequest));
    }

    @GetMapping(path = {"{id}"})
    public ResponseEntity<JobResponse> findJobDomain(@PathVariable long id) {
        return status(OK).body(domainJobService.findJobDomain(id));
    }

    @PutMapping(value = "{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<JobResponse> updateJobDomain(@PathVariable long id, @Valid @RequestBody JobRequest jobRequest) {
        return status(OK).body(domainJobService.updateJobDomain(id, jobRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteJobDomain(@PathVariable long id) {
        domainJobService.deleteJobDomain(id);
        return noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> findAllJobDomains() {
        return status(OK).body(domainJobService.findAllJobsDomain());
    }
}

