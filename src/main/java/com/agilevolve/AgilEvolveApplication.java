package com.agilevolve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AgilEvolveApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgilEvolveApplication.class, args);
	}

}
