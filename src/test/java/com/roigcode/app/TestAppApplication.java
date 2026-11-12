package com.roigcode.app;

import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

public class TestAppApplication {

    public static void main(String[] args) {
        SpringApplication.from(AppApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }
}
