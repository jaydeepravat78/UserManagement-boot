package com.example.UserManagementBoot.dao;

import java.util.List;

public interface UserDao<T> {
	void addUser(T user);
	
	T getUser(Object email, Object password);
	
	List<T> getAllUsers();
	
	T getUserData(Object id);
	
	Object deleteUser(Object id);
	
	void updateUser(T user);
	
	void deleteAddress(Object address);
	
	boolean checkEmail(Object email);
	
	boolean updatePassword(T user);
}
