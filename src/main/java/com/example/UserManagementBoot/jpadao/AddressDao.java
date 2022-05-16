package com.example.UserManagementBoot.jpadao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserManagementBoot.models.Address;

public interface AddressDao extends JpaRepository<Address, Integer>{

}
