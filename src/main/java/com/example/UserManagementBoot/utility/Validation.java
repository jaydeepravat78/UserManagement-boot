package com.example.UserManagementBoot.utility;

import java.util.regex.Pattern;

public class Validation {
	
	public static String checkName(String name) {
		if (name == null || name.isEmpty())
			return "*Name field cannot be empty\n";
		else if (!Pattern.matches("^[a-zA-Z]+$", name))
			return "*Name should only contains alphabet\n";
		else
			return "";
	}

	public static String checkEmail(String email) {
		if (email == null || email.isEmpty())
			return "*Email should not be empty\n";
		else if (!Pattern.matches("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$", email))
			return "*Please enter a valid email\n";
		else
			return "";
	}

	public static String checkPhone(String phone) {
		if (phone == null || phone.isEmpty())
			return "*Phone should not be empty";
		else if (!Pattern.matches("[7-9][0-9]{9}", phone))
			return "*Please enter a valid phone number\n";
		else
			return "";
	}

	public static String checkLang(String[] lang) {
		if (lang == null)
			return "*Select atleast one language\n";
		else
			return "";
	}

	public static String checkGender(String gender) {
		if (gender == null)
			return "*Please select gender\n";
		else
			return "";
	}

	public static String checkGame(String game) {
		if (game == null || game.isEmpty())
			return "*Please select a game\n";
		else
			return "";
	}

	public static String checkSecQues(String secQues) {
		if (secQues == null || secQues.isEmpty())
			return "*Please answer the Security question\n";
		else
			return "";
	}

	public static String checkPassword(String Password) {
		if (Password == null || Password.isEmpty())
			return "*Please enter a password";
		else if (!Pattern.matches("^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,30}$", Password))
			return "*Password should have minimum 8 and maximum 30 character with a number, alphabet character and a special character\n";
		else
			return "";
	}

	public static String confirmPassword(String password, String confirmPassword) {
		if (!password.equals(confirmPassword))
			return "*Password doesn't match\n";
		else
			return "";
	}

	public static String checkStreet(String[] street) {
		for (int i = 0; i < street.length; i++)
			if (street[i].isEmpty())
				return "*Please enter all street\n";
		return "";
	}

	public static String checkCity(String[] city) {
		for (int i = 0; i < city.length; i++)
			if (city[i].isEmpty())
				return "*Please enter all city\n";
		return "";
	}

	public static String checkState(String[] state) {
		for (int i = 0; i < state.length; i++)
			if (state[i].isEmpty())
				return "*Please enter all state\n";
		return "";
	}

}
