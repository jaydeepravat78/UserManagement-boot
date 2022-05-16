package com.example.UserManagementBoot.services;

import java.util.List;

import com.example.UserManagementBoot.models.User;


public interface UserService {
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
