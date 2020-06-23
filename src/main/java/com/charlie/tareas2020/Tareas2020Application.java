package com.charlie.tareas2020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//To discard the security auto-configuration and add our own configuration, we need to exclude the SecurityAutoConfiguration class.
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class Tareas2020Application {

	public static void main(String[] args) {
		SpringApplication.run(Tareas2020Application.class, args);
	}

}
