package com.rabbits.orchestrator.orchestrator.model;

final public class JobRequestModel extends JobModel {

    /**
     * return JobDomainModel
     */
    @Override
    public JobModel toJobModel() {
        JobModel domainJob = new JobDomainModel();
        domainJob.setMessage(getMessage());
        return domainJob;
    }

}
