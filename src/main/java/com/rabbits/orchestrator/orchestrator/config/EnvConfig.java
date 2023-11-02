package com.rabbits.orchestrator.orchestrator.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

public class EnvConfig {
    public static final Dotenv dotenv;

    static {
        dotenv = Dotenv.configure()
                .directory("assets")
                .filename("env")
                .load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
    }
}
