package com.smart.contact.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.contact.entity.Contact;
import com.smart.contact.entity.User;
import com.smart.contact.repositery.ContactRepositery;
import com.smart.contact.repositery.UserRepositery;

@Controller
@RequestMapping("/user")
public class ContactController {

	

	@Autowired
	private UserRepositery userRepositery;

	@Autowired
	private ContactRepositery contactRepositery;

	
	
	@GetMapping("/add-contact")
	public String getContactForm(Model m, Principal principal) {

		m.addAttribute("user", this.userRepositery.findUserByUserEmail(principal.getName()));
		m.addAttribute("contact", new Contact());

		return "user/add-contact";
	}

	@PostMapping("/process-contact")
	public String setContactForm(@Valid @ModelAttribute("contact") Contact contact,
			@RequestParam("profileImage") MultipartFile file, Principal principal, Model model, BindingResult rs) {

		if (rs.hasErrors()) {
			model.addAttribute("contact", contact);
			model.addAttribute("warning", rs.getFieldError());

			return "user/home";

		}

		User user = this.userRepositery.findUserByUserEmail(principal.getName());

		try {

			if (file.isEmpty()) {
				contact.setImage("1.png");
			} else {

				String saveImagePath = "UCNT" + new Date().getTime() + file.getOriginalFilename();

				File saveDirectory = new ClassPathResource("static/img/contact").getFile();

				Path path = Paths.get(saveDirectory.getAbsolutePath() + File.separator + saveImagePath);

				contact.setImage(saveImagePath);

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			}

			contact.setUser(user);

			user.getContacts().add(contact);

			this.userRepositery.save(user);
			model.addAttribute("user", user);

			model.addAttribute("message", "Contact has added successfully.");

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			model.addAttribute("warning", "Somthing went wrong try again.");

		}

		return "user/add-contact";
	}

	@GetMapping("show-contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page, Principal principal, Model model) {

		System.out.println("i m in show contact");
		User user = this.userRepositery.findUserByUserEmail(principal.getName());

		PageRequest pageRequest = PageRequest.of(page, 10);

		Page<Contact> contactsByUser = this.contactRepositery.findContactsByUser(user, pageRequest);

		model.addAttribute("contacts", contactsByUser);
		model.addAttribute("user", user);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPage", contactsByUser.getTotalPages());

		return "user/show_contacts";
	}

	@GetMapping("contact/{id}")
	public String contactById(@PathVariable("id") Long id, Model model, Principal principal) {

		try {
			Contact contact = this.contactRepositery.findById(id).get();
			User user = this.userRepositery.findUserByUserEmail(principal.getName());

			if (contact.getUser().getId() == user.getId()) {
				model.addAttribute("contact", contact);

				model.addAttribute("user", user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "user/contact";
	}

	@GetMapping("delete/{ContactId}/{currentPage}")
	public String deleteContactById(@PathVariable("ContactId") Long id, @PathVariable("currentPage") Long currentPage,
			Model model, Principal principal) {

		this.contactRepositery.deleteById(id);

		return "redirect:/user/show-contacts/" + currentPage;
	}

	@GetMapping("update/{id}")
	private String updateContact(@PathVariable("id") Long id, Principal principal, Model model) {

		Contact contact = this.contactRepositery.findById(id).get();

		model.addAttribute("contact", contact);
		model.addAttribute("user", this.userRepositery.findUserByUserEmail(principal.getName()));
		return "user/update";

	}

	@PostMapping("update-contact/{id}")
	private String updateHandler(@PathVariable("id") Long id, Principal principal, Model model,
			@Valid @ModelAttribute("contact") Contact contact, @RequestParam("profileImage") MultipartFile file) {

		User user = this.userRepositery.findUserByUserEmail(principal.getName());
		
		
		Contact contact2 = this.contactRepositery.findById(id).get();
		
		
		try {

			if (file.isEmpty()){
				contact.setImage(contact2.getImage());
				
			}else{
				
				String imageNameWithExtention = "UCNT"+new Date().getTime()+file.getOriginalFilename();
				
				File saveDir=new ClassPathResource("static/img/contact").getFile();
				
				Path path=Paths.get(saveDir.getAbsolutePath()+File.separator+imageNameWithExtention);
				
				contact.setImage(imageNameWithExtention);
				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		contact.setImage(contact.getImage());
		contact.setId(id);
		contact.setUser(user);
		
		this.contactRepositery.save(contact);

		model.addAttribute("contact", contact);
		model.addAttribute("user", user);

		return "user/update";
	}

}
