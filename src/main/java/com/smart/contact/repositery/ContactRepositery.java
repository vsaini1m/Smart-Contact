package com.smart.contact.repositery;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.smart.contact.entity.Contact;
import com.smart.contact.entity.User;

@Repository
public interface ContactRepositery extends CrudRepository<Contact, Long>{

	
	
	Page<Contact> findContactsByUser(User user, Pageable p);
	
	
	


	List<Contact> findByNameContainingAndUser(String name,User user);
}
