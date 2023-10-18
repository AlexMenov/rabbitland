package com.rabbits.orchestrator.orchestrator.repository;

import com.rabbits.orchestrator.orchestrator.model.JobDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainJobRepository extends CrudRepository<JobDomain, Long> {
}
