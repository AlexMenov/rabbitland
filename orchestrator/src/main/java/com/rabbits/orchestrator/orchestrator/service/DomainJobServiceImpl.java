package com.rabbits.orchestrator.orchestrator.service;

import com.rabbits.orchestrator.orchestrator.model.Job;
import com.rabbits.orchestrator.orchestrator.model.JobModel;
import com.rabbits.orchestrator.orchestrator.model.JobRequestModel;
import com.rabbits.orchestrator.orchestrator.repository.DomainJobRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DomainJobServiceImpl implements DomainJobService {

    private final DomainJobRepository domainJobRepository;

    @Override
    public JobModel addJobDomain(JobRequestModel jobRequest) {
        return domainJobRepository.save(jobRequest.toJobModel());
    }

    @Override
    public JobModel findJobDomain(String id) {
        return domainJobRepository
                .findById(Long.valueOf(id))
                .orElseThrow()
                .toJobModel();
    }

    @Transactional
    @Override
    public JobModel updateJobDomain(String id, JobModel jobRequest) {
        JobModel domainJob = domainJobRepository
                .findById(Long.valueOf(id))
                .orElseThrow();
        domainJob.setMessage(jobRequest.getMessage());
        return domainJobRepository.save(domainJob);
    }

    @Transactional
    @Override
    public JobModel deleteJobDomain(String id) {
        JobModel domainJob = domainJobRepository
                .findById(Long.valueOf(id))
                .orElseThrow();
        domainJobRepository.deleteById(domainJob.getId());
        return domainJob;
    }

    @Override
    public Iterable<JobModel> findAllJobsDomain() {
        return StreamSupport
                .stream(domainJobRepository.findAll().spliterator(), false)
                .map(Job::toJobModel)
                .collect(Collectors.toList());
    }
}
