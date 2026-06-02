package com.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableCaching
public class EmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
		System.out.println("Employee Service Running...!");
	}

}
