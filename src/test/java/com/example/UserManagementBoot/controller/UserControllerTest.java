package com.example.UserManagementBoot.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.example.UserManagementBoot.models.Address;
import com.example.UserManagementBoot.models.User;
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
		mockMvc.perform(get("/").sessionAttr("userSession", new User())).andExpect(redirectedUrl("home"));
	}

	@Test
	void defaultUrlTestForDashboard() throws Exception {
		mockMvc.perform(get("/").sessionAttr("admin", new User())).andExpect(redirectedUrl("dashboard"));
	}

	@Test
	void indexUrlTestForIndex() throws Exception {
		mockMvc.perform(get("/index")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	@Test
	void indexUrlTestforHome() throws Exception {
		mockMvc.perform(get("/index").sessionAttr("userSession", new User())).andExpect(redirectedUrl("home"));
	}

	@Test
	void indexUrlTestforDashboard() throws Exception {
		mockMvc.perform(get("/index").sessionAttr("admin", new User())).andExpect(redirectedUrl("dashboard"));
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
	void homeUrlTestForIndex() throws Exception {
		mockMvc.perform(get("/home")).andExpect(redirectedUrl("index"));
	}

	@Test
	void homeUrlTest() throws Exception {
		User user = new User();
		user.setId(1);
		user.setName("jay");
		user.setEmail("jay@gmail.com");
		user.setPhone("9876543211");
		user.setGender("male");
		user.setGame("GTA");
		mockMvc.perform(get("/home").sessionAttr("userSession", user)).andExpect(status().isOk());
	}

	@Test
	void dashboardUrlTestForIndex() throws Exception {
		mockMvc.perform(get("/dashboard")).andExpect(redirectedUrl("index"));
	}

	@Test
	void dashboardUrlTest() throws Exception {
		mockMvc.perform(get("/dashboard").sessionAttr("admin", new User())).andExpect(status().isOk());
	}

	@Test
	void registrationTestForUser() throws Exception {
		FileInputStream file = new FileInputStream(
				"C:\\Users\\usa\\OneDrive\\Pictures\\Saved Pictures\\45300ad3107192f2a92bb76a66b2d9de.jpg");
		MockMultipartFile user_photo = new MockMultipartFile("user_photo", file);
		User user = new User();
		user.setName("jay");
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setGender("male");
		user.setPhone("9876543211");
		user.setSecQues("School");
		List<Address> addresses = new ArrayList<>();
		Address add = new Address();
		add.setStreet("abc");
		add.setState("abc");
		add.setCity("abc");
		addresses.add(add);
		user.setAddresses(addresses);
		String[] lang = { "Java" };
		user.setLang(lang);
		mockMvc.perform(
				multipart("/registerController").file(user_photo).contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
						.accept(MediaType.MULTIPART_FORM_DATA_VALUE).flashAttr("user", user).param("isadmin", "false"))
				.andExpect(redirectedUrl("index"));
	}

	@Test
	void registrationTestForAdmin() throws Exception {
		FileInputStream file = new FileInputStream(
				"C:\\Users\\usa\\OneDrive\\Pictures\\Saved Pictures\\45300ad3107192f2a92bb76a66b2d9de.jpg");
		MockMultipartFile user_photo = new MockMultipartFile("user_photo", file);
		User user = new User();
		user.setName("jay");
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setGender("male");
		user.setPhone("9876543211");
		user.setSecQues("School");
		List<Address> addresses = new ArrayList<>();
		Address add = new Address();
		add.setStreet("abc");
		add.setState("abc");
		add.setCity("abc");
		addresses.add(add);
		user.setAddresses(addresses);
		String[] lang = { "Java" };
		user.setLang(lang);
		mockMvc.perform(
				multipart("/registerController").file(user_photo).contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
						.accept(MediaType.MULTIPART_FORM_DATA_VALUE).flashAttr("user", user).param("isadmin", "true"))
				.andExpect(redirectedUrl("index"));
	}

	@Test
	void registrationTestForRedirectAdmin() throws Exception {
		FileInputStream file = new FileInputStream(
				"C:\\Users\\usa\\OneDrive\\Pictures\\Saved Pictures\\45300ad3107192f2a92bb76a66b2d9de.jpg");
		MockMultipartFile user_photo = new MockMultipartFile("user_photo", file);
		User user = new User();
		user.setName("jay");
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setGender("male");
		user.setPhone("9876543211");
		user.setSecQues("School");
		List<Address> addresses = new ArrayList<>();
		Address add = new Address();
		add.setStreet("abc");
		add.setState("abc");
		add.setCity("abc");
		addresses.add(add);
		user.setAddresses(addresses);
		String[] lang = { "Java" };
		user.setLang(lang);
		mockMvc.perform(multipart("/registerController").file(user_photo)
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE).accept(MediaType.MULTIPART_FORM_DATA_VALUE)
				.flashAttr("user", user).param("isadmin", "false").sessionAttr("admin", new User()))
				.andExpect(redirectedUrl("dashboard"));
	}
	
	@Test
	void registrationTestError() throws Exception {
		FileInputStream file = new FileInputStream(
				"C:\\Users\\usa\\OneDrive\\Pictures\\Saved Pictures\\45300ad3107192f2a92bb76a66b2d9de.jpg");
		MockMultipartFile user_photo = new MockMultipartFile("user_photo", file);
		User user = new User();
		user.setName("jay");
		user.setEmail("jay");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setGender("male");
		user.setPhone("9876543211");
		user.setSecQues("School");
		List<Address> addresses = new ArrayList<>();
		Address add = new Address();
		add.setStreet("abc");
		add.setState("abc");
		add.setCity("abc");
		addresses.add(add);
		user.setAddresses(addresses);
		String[] lang = { "Java" };
		user.setLang(lang);
		mockMvc.perform(multipart("/registerController").file(user_photo)
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE).accept(MediaType.MULTIPART_FORM_DATA_VALUE)
				.flashAttr("user", user).param("isadmin", "false").sessionAttr("admin", new User()))
				.andExpect(status().isOk());
	}

	@Test
	void loginTestForInvalidEmailOrPassword() throws Exception {
		String email = "jay@gmail.com";
		String password = "jay@1234";
		when(service.getUser(email, password)).thenReturn(null);
		mockMvc.perform(post("/loginController").param("email", email).param("password", password))
				.andExpect(status().isOk());
	}

	@Test
	void loginTestForAdmin() throws Exception {
		String email = "jay@gmail.com";
		String password = "jay@1234";
		User user = new User();
		user.setAdmin(true);
		when(service.getUser(email, password)).thenReturn(user);
		mockMvc.perform(post("/loginController").param("email", email).param("password", password))
				.andExpect(redirectedUrl("dashboard"));
	}

	@Test
	void loginTestForUser() throws Exception {
		String email = "jay@gmail.com";
		String password = "jay@1234";
		User user = new User();
		user.setAdmin(false);
		when(service.getUser(email, password)).thenReturn(user);
		mockMvc.perform(post("/loginController").param("email", email).param("password", password))
				.andExpect(redirectedUrl("home"));
	}

	@Test
	void logoutTest() throws Exception {
		mockMvc.perform(get("/logoutController")).andExpect(redirectedUrl("index"));
	}

	@Test
	void userDataTestRedirect() throws Exception {
		mockMvc.perform(post("/userDataController").param("id", "1")).andExpect(redirectedUrl("index"));
	}

	@Test
	void userDataTestForUser() throws Exception {
		User user = new User();
		user.setName("jay");
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setGender("male");
		user.setPhone("9876543211");
		user.setSecQues("School");
		List<Address> addresses = new ArrayList<>();
		Address add = new Address();
		add.setStreet("abc");
		add.setState("abc");
		add.setCity("abc");
		addresses.add(add);
		user.setAddresses(addresses);
		String[] lang = { "Java" };
		user.setLang(lang);
		when(service.getUserData(1)).thenReturn(user);
		mockMvc.perform(post("/userDataController").param("id", "1").sessionAttr("userSession", user))
				.andExpect(status().isOk());
	}

	@Test
	void userDataTestForAdmin() throws Exception {
		User user = new User();
		user.setName("jay");
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setGender("male");
		user.setPhone("9876543211");
		user.setSecQues("School");
		List<Address> addresses = new ArrayList<>();
		Address add = new Address();
		add.setStreet("abc");
		add.setState("abc");
		add.setCity("abc");
		addresses.add(add);
		user.setAddresses(addresses);
		String[] lang = { "Java" };
		user.setLang(lang);
		when(service.getUserData(1)).thenReturn(user);
		mockMvc.perform(post("/userDataController").param("id", "1").sessionAttr("admin", user))
				.andExpect(status().isOk());
	}

	@Test
	void dashboardTest() throws Exception {
		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setName("jay");
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setGender("male");
		user.setPhone("9876543211");
		user.setSecQues("School");
		List<Address> addresses = new ArrayList<>();
		Address add = new Address();
		add.setStreet("abc");
		add.setState("abc");
		add.setCity("abc");
		addresses.add(add);
		user.setAddresses(addresses);
		String[] lang = { "Java" };
		user.setLang(lang);
		users.add(user);
		when(service.getAllUser()).thenReturn(users);
		mockMvc.perform(post("/dashboardController")).andExpectAll(status().isOk());
	}

	@Test
	void deleteUserTestSuccess() throws Exception {
		when(service.deleteUser(1)).thenReturn(true);
		mockMvc.perform(post("/deleteController").param("id", "1")).andExpect(status().isOk());
	}

	@Test
	void deleteUserTestFail() throws Exception {
		when(service.deleteUser(1)).thenReturn(false);
		mockMvc.perform(post("/deleteController").param("id", "1")).andExpect(status().isOk());
	}

	@Test
	void addUsersTestWithoutError() throws Exception {
		when(service.addAllUser(anyList())).thenReturn("");
		FileInputStream file = new FileInputStream("G:\\Excel Practise\\Course 1\\Week 1\\Dummy.xlsx");
		MockMultipartFile excelFile = new MockMultipartFile("excelFile", "Dummy.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", file);
		mockMvc.perform(multipart("/usersController").file(excelFile)).andExpect(status().isOk());
	}

	@Test
	void addUsersTestWithError() throws Exception {
		when(service.addAllUser(anyList())).thenReturn("Error");
		FileInputStream file = new FileInputStream("G:\\Excel Practise\\Course 1\\Week 1\\Dummy.xlsx");
		MockMultipartFile excelFile = new MockMultipartFile("excelFile", "Dummy.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", file);
		mockMvc.perform(multipart("/usersController").file(excelFile)).andExpect(status().isOk());
	}
	
	@Test
	void addUserTestWithFileNotFoundError() throws Exception {
		when(service.addAllUser(anyList())).thenReturn("Error");
		MockMultipartFile excelFile = new MockMultipartFile("excelFile", "".getBytes());
		mockMvc.perform(multipart("/usersController").file(excelFile)).andExpect(status().isOk());
	}
	
	@Test
	void addUserTestWithNotAnExcelFile() throws Exception {
		when(service.addAllUser(anyList())).thenReturn("Error");
		MockMultipartFile excelFile = new MockMultipartFile("excelFile", "Dummy.xlsx", MediaType.TEXT_PLAIN_VALUE, "abc".getBytes());
		mockMvc.perform(multipart("/usersController").file(excelFile)).andExpect(status().isOk());
	}
	
	@Test
	void checkEmailTestWhenEmailExists() throws Exception {
		String email = "jay@gmail.com";
		when(service.checkEmail(email)).thenReturn(true);
		mockMvc.perform(post("/emailCheckController").param("email", email)).andExpect(status().isOk());
	}

	@Test
	void checkEmailTestWhenEmailDoesntExists() throws Exception {
		String email = "jay@gmail.com";
		when(service.checkEmail(email)).thenReturn(false);
		mockMvc.perform(post("/emailCheckController").param("email", email)).andExpect(status().isOk());
	}

	@Test
	void updateUserDataTestWhenUserUpdates() throws Exception {
		User user = new User();
		user.setId(1);
		user.setName("jay");
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setGender("male");
		user.setPhone("9876543211");
		user.setSecQues("School");
		List<Address> addresses = new ArrayList<>();
		Address add = new Address();
		add.setStreet("abc");
		add.setState("abc");
		add.setCity("abc");
		addresses.add(add);
		user.setAddresses(addresses);
		String[] lang = { "Java" };
		user.setLang(lang);
		User expectedUser = new User();
		expectedUser.setName("jay");
		expectedUser.setEmail("jay@gmail.com");
		expectedUser.setPassword("jay@1234");
		expectedUser.setGame("GTA");
		expectedUser.setGender("male");
		expectedUser.setPhone("9876543211");
		expectedUser.setSecQues("School");
		when(service.getUserData(user.getId())).thenReturn(expectedUser);
		doNothing().when(service).updateUser(user);
		FileInputStream file = new FileInputStream(
				"C:\\Users\\usa\\OneDrive\\Pictures\\Saved Pictures\\45300ad3107192f2a92bb76a66b2d9de.jpg");
		MockMultipartFile user_photo = new MockMultipartFile("user_photo", file);
		mockMvc.perform(multipart("/updateController").file(user_photo)
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE).accept(MediaType.MULTIPART_FORM_DATA_VALUE)
				.flashAttr("user", user).param("isadmin", "false").param("address_id", "1").sessionAttr("userSession", new User()))
				.andExpect(redirectedUrl("home"));
	}
	
	@Test
	void updateUserDataTestWithError() throws Exception {
		User user = new User();
		user.setId(1);
		user.setName("jay");
		user.setEmail("jay.com");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setGender("male");
		user.setPhone("9876543211");
		user.setSecQues("School");
		List<Address> addresses = new ArrayList<>();
		Address add = new Address();
		add.setStreet("abc");
		add.setState("abc");
		add.setCity("abc");
		addresses.add(add);
		user.setAddresses(addresses);
		String[] lang = { "Java" };
		user.setLang(lang);
		User expectedUser = new User();
		expectedUser.setName("jay");
		expectedUser.setEmail("jay@gmail.com");
		expectedUser.setPassword("jay@1234");
		expectedUser.setGame("GTA");
		expectedUser.setGender("male");
		expectedUser.setPhone("9876543211");
		expectedUser.setSecQues("School");
		when(service.getUserData(user.getId())).thenReturn(expectedUser);
		FileInputStream file = new FileInputStream(
				"C:\\Users\\usa\\OneDrive\\Pictures\\Saved Pictures\\45300ad3107192f2a92bb76a66b2d9de.jpg");
		MockMultipartFile user_photo = new MockMultipartFile("user_photo", file);
		mockMvc.perform(multipart("/updateController").file(user_photo)
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE).accept(MediaType.MULTIPART_FORM_DATA_VALUE)
				.flashAttr("user", user).param("isadmin", "false").param("address_id", "1").sessionAttr("userSession", new User()))
				.andExpect(status().isOk());
	}
	
	@Test
	void updateUserDataTestToUpdateAdmin() throws Exception {
		User user = new User();
		user.setId(1);
		user.setName("jay");
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setGame("GTA");
		user.setGender("male");
		user.setPhone("9876543211");
		user.setSecQues("School");
		List<Address> addresses = new ArrayList<>();
		Address add = new Address();
		add.setStreet("abc");
		add.setState("abc");
		add.setCity("abc");
		addresses.add(add);
		user.setAddresses(addresses);
		String[] lang = { "Java" };
		user.setLang(lang);
		User expectedUser = new User();
		expectedUser.setName("jay");
		expectedUser.setEmail("jay@gmail.com");
		expectedUser.setPassword("jay@1234");
		expectedUser.setGame("GTA");
		expectedUser.setGender("male");
		expectedUser.setPhone("9876543211");
		expectedUser.setSecQues("School");
		when(service.getUserData(user.getId())).thenReturn(expectedUser);
		doNothing().when(service).updateUser(user);
		MockMultipartFile user_photo = new MockMultipartFile("user_photo", "".getBytes());
		mockMvc.perform(multipart("/updateController").file(user_photo)
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE).accept(MediaType.MULTIPART_FORM_DATA_VALUE)
				.flashAttr("user", user).param("isadmin", "true").param("address_id", "1").sessionAttr("admin", new User()))
				.andExpect(redirectedUrl("dashboard"));
	}
	
	@Test
	void forgotPasswordTestForValidUser() throws Exception {
		User user = new User();
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setSecQues("School");
		user.setGame("GTA");
		when(service.updatePassword(user)).thenReturn(true);
		mockMvc.perform(post("/forgotController").flashAttr("user", user)).andExpect(redirectedUrl("index"));
	}

	@Test
	void forgotPasswordTestForInvalidUser() throws Exception {
		User user = new User();
		user.setEmail("jay@gmail.com");
		user.setPassword("jay@1234");
		user.setSecQues("School");
		user.setGame("GTA");
		when(service.updatePassword(user)).thenReturn(false);
		mockMvc.perform(post("/forgotController").flashAttr("user", user)).andExpect(status().isOk());
	}
}
