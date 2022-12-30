package com.verse.verselogistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"services"})
@ComponentScan(basePackages = {"controllers"})
@ComponentScan(basePackages = {"data.dtos.repositories"})
@ComponentScan(basePackages = {"advice"})
@EnableMongoRepositories(basePackages = {"data.dtos.repositories"})
public class VerseLogisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(VerseLogisticsApplication.class, args);
    }
}
