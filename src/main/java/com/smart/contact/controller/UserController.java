package com.smart.contact.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.contact.entity.User;
import com.smart.contact.repositery.ContactRepositery;
import com.smart.contact.repositery.UserRepositery;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepositery userRepositery;

	@Autowired
	private ContactRepositery contactRepositery;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	

	@GetMapping("/home")
	public String getHome(Principal principal, Model m) {

		String name = principal.getName();

		// fatch users data
		User user = this.userRepositery.findUserByUserEmail(name);

		m.addAttribute("user", user);

		return "user/home";
	}

		
	@GetMapping("/settings")
	private String getSettings(Principal principal,Model model) {

		model.addAttribute("user",this.userRepositery.findUserByUserEmail(principal.getName()));
		
		return "user/settings";
	}

	
	
	@PostMapping("/updateProfile")
	private String setSettings(Principal principal,Model model,@Valid @ModelAttribute("user")User user) {
	
		user.setId(this.userRepositery.findUserByUserEmail(principal.getName()).getId());
		
		@Valid
		User save = this.userRepositery.save(user);
		
		model.addAttribute("user",save);
		
		
		
		return "user/settings";
	}
	
	
	@PostMapping("/updatePerofileImage")
	private String updateProfileImage(@RequestParam("profileImage") MultipartFile file,Principal principal,Model model) {
		User user = this.userRepositery.findUserByUserEmail(principal.getName());
		
		try {
			
			if (file.isEmpty()) {
				user.setImage(user.getImage());
			}else {
				
				String saveFileNameWithExtention="UPI"+new Date().getTime()+file.getOriginalFilename();
				
				File saveDir=new ClassPathResource("static/img/user").getFile();
				
				Path pth=Paths.get(saveDir.getAbsolutePath()+File.separator+saveFileNameWithExtention);
				
				Files.copy(file.getInputStream(), pth,StandardCopyOption.REPLACE_EXISTING);
				
				user.setImage(saveFileNameWithExtention);
				
			}
			
			this.userRepositery.save(user);
			
		} catch (Exception e) {

		e.printStackTrace();
		}
		
		
		model.addAttribute("user",user);
		return "user/settings";
	}
	
	
	
	@GetMapping("/changePassword")
	private String setChangerPassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,Principal principal
			,Model model
			) {
		
		
		
		
		User user = this.userRepositery.findUserByUserEmail(principal.getName());
		
		if(bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
			
			user.setPassword(bCryptPasswordEncoder.encode(newPassword));
			
		
			
			model.addAttribute("successMsg","Password changed successfully");
			
			
			
		}else {
			model.addAttribute("errorMsg","Password not match , try again");
			
			
			
		
		}
		
		
		
		model.addAttribute("user",user);
		return "user/settings";

	}
	
	
}
