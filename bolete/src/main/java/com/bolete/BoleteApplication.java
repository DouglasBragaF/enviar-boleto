package com.bolete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BoleteApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoleteApplication.class, args);
	}

}
