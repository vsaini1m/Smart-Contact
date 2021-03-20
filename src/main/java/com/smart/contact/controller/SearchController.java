

package com.smart.contact.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.contact.entity.Contact;
import com.smart.contact.entity.User;
import com.smart.contact.repositery.ContactRepositery;
import com.smart.contact.repositery.UserRepositery;

@Controller
public class SearchController {
	
	@Autowired
	private UserRepositery userRepositery;

	@Autowired
	private ContactRepositery contactRepositery;

	
	
	@GetMapping("/search/{q}")
	public ResponseEntity<?> searchContact(@PathVariable("q") String q, Principal principal, Model model) {

		System.out.println("i m in show contact");
		User user = this.userRepositery.findUserByUserEmail(principal.getName());
		
		  List<Contact> contactsByContactName = this.contactRepositery.findByNameContainingAndUser(q, user);
		 
		 System.out.println(contactsByContactName);
		return ResponseEntity.ok(contactsByContactName);
		
		
		
	}
}