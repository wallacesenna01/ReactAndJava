package com.wallace.artur.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FullstackProJect2Application {

	public static void main(String[] args) {
		SpringApplication.run(FullstackProJect2Application.class, args);
	}

}
