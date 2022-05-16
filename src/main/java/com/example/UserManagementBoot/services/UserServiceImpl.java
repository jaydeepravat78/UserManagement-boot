package com.example.UserManagementBoot.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.UserManagementBoot.jpadao.AddressDao;
import com.example.UserManagementBoot.jpadao.UserDao;
import com.example.UserManagementBoot.models.Address;
import com.example.UserManagementBoot.models.User;
import com.example.UserManagementBoot.utility.KeyGeneration;
import com.example.UserManagementBoot.utility.Validation;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	@Autowired
	private AddressDao addressDao;

	@Override
	public void addUser(User user) {
		for (Address address : user.getAddresses()) {
			address.setUser(user);
		}
		dao.save(user);
	}

	@Override
	public User getUser(String email, String password) {
		List<User> userData = dao.findDistinctByEmail(email);
		if (!userData.isEmpty()) {
			User user = userData.get(0);
			if (password.equals(KeyGeneration.decrypt(user.getPassword())))
				return user;
			else
				return null;
		} else {
			return null;
		}
	}

	@Override
	public List<User> getAllUser() {
		return dao.findByIsAdminFalse();
	}

	@Override
	public User getUserData(int id) {
		User user = dao.findById(id).get();
		user.setPassword(KeyGeneration.decrypt(user.getPassword()));
		return user;
	}

	@Override
	public boolean deleteUser(int id) {
		dao.deleteById(id);
		return true;
	}

	@Override
	public String addAllUser(List<User> users) {
		String error = "";
		int count = 1;
		for (User user : users) {
			String inputError = "";
			inputError += Validation.checkName(user.getName());
			inputError += Validation.checkEmail(user.getEmail());
			inputError += Validation.checkPassword(user.getPassword());
			inputError += Validation.checkPhone(user.getPhone());
			inputError += Validation.checkGender(user.getGender());
			inputError += Validation.checkLang(user.getLang());
			inputError += Validation.checkGame(user.getGame());
			inputError += Validation.checkSecQues(user.getSecQues());
			if (inputError.isEmpty()) {
				user.setPassword(KeyGeneration.encrypt(user.getPassword()));
				dao.save(user);
			} else {
				error += "At row " + count + " " + inputError;
			}
			count++;
		}
		return error;
	}

	@Override
	public void updateUser(User user) {
		for (Address address : user.getAddresses()) {
			address.setUser(user);
		}
		List<Integer> address_id = new ArrayList<>();
		List<Address> oldAddresses = getUserData(user.getId()).getAddresses();
		for (Address oldAddress : oldAddresses) {
			Address newAddress = user.getAddresses().stream()
					.filter(address -> address.getAddress_id() == oldAddress.getAddress_id()).findAny().orElse(null);
			if (newAddress == null) {
				address_id.add(oldAddress.getAddress_id());
			}
		}
		dao.save(user);
		addressDao.deleteAllById(address_id);
	}

	@Override
	public boolean checkEmail(String email) {
		return dao.findDistinctByEmail(email).isEmpty();
	}

	@Override
	public boolean updatePassword(User user) {
		User userData = dao.findDistinctByEmail(user.getEmail()).get(0);
		if (userData != null && user.getSecQues().equals(userData.getSecQues())
				&& user.getGame().equals(userData.getGame())) {
			userData.setPassword(KeyGeneration.encrypt(user.getPassword()));
			dao.save(userData);
			return true;
		}
		return false;
	}
}
