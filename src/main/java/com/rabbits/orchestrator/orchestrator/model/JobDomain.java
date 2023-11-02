package com.rabbits.orchestrator.orchestrator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Представление задания (сообщения) для обмена между микросервисами
 */
@Entity
@Table(name = "domain_jobs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class JobDomain {
    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Сообщение для выполнения
     */
    @Column
    private String message;

    public JobDomain(String message) {
        this.message = message;
    }
}

