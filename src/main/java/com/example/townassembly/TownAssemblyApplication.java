package com.example.townassembly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TownAssemblyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TownAssemblyApplication.class, args);
    }
}
