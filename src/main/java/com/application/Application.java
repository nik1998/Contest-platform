package com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String text = "1999-01-12T23:23";
        LocalDateTime parsedDate = LocalDateTime.parse(text, formatter);
        String st = formatter.format(parsedDate);
        SpringApplication.run(Application.class, args);
    }
}

