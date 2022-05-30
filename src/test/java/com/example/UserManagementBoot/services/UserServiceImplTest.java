package com.example.UserManagementBoot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
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

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl service;

	@Mock
	private UserDao userDao;

	@Mock
	private AddressDao addressDao;

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
	void getUserTestWhenPasswordMatches() {
		String email = "jay@gmail.com";
		String password = "jay@1234";
		User user = new User();
		user.setEmail(email);
		user.setPassword(KeyGeneration.encrypt(password));
		when(userDao.findDistinctByEmail(email)).thenReturn(user);
		User userData = service.getUser(email, password);
		assertNotNull(userData);
		verify(userDao, atLeastOnce()).findDistinctByEmail(email);
	}

	@Test
	void getUserTestWhenPasswordDoesNotMatches() {
		String email = "jay@gmail.com";
		String password = "jay@1234";
		User user = new User();
		user.setEmail(email);
		user.setPassword(KeyGeneration.encrypt("jay"));
		when(userDao.findDistinctByEmail(email)).thenReturn(user);
		User userData = service.getUser(email, password);
		assertNull(userData);
		verify(userDao, atLeastOnce()).findDistinctByEmail(email);
	}

	@Test
	void getUserTestWhenEmailDoesNotExists() {
		String email = "jay@gmail.com";
		String password = "jay@1234";
		when(userDao.findDistinctByEmail(email)).thenReturn(null);
		User userData = service.getUser(email, password);
		assertNull(userData);
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
		when(userDao.findByRole("USER")).thenReturn(expectedUsers);
		List<User> users = service.getAllUser();
		assertEquals(expectedUsers, users);
		verify(userDao, atLeastOnce()).findByRole(anyString());
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

	@Test
	void updateUserTest() {
		User user = new User();
		user.setId(1);
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		Address add1 = new Address();
		add1.setAddress_id(1);
		add1.setCity("city");
		Address add2 = new Address();
		add2.setCity("city 2");
		List<Address> newAddresses = new ArrayList<>();
		newAddresses.add(add1);
		newAddresses.add(add2);
		user.setAddresses(newAddresses);
		User expcetedUser = new User();
		expcetedUser.setEmail("jay@gmail.com");
		expcetedUser.setPassword("jay@1234");
		Address add3 = new Address();
		add3.setAddress_id(3);
		Address add4 = new Address();
		add4.setAddress_id(1);
		add4.setCity("city");
		List<Address> oldAddresses = new ArrayList<>();
		oldAddresses.add(add3);
		oldAddresses.add(add4);
		expcetedUser.setAddresses(oldAddresses);
		when(userDao.getById(1)).thenReturn(expcetedUser);
		when(userDao.save(user)).thenReturn(new User());
		doNothing().when(addressDao).deleteAllById(anyList());
		service.updateUser(user);
		verify(userDao, atLeastOnce()).save(any());
		verify(userDao, atLeastOnce()).getById(anyInt());
	}

	@Test
	void updatePasswordTest() {
		User user = new User();
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setSecQues("School");
		User expectedUser = new User();
		expectedUser.setEmail("jay@gmail.com");
		expectedUser.setGame("GTA");
		expectedUser.setSecQues("School");
		when(userDao.findDistinctByEmail(user.getEmail())).thenReturn(expectedUser);
		when(userDao.save(expectedUser)).thenReturn(new User());
		boolean res = service.updatePassword(user);
		assertTrue(res);
		verify(userDao, atLeastOnce()).findDistinctByEmail(anyString());
		verify(userDao, atLeastOnce()).save(any());
	}

	@Test
	void updatePasswordTestWhenInvalidEmail() {
		User user = new User();
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setSecQues("School");
		when(userDao.findDistinctByEmail(user.getEmail())).thenReturn(null);
		boolean res = service.updatePassword(user);
		assertFalse(res);
		verify(userDao, atLeastOnce()).findDistinctByEmail(anyString());
	}

	@Test
	void updatePasswordTestWhenInvalidGame() {
		User user = new User();
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setSecQues("School");
		User expectedUser = new User();
		expectedUser.setEmail("jay@gmail.com");
		expectedUser.setGame("Fifa");
		expectedUser.setSecQues("School");
		when(userDao.findDistinctByEmail(user.getEmail())).thenReturn(expectedUser);
		boolean res = service.updatePassword(user);
		assertFalse(res);
		verify(userDao, atLeastOnce()).findDistinctByEmail(anyString());
	}

	@Test
	void updatePassowrdTestWhenInvalidSecQues() {
		User user = new User();
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setSecQues("School");
		User expectedUser = new User();
		expectedUser.setEmail("jay@gmail.com");
		expectedUser.setGame("GTA");
		expectedUser.setSecQues("My School");
		when(userDao.findDistinctByEmail(user.getEmail())).thenReturn(expectedUser);
		boolean res = service.updatePassword(user);
		assertFalse(res);
		verify(userDao, atLeastOnce()).findDistinctByEmail(anyString());
	}

	@Test
	void addAllUserTestWithoutError() {
		User user = new User();
		user.setName("jay");
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setPhone("7878787878");
		user.setGender("male");
		String[] lang = {"Java"};
		user.setLang(lang);
		user.setGame("GTA");
		user.setSecQues("School");
		List<User> users = new ArrayList<>();
		users.add(user);
		when(userDao.save(user)).thenReturn(new User());
		String error = service.addAllUser(users);
		assertEquals("", error);
		verify(userDao, atLeastOnce()).save(any());
	}
	
	@Test
	void addAllUserTestWithErro() {
		User user = new User();
		user.setName("jay1");
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setPhone("7878787878");
		user.setGender("male");
		String[] lang = {"Java"};
		user.setLang(lang);
		user.setGame("GTA");
		user.setSecQues("School");
		List<User> users = new ArrayList<>();
		users.add(user);
		String error = service.addAllUser(users);
		assertEquals("At row 1 *Name should only contains alphabet\n", error);
	}
}
