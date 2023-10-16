package com.rabbits.worker.worker.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@Getter
@Configuration
public class ReceiverConfig {

    private final CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

}
