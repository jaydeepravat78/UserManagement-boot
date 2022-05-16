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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import com.example.UserManagementBoot.models.User;
import com.example.UserManagementBoot.services.UserService;
import com.example.UserManagementBoot.utility.KeyGeneration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
public class UserController {

	@Autowired
	UserService service;

	private static final Logger log = Logger.getLogger(UserController.class);

	@GetMapping("/")
	public String defaultUrl(HttpSession session, HttpServletResponse response) {

		response.setHeader("Cache-Control", "no-cache"); // Forces caches to obtain a new copy of the page from the
															// origin server
		response.setHeader("Cache-Control", "no-store"); // Directs caches not to store the page under any
															// circumstance
		response.setDateHeader("Expires", 0); // Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0 backward compatibility
		if (session != null && session.getAttribute("userSession") != null)
			return "redirect:home";
		else if (session != null && session.getAttribute("admin") != null)
			return "redirect:dashboard";
		else
			return "index";
	}

	@GetMapping("/index")
	public String index(HttpSession session, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache"); // Forces caches to obtain a new copy of the page from the
															// origin server
		response.setHeader("Cache-Control", "no-store"); // Directs caches not to store the page under any
															// circumstance
		response.setDateHeader("Expires", 0); // Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0 backward compatibility
		if (session != null && session.getAttribute("userSession") != null)
			return "redirect:home";
		else if (session != null && session.getAttribute("admin") != null)
			return "redirect:dashboard";
		else {
			return "index";
		}
	}

	@GetMapping("/registration")
	public String register() {
		return "registration";
	}

	@GetMapping("/forgot")
	public String forgot() {
		return "forgot";
	}

	@GetMapping("/home")
	public String home(HttpSession session, HttpServletResponse response) {

		response.setHeader("Cache-Control", "no-cache"); // Forces caches to obtain a new copy of the page from the
															// origin server
		response.setHeader("Cache-Control", "no-store"); // Directs caches not to store the page under any
															// circumstance
		response.setDateHeader("Expires", 0); // Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0 backward compatibility
		if (session != null && session.getAttribute("userSession") != null)
			return "home";
		else
			return "redirect:index";
	}

	@GetMapping("/dashboard")
	public String admindashboard(HttpSession session, HttpServletResponse response) {

		response.setHeader("Cache-Control", "no-cache"); // Forces caches to obtain a new copy of the page from the
															// origin server
		response.setHeader("Cache-Control", "no-store"); // Directs caches not to store the page under any
															// circumstance
		response.setDateHeader("Expires", 0); // Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0 backward compatibility
		if (session != null && session.getAttribute("admin") != null)
			return "dashboard";
		else
			return "redirect:index";
	}

	@PostMapping(path = "/registerController", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String registration(@Valid @ModelAttribute User user, BindingResult br,
			@RequestPart MultipartFile user_photo, HttpSession session, Model model) {
		StringBuilder errors = new StringBuilder();
		if (br.hasErrors()) {
			List<FieldError> error = br.getFieldErrors();
			for (FieldError err : error)
				errors.append(err.getDefaultMessage());
			model.addAttribute("error", errors.toString());
			model.addAttribute("userError", user);
			return "registration";
		} else {
			try {
				user.setAdmin(false);
				user.setPassword(KeyGeneration.encrypt(user.getPassword()));
				user.setProfilePic(Base64.getEncoder().encodeToString(user_photo.getBytes()));
			} catch (IOException e) {
				log.error(e);
			}
			service.addUser(user);
			log.info(user.getEmail() + " signed up");
			if (session != null && session.getAttribute("admin") != null)
				return "redirect:dashboard";
			return "index";
		}
	}

	@PostMapping("/loginController")
	public String login(@RequestParam String email, @RequestParam String password, Model model, HttpSession session,
			HttpServletResponse response) {

		response.setHeader("Cache-Control", "no-cache"); // Forces caches to obtain a new copy of the page from the
															// origin server
		response.setHeader("Cache-Control", "no-store"); // Directs caches not to store the page under any
															// circumstance
		response.setDateHeader("Expires", 0); // Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0 backward compatibility
		BasicConfigurator.configure();
		User user = service.getUser(email, password);
		if (user != null) {
			if (user.isAdmin()) {
				log.info("Admin logged in: " + user.getId());
				session.setAttribute("admin", user);
				return "redirect:dashboard";
			} else {
				log.info("User logged in: " + user.getId());
				session.setAttribute("userSession", user);
				return "redirect:home";
			}
		} else {
			model.addAttribute("errorMessage", "*Invalid user email or password");
			model.addAttribute("errorEmail", email);
			return "index";
		}
	}

	@GetMapping("/logoutController")
	public String logout(HttpSession session, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache"); // Forces caches to obtain a new copy of the page from the
															// origin server
		response.setHeader("Cache-Control", "no-store"); // Directs caches not to store the page under any circumstance
		response.setDateHeader("Expires", 0); // Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0 backward compatibility
		session.invalidate();
		return "redirect:index";
	}

	@PostMapping("/userDataController")
	public String userData(@RequestParam int id, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache"); // Forces caches to obtain a new copy of the page from the
		// origin server
		response.setHeader("Cache-Control", "no-store"); // Directs caches not to store the page under any circumstance
		response.setDateHeader("Expires", 0); // Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0 backward compatibility
		if (session != null && (session.getAttribute("userSession") != null || session.getAttribute("admin") != null)) {
			User userData = service.getUserData(id);
			model.addAttribute("userData", userData);
			return "registration";
		} else {
			return "redirect:index";
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
		return "dashboard";
	}

	@PostMapping(path = "/emailCheckController")
	@ResponseBody
	public boolean checkEmail(@RequestParam String email) {
		return service.checkEmail(email);
	}

	@PostMapping(path = "/updateController", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String update(@Valid @ModelAttribute User user, BindingResult br, @RequestPart MultipartFile user_photo,
			@RequestParam String[] address_id, HttpSession session, Model model) {
		StringBuilder errors = new StringBuilder();
		User oldData = service.getUserData(user.getId());
		if (br.hasErrors()) {
			List<FieldError> error = br.getFieldErrors();
			for (FieldError err : error)
				errors.append(err.getDefaultMessage());
			user.setEmail(oldData.getEmail());
			model.addAttribute("error", errors.toString());
			model.addAttribute("userError", user);
			return "registration";
		}
		user.setEmail(oldData.getEmail());
		user.setPassword(KeyGeneration.encrypt(user.getPassword()));
		if (user_photo.isEmpty()) {
			user.setProfilePic(oldData.getProfilePic());
		} else {
			try {
				user.setProfilePic(Base64.getEncoder().encodeToString(user_photo.getBytes()));
			} catch (IOException e) {
				log.error(e);
			}
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
			return "redirect:home";
		}
		else
			return "redirect:dashboard";
	}

	@PostMapping("/forgotController")
	public String forgot(@ModelAttribute User user, Model model) {
		if (service.updatePassword(user)) {
			log.info(user.getEmail() + ": " + "Updated password");
			return "redirect:index";
		} else {
			model.addAttribute("error", "Please enter correct details");
			return "forgot";
		}
	}

}
