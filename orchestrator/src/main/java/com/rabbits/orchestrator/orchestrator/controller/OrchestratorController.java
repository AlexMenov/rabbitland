package com.rabbits.orchestrator.orchestrator.controller;

import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.service.DomainJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<JobResponse> addJobDomain(@RequestBody JobRequest jobRequest) {
        return domainJobService.addJobDomain(jobRequest)
                .map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<JobResponse> getJobDomain(@PathVariable String id) {
        return domainJobService.findJobDomain(Long.valueOf(id))
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping
//    public ResponseEntity<Iterable<JobModel>> findAllJobs() {
//        return new ResponseEntity<>(domainJobService.findAllJobsDomain(), HttpStatus.FOUND);
//    }
//
//    @PostMapping("/{id}")
//    public JobModel updateJobDomain(@PathVariable String id, JobRequestModel jobRequest) {
//        return domainJobService.updateJobDomain(id, jobRequest);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<JobModel> deleteJobDomain(@PathVariable String id) {
//        return new ResponseEntity<>(domainJobService.deleteJobDomain(id), HttpStatus.NO_CONTENT);
//    }
}

