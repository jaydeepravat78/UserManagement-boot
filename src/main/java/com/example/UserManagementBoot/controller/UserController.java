package com.example.UserManagementBoot.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.UserManagementBoot.models.Address;
import com.example.UserManagementBoot.models.CustomUserDetails;
import com.example.UserManagementBoot.models.User;
import com.example.UserManagementBoot.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
public class UserController {

	@Autowired
	UserService service;
	
	@Autowired
	PasswordEncoder encoder;
	
	
	private static final Logger log = LogManager.getLogger(UserController.class);
	private static final String HOMEPAGE = "home";
	private static final String DAHSBOARDPAGE = "dashboard";
	private static final String INDEXPAGE = "index";
	private static final String REGISTRATIONPAGE = "registration";
	private static final String FORGOTPAGE = "forgot";

	@GetMapping("/")
	public String defaultUrl(HttpSession session, HttpServletResponse response) {
		if (session != null && session.getAttribute("userSession") != null)
			return "redirect:" + HOMEPAGE;
		else if (session != null && session.getAttribute("admin") != null)
			return "redirect:" + DAHSBOARDPAGE;
		else
			return INDEXPAGE;
	}

	@GetMapping("/index")
	public String index(HttpSession session, HttpServletResponse response) {
		if (session != null && session.getAttribute("userSession") != null)
			return "redirect:" + HOMEPAGE;
		else if (session != null && session.getAttribute("admin") != null)
			return "redirect:" + DAHSBOARDPAGE;
		else {
			return INDEXPAGE;
		}
	}

	@GetMapping("/registration")
	public String register() {
		return REGISTRATIONPAGE;
	}

	@GetMapping("/forgot")
	public String forgot() {
		return FORGOTPAGE;
	}
	
	@GetMapping("/failedLogin")
	public String failedLogin(Model model) {
		model.addAttribute("errorMessage", "*Invalid user email or password");
		return INDEXPAGE;
	}

	@GetMapping("/home")
	public String home(HttpSession session, HttpServletResponse response) {
			return HOMEPAGE;		
	}

	@GetMapping("/dashboard")
	public String admindashboard(HttpSession session, HttpServletResponse response) {
			return  DAHSBOARDPAGE;
	}

	@PostMapping(path = "/registerController", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String registration(@Valid @ModelAttribute User user, BindingResult br,
			@RequestPart MultipartFile user_photo, @RequestParam String isadmin, HttpSession session, Model model)
			throws IOException {
		if (br.hasErrors()) {
			StringBuilder errors = new StringBuilder();
			List<FieldError> error = br.getFieldErrors();
			for (FieldError err : error)
				errors.append(err.getDefaultMessage());
			model.addAttribute("error", errors.toString());
			model.addAttribute("userError", user);
			return REGISTRATIONPAGE;
		} else {
			user.setProfilePic(Base64.getEncoder().encodeToString(user_photo.getBytes()));
			user.setPassword(encoder.encode(user.getPassword()));
			if (isadmin.equals("true"))
				user.setRole("ROLE_ADMIN");
			else
				user.setRole("ROLE_USER");
			service.addUser(user);
			log.info(user.getEmail() + " signed up");
			if (session != null && session.getAttribute("admin") != null)
				return "redirect:" + DAHSBOARDPAGE;
			return "redirect:" + INDEXPAGE;
		}
	}

	@GetMapping("/loginController")
	public String login(HttpSession session, HttpServletResponse response) {
		CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = details.getUser();
		if (user != null) {
			if (user.getRole().equals("ROLE_ADMIN")) {
				log.info("Admin logged in: " + user.getId());
				session.setAttribute("admin", user);
				return "redirect:" + DAHSBOARDPAGE;
			} else {
				log.info("User logged in: " + user.getId());
				session.setAttribute("userSession", user);
				return "redirect:" + HOMEPAGE;
			}
		} else {
			return INDEXPAGE;
		}
	}

	@PostMapping("/userDataController")
	public String userData(@RequestParam int id, Model model, HttpSession session, HttpServletResponse response) {
		if (session != null && (session.getAttribute("userSession") != null || session.getAttribute("admin") != null)) {
			User userData = service.getUserData(id);
			model.addAttribute("userData", userData);
			return REGISTRATIONPAGE;
		} else {
			return "redirect:" + INDEXPAGE;
		}
	}

	@PostMapping(path = "/dashboardController")
	@ResponseBody
	public JsonObject dashboard() {
		List<User> users = service.getAllUser();
		List<User> usersdata = new ArrayList<>();
		for (User user : users) {
			User userdata = new User();
			userdata.setId(user.getId());
			userdata.setName(user.getName());
			userdata.setEmail(user.getEmail());
			userdata.setPhone(user.getPhone());
			userdata.setGame(user.getGame());
			userdata.setGender(user.getGender());
			usersdata.add(userdata);
		}
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		jobj.addProperty("status", "success");
		jobj.add("data", gson.toJsonTree(usersdata));
		return jobj;
	}

	@PostMapping("/deleteController")
	@ResponseBody
	public boolean delete(@RequestParam int id) {
		log.info(id + ": user deleted");
		return service.deleteUser(id);
	}

	@PostMapping("/usersController")
	public String addUsers(@RequestPart MultipartFile excelFile, Model model) throws IOException {
		String fileType = excelFile.getContentType();
		if (fileType != null && fileType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			InputStream out = excelFile.getInputStream();
			try (XSSFWorkbook wb = new XSSFWorkbook(out)) {
				XSSFSheet sheet = wb.getSheetAt(0);
				Iterator<Row> itr = sheet.iterator();
				List<User> users = new ArrayList<>();
				while (itr.hasNext()) {
					Row row = itr.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					User user = new User();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						int columnIndex = cell.getColumnIndex();
						switch (columnIndex) {
						case 0:
							if (cell.getCellTypeEnum() == CellType.STRING)
								user.setName(cell.getStringCellValue());
							break;
						case 1:
							if (cell.getCellTypeEnum() == CellType.STRING)
								user.setEmail(cell.getStringCellValue());
							break;
						case 2:
							if (cell.getCellTypeEnum() == CellType.STRING)
								user.setPassword(cell.getStringCellValue());
							break;
						case 3:
							if (cell.getCellTypeEnum() == CellType.NUMERIC)
								user.setPhone(String.valueOf((long) cell.getNumericCellValue()));
							break;
						case 4:
							if (cell.getCellTypeEnum() == CellType.STRING)
								user.setGender(cell.getStringCellValue());
							break;
						case 5:
							if (cell.getCellTypeEnum() == CellType.STRING)
								user.setLang(cell.getStringCellValue().split(" "));
							break;
						case 6:
							if (cell.getCellTypeEnum() == CellType.STRING)
								user.setGame(cell.getStringCellValue());
							break;
						case 7:
							if (cell.getCellTypeEnum() == CellType.STRING)
								user.setSecQues(cell.getStringCellValue());
							break;
						default:
							log.error("Number of column exceded");
						}
					}
					users.add(user);
				}

				String error = service.addAllUser(users);
				if (error.isEmpty()) {
					log.info(users.size() + " users added");
				} else {
					log.info("No User added");
					model.addAttribute("errorMessage", error);
				}
			}
		} else {
			log.info("error");
			model.addAttribute("errorMessage", "Please enter a excel file");
		}
		return DAHSBOARDPAGE;
	}

	@PostMapping(path = "/emailCheckController")
	@ResponseBody
	public boolean checkEmail(@RequestParam String email) {
		return service.checkEmail(email);
	}

	@PostMapping(path = "/updateController", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String update(@Valid @ModelAttribute User user, BindingResult br, @RequestPart MultipartFile user_photo,
			@RequestParam String isadmin, @RequestParam String[] address_id, HttpSession session, Model model)
			throws IOException {
		User oldData = service.getUserData(user.getId());
		if (br.hasErrors()) {
			StringBuilder errors = new StringBuilder();
			List<FieldError> error = br.getFieldErrors();
			for (FieldError err : error)
				errors.append(err.getDefaultMessage());
			user.setEmail(oldData.getEmail());
			model.addAttribute("error", errors.toString());
			model.addAttribute("userError", user);
			return REGISTRATIONPAGE;
		}
		user.setEmail(oldData.getEmail());
		user.setPassword(encoder.encode(user.getPassword()));
		if (isadmin.equals("true"))
			user.setRole("ROLE_ADMIN");
		else
			user.setRole("ROLE_USER");
		if (user_photo.isEmpty()) {
			user.setProfilePic(oldData.getProfilePic());
		} else {
			user.setProfilePic(Base64.getEncoder().encodeToString(user_photo.getBytes()));
		}
		List<Address> addresses = user.getAddresses();
		for (int i = 0; i < address_id.length; i++) {
			if (!address_id[i].isEmpty()) {
				int id = Integer.parseInt(address_id[i]);
				addresses.get(i).setAddress_id(id);
			}
		}
		service.updateUser(user);
		log.info(user.getId() + ": Data updated");
		if (session != null && session.getAttribute("userSession") != null) {
			session.setAttribute("userSession", user);
			return "redirect:" + HOMEPAGE;
		} else
			return "redirect:" + DAHSBOARDPAGE;
	}

	@PostMapping("/forgotController")
	public String forgot(@ModelAttribute User user, Model model) {
		user.setPassword(encoder.encode(user.getPassword()));
		if (service.updatePassword(user)) {
			log.info(user.getEmail() + ": " + "Updated password");
			return "redirect:" + INDEXPAGE;
		} else {
			model.addAttribute("error", "Please enter correct details");
			return FORGOTPAGE;
		}
	}
}
