package com.rabbits.orchestrator.orchestrator.service;

import com.rabbits.orchestrator.orchestrator.model.JobModel;
import com.rabbits.orchestrator.orchestrator.model.JobRequestModel;

public interface DomainJobService {
    JobModel addJobDomain (JobRequestModel jobRequest);
    JobModel findJobDomain (String id);
    JobModel updateJobDomain (String id, JobModel jobRequest);
    JobModel deleteJobDomain (String id);
    Iterable<JobModel> findAllJobsDomain ();
}
