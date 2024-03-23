package com.narrowware.vending;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// We want automation in terms of save/update dates
@EnableJpaAuditing
public class VendingApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendingApplication.class, args);
	}

}
