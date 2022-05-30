package com.example.UserManagementBoot.jpadao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserManagementBoot.models.User;

public interface UserDao extends JpaRepository<User, Integer> {
	
	User findDistinctByEmail(String email);
	
	List<User> findByRole(String role);
	
}
