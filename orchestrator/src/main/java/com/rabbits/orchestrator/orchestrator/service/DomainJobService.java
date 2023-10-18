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
    public Optional<JobResponse> updateJobDomain(Long id, JobRequest jobRequest) {
        Optional<JobDomain> domainJob = domainJobRepository.findById(id);
        domainJob.ifPresent(jobDomain -> jobDomain.setMessage(jobRequest.message()));
        return Optional.of(
                JobMapper.toJobResponse(
                        domainJobRepository.save(domainJob.orElseThrow()
                        )
                )
        );
    }

    @Transactional
    public Optional<JobResponse> deleteJobDomain(String id) {
        JobDomain domainJob = domainJobRepository
                .findById(Long.valueOf(id))
                .orElseThrow();
        domainJobRepository.deleteById(domainJob.getId());
        return Optional.of(JobMapper.toJobResponse(domainJob));
    }

    public Iterable<Optional<JobResponse>> findAllJobsDomain() {
        return StreamSupport
                .stream(domainJobRepository.findAll().spliterator(), false)
                .map(job -> Optional.of(JobMapper.toJobResponse(job)))
                .collect(Collectors.toList());
    }
}
