package com.rabbits.orchestrator.orchestrator.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Непосредственно само задание для выполнения
 */
public record JobRequest(
        @NotNull(message = "Job message cannot be null")
        @NotBlank(message = "Job message cannot be empty")
        String message
) {
}
