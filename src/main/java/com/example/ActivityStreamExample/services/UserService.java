package com.example.ActivityStreamExample.services;

import com.example.ActivityStreamExample.model.User;

public interface UserService {
	User getUser(String login);
	User addNewUser(User user);
	User updateUser(User user);

}
