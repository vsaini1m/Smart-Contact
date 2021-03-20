package com.smart.contact.confg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.contact.entity.User;
import com.smart.contact.repositery.UserRepositery;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepositery userRepositery;
	
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		
		User user = this.userRepositery.findUserByUserEmail(arg0);
		
		if (user==null)
			throw new UsernameNotFoundException("User not found!");
		
		CustomUserDetails userDetails = new CustomUserDetails(user);
		
		return userDetails;
	}

}
