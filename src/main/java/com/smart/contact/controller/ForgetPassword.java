package com.smart.contact.controller;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.contact.repositery.ContactRepositery;
import com.smart.contact.repositery.UserRepositery;

@Controller
public class ForgetPassword {
	

	@Autowired
	private UserRepositery userRepositery;

	@Autowired
	private ContactRepositery contactRepositery;

	
	@GetMapping("/forget-password")
	public String forget() {
		return "forget_password";
	}
	
	
	
	
	@PostMapping("/forget-password")
	public String forgetSet(@RequestParam("email") String email,Model m) {
		
		
		//if email not in database
		if (this.userRepositery.findUserByUserEmail(email)==null) {
			m.addAttribute("error","Invalid Email, Please cheack your email!");
			return "forget_password";
			
		}
		
		
		
		int otp = new Random().nextInt(999999);
		
		//mail code
		
		
		String messege="Your Otp is <h2>"+otp+"</h2>";
		String subject="smart contact manager";
		String from="vinitsainijavadeveloper@gmail.com";
		String password="mpic5sih";
		
		
		System.out.println("preparing to send");
		
		
		if(sendEmail(messege,subject,email,from,otp,password))
			return "verify_otp";
		else
			m.addAttribute("error","We are unable to Send OTP, Please try Again later!");
			
		
		
		//end mail code
		
		return "forget_password";
	}

	
	private boolean sendEmail(String messege, String subject, String email, String from,int otp,String password) {
		//Variable for gmail
				String host="smtp.gmail.com";
				
				//get the system properties
				Properties properties = System.getProperties();
				System.out.println("PROPERTIES "+properties);
				
				//setting important information to properties object
				
				//host set
				properties.put("mail.smtp.host", host);
				properties.put("mail.smtp.port","465");
				properties.put("mail.smtp.ssl.enable","true");
				properties.put("mail.smtp.auth","true");
				
				//Step 1: to get the session object..
				Session session=Session.getInstance(properties, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {				
						return new PasswordAuthentication(from, password);
					}
					
					
					
				});
				
				session.setDebug(true);
				
				//Step 2 : compose the message [text,multi media]
				MimeMessage m = new MimeMessage(session);
				
				try {
				
				//from email
				m.setFrom(from);
				
				//adding recipient to message
				m.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				
				//adding subject to message
				m.setSubject(subject);
			
				
				//adding text to message
				m.setText(messege);
				
				//send 
				
				//Step 3 : send the message using Transport class
				Transport.send(m);
				
				System.out.println("Sent success...................");
				
				
				}catch (Exception e) {
				
					e.printStackTrace();
					return false;
				}
				
				
				
				return true;
		
	}


	@PostMapping("/verify-otp")
	public String verifyotp(@RequestParam("otp") String otp) {
		
		
		
		return "verify_otp";
	}

	
	
	
	
}
