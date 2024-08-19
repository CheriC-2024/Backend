package com.art.cheric;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ChericApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChericApplication.class, args);
	}

}
