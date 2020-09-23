package com.gmail.dimaliahov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class DimaliahovApplication {

	public static void main (String[] args) {
		SpringApplication.run(DimaliahovApplication.class, args);
	}

}
