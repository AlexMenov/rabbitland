package com.rabbits.orchestrator.orchestrator.model;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@MappedSuperclass
@EqualsAndHashCode
@ToString
sealed abstract public class JobModel implements Job permits
        JobResponseModel,
        JobRequestModel,
        JobDomainModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "messages")
    private String message;

    final public Long getId() {
        return id;
    }

    final public void setId(Long id) {
        this.id = id;
    }

    final public String getMessage() {
        return message;
    }

    final public void setMessage(String message) {
        this.message = message;
    }
}
