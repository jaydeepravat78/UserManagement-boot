package com.example.UserManagementBoot;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.log4j.BasicConfigurator;

import com.example.UserManagementBoot.controller.UserController;


@SpringBootTest
class UserManagementBootApplicationTests {

	@Autowired
	private UserController controller;
	
	@Test
	void contextLoads() {
		BasicConfigurator.configure();
		assertThat(controller).isNotNull();
	}

}
