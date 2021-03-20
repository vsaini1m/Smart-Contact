package com.smart.contact.repositery;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart.contact.entity.User;

@Repository
public interface UserRepositery extends CrudRepository<User, Long>{

	
	@Query("select u from User u where u.email=:email")
	public User findUserByUserEmail(@Param("email") String email);
	
	
}
