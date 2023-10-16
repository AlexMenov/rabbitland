package com.rabbits.orchestrator.orchestrator.controller;

import com.rabbits.orchestrator.orchestrator.model.JobDomainModel;
import com.rabbits.orchestrator.orchestrator.model.JobModel;
import com.rabbits.orchestrator.orchestrator.model.JobRequestModel;
import com.rabbits.orchestrator.orchestrator.service.DomainJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(
        path = "/api/orchestrator/jobs",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class OrchestratorController {

    private final DomainJobService domainJobService;

    @PutMapping
    public ResponseEntity<JobModel> addJobDomain(JobRequestModel jobRequest) {
        return new ResponseEntity<>(domainJobService.addJobDomain(jobRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = {"/{id}"})
    public JobModel getJobDomain(@PathVariable String id) {
        return domainJobService.findJobDomain(id);
    }

    @GetMapping
    public Iterable<JobModel> findAllJobs() {
        JobModel jobModel = new JobDomainModel();
        jobModel.setMessage("hello");
        jobModel.setId(10L);
        return Collections.singleton(jobModel);
    }

    @PostMapping("/{id}")
    public JobModel updateJobDomain(@PathVariable String id, JobRequestModel jobRequest) {
        return domainJobService.updateJobDomain(id, jobRequest);
    }

    @DeleteMapping("/{id}")
    public JobModel deleteJobDomain(@PathVariable String id) {
        return domainJobService.deleteJobDomain(id);
    }
}

