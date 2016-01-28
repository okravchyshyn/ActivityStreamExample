package com.example.ActivityStreamExample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ActivityStreamExample.model.User;

@Repository("userRepository")
public interface UserRepository  extends JpaRepository<User, Long> {

	@Query("Select u from User u where u.login = ?1") 
	User getUser(String login);
	/*
	User addNewUser(User user);
	User updateUser(User user);
*/
}
