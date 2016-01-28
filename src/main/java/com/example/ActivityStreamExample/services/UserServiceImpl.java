package com.example.ActivityStreamExample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ActivityStreamExample.model.User;
import com.example.ActivityStreamExample.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	public User getUser(String login) {
		return userRepository.getUser(login);
	}

	@Transactional
	public User addNewUser(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Transactional
	public User updateUser(User user) {
		return userRepository.save(user);
	}

}
