package com.example.UserManagementBoot.controller;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.UserManagementBoot.services.UserService;

@WebMvcTest
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	
	@MockBean
	private UserService service;

	@Test
	void defaultUrlTestForIndex() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	@Test
	void defaultUrlTestForHome() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}
	
	@Test
	void indexUrlTestForIndex() throws Exception {
		mockMvc.perform(get("/index")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	@Test
	void registerUrlTest() throws Exception {
		mockMvc.perform(get("/registration")).andExpect(status().isOk()).andExpect(view().name("registration"));
	}

	@Test
	void forgotUrlTest() throws Exception {
		mockMvc.perform(get("/forgot")).andExpect(status().isOk()).andExpect(view().name("forgot"));
	}
	
	@Test
	void homeUrlTest() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().is3xxRedirection());
	}
	
	@Test
	void dashboardUrlTest() throws Exception {
		mockMvc.perform(get("/dashboard")).andExpect(status().is3xxRedirection());
	}
}
