package com.rabbits.orchestrator.orchestrator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "domain_jobs")
@AllArgsConstructor
final public class JobDomainModel extends JobModel {

    /**
     * return JobResponseModel
     */
    @Override
    public JobModel toJobModel() {
        JobModel jobModel = new JobResponseModel();
        jobModel.setMessage(getMessage());
        return jobModel;
    }

}
