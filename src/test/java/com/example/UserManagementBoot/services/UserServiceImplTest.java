package com.example.UserManagementBoot.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.UserManagementBoot.jpadao.AddressDao;
import com.example.UserManagementBoot.jpadao.UserDao;
import com.example.UserManagementBoot.models.Address;
import com.example.UserManagementBoot.models.User;
import com.example.UserManagementBoot.utility.KeyGeneration;
import com.example.UserManagementBoot.utility.Validation;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl service;

	@Mock
	private UserDao userDao;

	@Mock
	private AddressDao addressDao;

	@Mock
	private KeyGeneration key;

	@Mock
	private Validation validation;

	@BeforeAll
	void setUp() {
		service = new UserServiceImpl();
	}

	@Test
	void userAddTest() {
		User user = new User();
		user.setName("Jay");
		user.setEmail("jay@gmail.com");
		List<Address> address = new ArrayList<>();
		Address add1 = new Address();
		address.add(add1);
		user.setAddresses(address);
		User userExpected = new User();
		userExpected.setName("Jay");
		userExpected.setEmail("jay@gmail.com");
		userExpected.setAddresses(address);
		when(userDao.save(user)).thenReturn(userExpected);
		service.addUser(user);
		verify(userDao, atLeastOnce()).save(any());
	}

	@Test
	void getUserTest() {
		String email = "jay@gmail.com";
		String password = "jay@1234";
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		when(userDao.findDistinctByEmail(email)).thenReturn(user);
		when(KeyGeneration.decrypt(user.getPassword())).thenReturn(password);
		User userData = service.getUser(email, password);
		assertNotNull(userData);
		verify(userDao, atLeastOnce()).findDistinctByEmail(email);
	}

	@Test
	void deleteUserTest() {
		int id = 1;
		doNothing().when(userDao).deleteById(id);
		boolean result = service.deleteUser(id);
		assertTrue(result);
		verify(userDao, atLeastOnce()).deleteById(anyInt());
	}

	@Test
	void getUserDataTest() {
		int id = 1;
		User expectedUser = new User();
		expectedUser.setName("jay");
		when(userDao.getById(id)).thenReturn(expectedUser);
		User user = service.getUserData(id);
		assertNotNull(user);
		verify(userDao, atLeastOnce()).getById(anyInt());
	}

	@Test
	void getAllUserTest() {
		User user = new User();
		user.setName("jay");
		List<User> expectedUsers = new ArrayList<>();
		expectedUsers.add(user);
		when(userDao.findByIsAdminFalse()).thenReturn(expectedUsers);
		List<User> users = service.getAllUser();
		assertEquals(expectedUsers, users);
		verify(userDao, atLeastOnce()).findByIsAdminFalse();
	}

	@Test
	void checkEmailTestForValidEmail() {
		String email = "jay@gmail.com";
		User user = new User();
		user.setName(email);
		when(userDao.findDistinctByEmail(email)).thenReturn(user);
		boolean result = service.checkEmail(email);
		assertFalse(result);
		verify(userDao, atLeastOnce()).findDistinctByEmail(anyString());
	}

	@Test
	void checkEmailTestForInValidEmail() {
		String email = "jay@gmail.com";
		when(userDao.findDistinctByEmail(email)).thenReturn(null);
		boolean result = service.checkEmail(email);
		assertTrue(result);
		verify(userDao, atLeastOnce()).findDistinctByEmail(anyString());
	}
}
