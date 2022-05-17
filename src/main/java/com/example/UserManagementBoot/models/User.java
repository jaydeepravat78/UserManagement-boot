package com.example.UserManagementBoot.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "*Name cannot be empty<br>")
	@Pattern(regexp = "^[a-zA-Z ]+$", message = "*Please enter a valid name<br>")
	public String name;

	@Pattern(regexp = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$", message = "*Please enter a valid email<br>")
	private String email;

	@NotBlank(message = "*Password cannot be empty<br>")
	private String password;

	@NotBlank(message = "*Phone number cannot be empty<br>")
	@Pattern(regexp = "[7-9][0-9]{9}", message = "*Please enter a valid phone number<br>")
	private String phone;
	@NotBlank(message = "*Please select a gender<br>")
	private String gender;
	@NotBlank(message = "*Please select a game<br>")
	private String game;
	@NotNull(message = "*Please select atleast one language<br>")
	private String[] lang;
	@NotBlank(message = "*Please answer the security question<br>")
	private String secQues;
	@Lob
	private String profilePic;
	private boolean isAdmin;

	@Valid
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Address> addresses;

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getSecQues() {
		return secQues;
	}

	public void setSecQues(String secQues) {
		this.secQues = secQues;
	}

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String[] getLang() {
		return Arrays.copyOf(this.lang, lang.length);
	}

	public void setLang(String[] lang) {
		this.lang = Arrays.copyOf(lang, lang.length);
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

}
