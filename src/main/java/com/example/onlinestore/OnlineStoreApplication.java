package com.example.onlinestore;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition
public class OnlineStoreApplication {

    public static void main(String[] args) {
//        SpringApplication app = new SpringApplication(OnlineStoreApplication.class);
//        app.setDefaultProperties(Collections.singletonMap("server.port", "3000"));
        SpringApplication.run(OnlineStoreApplication.class, args);
//        app.run(args);
    }

}
