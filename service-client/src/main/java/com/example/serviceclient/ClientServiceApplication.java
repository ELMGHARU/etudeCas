package com.example.serviceclient;

import com.example.serviceclient.model.Client;
import com.example.serviceclient.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner initializeDatabase(ClientRepository clientRepository) {
        return args -> {
            clientRepository.save(new Client(null, "YOUNESS KASSIDE", 30));
            clientRepository.save(new Client(null, "HIBA ARBEL", 28));
        };
    }
}