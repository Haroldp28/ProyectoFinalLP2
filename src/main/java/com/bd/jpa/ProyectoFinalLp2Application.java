package com.bd.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.bd.java.repositorio")
@EntityScan(basePackages = "com.bd.jpa.modelo")
@SpringBootApplication
public class ProyectoFinalLp2Application {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFinalLp2Application.class, args);
	}

}
