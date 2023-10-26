package com.rabbits.orchestrator.orchestrator.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

public class FlywayConfig {
    public Flyway flyway() {
        return Flyway
                .configure()
                .dataSource(
                        EnvConfig.dotenv.get("spring.datasource.url"),
                        EnvConfig.dotenv.get("spring.datasource.username"),
                        EnvConfig.dotenv.get("spring.datasource.password")
                )
                .load();
    }
}

