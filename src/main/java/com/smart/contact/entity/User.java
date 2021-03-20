package com.smart.contact.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank(message = "Name must be not empty")
	@Size(min = 5,max = 20,message = "name must be between 5 - 20")
	private String name;
	
	@Column(unique = true)	
	@Email(message = "please enter valid email")
	private String email;
	
	private String image;
	
	@Size(min = 8,max = 100 ,message = "password length must be between 8 - 10")
	private String password;
	
	@Size(min = 8,message = "please type at least 20 charactars somthing about yourself")
	@Column(length=500)
	private String about;
	private String role;
	private boolean enabled;
	
	

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
	private List<Contact> contacts=new ArrayList<>();


	public long getId() {
		return id;
	}


	public void setId(long id) {
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


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getAbout() {
		return about;
	}


	public void setAbout(String about) {
		this.about = about;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public List<Contact> getContacts() {
		return contacts;
	}


	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

/*
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", image=" + image + ", password=" + password
				+ ", about=" + about + ", role=" + role + ", enabled=" + enabled + ", contacts=" + contacts + "]";
	}
*/

	public User(long id, String name, String email, String image, String password, String about, String role,
			boolean enabled, List<Contact> contacts) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.image = image;
		this.password = password;
		this.about = about;
		this.role = role;
		this.enabled = enabled;
		this.contacts = contacts;
	}


	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
