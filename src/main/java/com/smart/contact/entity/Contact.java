package com.smart.contact.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;  
	
	@NotBlank(message = "Name must be not empty")
	@Size(min = 5,max = 50,message = "name's length must be between 5-50 charactor")
	private String name;
	
	private String secondName;
	
	private String email;
	
	@Column(length=500)
	private String about;
	
	private String image;
	
	private String work;
	
	private String phone;
	
	@ManyToOne
	@JsonIgnore
	private User user;

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

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", secondName=" + secondName + ", email=" + email + ", about="
				+ about + ", image=" + image + ", work=" + work + ", phone=" + phone + ", user=" + user + "]";
	}

	public Contact(long id, String name, String secondName, String email, String about, String image, String work,
			String phone, User user) {
		super();
		this.id = id;
		this.name = name;
		this.secondName = secondName;
		this.email = email;
		this.about = about;
		this.image = image;
		this.work = work;
		this.phone = phone;
		this.user = user;
	}

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
