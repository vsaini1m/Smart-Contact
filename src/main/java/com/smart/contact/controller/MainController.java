package com.smart.contact.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.contact.entity.User;
import com.smart.contact.repositery.UserRepositery;

@Controller
public class MainController {

	@Autowired
	private UserRepositery userRepositery;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private @Valid User save;

	@GetMapping("/")
	public String getIndex() {

		return "index";
	}

	@GetMapping("/register")
	public String getRegister(Model m) {

		m.addAttribute("user", new User());

		return "register";
	}

	@GetMapping("/login")
	public String getLogin(Model m) {

		m.addAttribute("user", new User());
		
		return "login";
	}

	@PostMapping("/register")
	public String setRegister(@Valid @ModelAttribute("user") User user, BindingResult rs, Model m,
			@RequestParam("perofileImage") MultipartFile file) throws IOException {

		if (rs.hasErrors()) {
			m.addAttribute("user", user);
			System.out.println("ERRORS");
			return "register";
		}

		// save user

		if (file.isEmpty()) {
			user.setImage("1.png");
		} else {

			String imageNameWithExtention = "UPI" + new Date().getTime() + file.getOriginalFilename();

			File saveDir = new ClassPathResource("static/img/user").getFile();
			Path pth = Paths.get(saveDir.getAbsolutePath() + File.separator + imageNameWithExtention);

			user.setImage(imageNameWithExtention);

			Files.copy(file.getInputStream(), pth, StandardCopyOption.REPLACE_EXISTING);

		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		user.setEnabled(true);

		User savedUser = this.userRepositery.save(user);

		if (user != null)
			return "index";

		return "register";
	}

}
