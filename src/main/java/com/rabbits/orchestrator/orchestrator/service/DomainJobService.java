package com.rabbits.orchestrator.orchestrator.service;

import com.rabbits.orchestrator.orchestrator.mapper.JobMapper;
import com.rabbits.orchestrator.orchestrator.model.JobDomain;
import com.rabbits.orchestrator.orchestrator.model.JobRequest;
import com.rabbits.orchestrator.orchestrator.model.JobResponse;
import com.rabbits.orchestrator.orchestrator.repository.DomainJobRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.rabbits.orchestrator.orchestrator.mapper.JobMapper.toJobDomain;
import static com.rabbits.orchestrator.orchestrator.mapper.JobMapper.toJobResponse;
import static java.util.stream.StreamSupport.stream;

@Service
@RequiredArgsConstructor
public class DomainJobService {

    private final DomainJobRepository domainJobRepository;

    public JobResponse addJobDomain(JobRequest jobRequest) {
        JobDomain jobDomain = domainJobRepository.save(toJobDomain(jobRequest));
        return toJobResponse(jobDomain);
    }

    public JobResponse findJobDomain(Long id) {
        return domainJobRepository
                .findById(id)
                .map(JobMapper::toJobResponse)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public JobResponse updateJobDomain(Long id, JobRequest jobRequest) {
        return domainJobRepository
                .findById(id)
                .map(jobDomain -> {
                    jobDomain.setMessage(jobRequest.message());
                    return domainJobRepository.save(jobDomain);
                })
                .map(JobMapper::toJobResponse)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteJobDomain(Long id) {
        domainJobRepository
                .deleteById(id);
    }

    public List<JobResponse> findAllJobsDomain() {
        return stream(domainJobRepository.findAll().spliterator(), false)
                .map(JobMapper::toJobResponse)
                .collect(Collectors.toList());
    }
}
