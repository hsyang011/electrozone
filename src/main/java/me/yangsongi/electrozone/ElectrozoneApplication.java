package me.yangsongi.electrozone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ElectrozoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectrozoneApplication.class, args);
	}

}
