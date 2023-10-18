package com.rabbits.orchestrator.orchestrator.service;

import com.rabbits.orchestrator.orchestrator.mapper.JobMapper;
import com.rabbits.orchestrator.orchestrator.model.JobDomain;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.repository.DomainJobRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DomainJobService {

    private final DomainJobRepository domainJobRepository;

    public Optional<JobResponse> addJobDomain(JobRequest jobRequest) {
        JobDomain jobDomain = JobMapper.toJobDomain(jobRequest);
        jobDomain = domainJobRepository.save(jobDomain);
        return Optional.of(JobMapper.toJobResponse(jobDomain));
    }

    public Optional<JobResponse> findJobDomain(Long id) {
        return domainJobRepository
                .findById(id)
                .map(JobMapper::toJobResponse);
    }

    @Transactional
    public JobResponse updateJobDomain(Long id, JobRequest jobRequest) {
//        Optional<JobDomain> domainJob = domainJobRepository.findById(id);
//        domainJob.ifPresent(jobDomain -> jobDomain.setMessage(jobRequest.message()));
//        domainJob = domainJobRepository.save(domainJob.get());
        return null;
    }

    @Transactional
    public Optional<JobResponse> deleteJobDomain(String id) { // Optional
//        JobModel domainJob = domainJobRepository
//                .findById(Long.valueOf(id))
//                .orElseThrow();
//        domainJobRepository.deleteById(domainJob.getId());
        return null;
    }

    public Iterable<JobResponse> findAllJobsDomain() {
        return StreamSupport
                .stream(domainJobRepository.findAll().spliterator(), false)
                .map(JobMapper::toJobResponse)
                .collect(Collectors.toList());
    }
}
