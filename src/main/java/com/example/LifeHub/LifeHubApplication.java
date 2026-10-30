package com.example.LifeHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EnableWebSocketMessageBroker
@EnableJpaAuditing
public class LifeHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifeHubApplication.class, args);
	}

}
