package com.example.UserManagementBoot;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:test.properties")
public class UserManagementBootApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(UserManagementBootApplication.class, args);
	}

}
