package ru.meklaw.autodrome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class AutodromeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutodromeApplication.class, args);
	}

}
