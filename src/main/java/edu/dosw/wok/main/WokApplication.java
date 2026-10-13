package edu.dosw.wok.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

// Misma clase principal del parcial, en el subpaquete main
@SpringBootApplication(scanBasePackages = "edu.dosw.wok")
@EnableMongoRepositories(basePackages = "edu.dosw.wok.persistence.repositories")
public class WokApplication {

	public static void main(String[] args) {
		SpringApplication.run(WokApplication.class, args);
	}

}
