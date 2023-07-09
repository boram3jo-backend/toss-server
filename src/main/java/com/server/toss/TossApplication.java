package com.server.toss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TossApplication {

	public static void main(String[] args) {
		SpringApplication.run(TossApplication.class, args);
	}

}
