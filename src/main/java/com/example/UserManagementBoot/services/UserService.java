package com.example.UserManagementBoot.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.UserManagementBoot.models.User;


public interface UserService extends UserDetailsService{
	void addUser(User user);
	
	User getUser(String email, String password);
	
	User getUserData(int id);
	
	List<User> getAllUser();
	
	boolean deleteUser(int id);
	
	String addAllUser(List<User> users);
	
	void updateUser(User user);
	
	boolean checkEmail(String email);
	
	boolean updatePassword(User user);
}
