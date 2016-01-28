package com.example.ActivityStreamExample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ActivityStreamExample.model.User;
import com.example.ActivityStreamExample.services.UserService;

@Controller
public class AddNewUserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/addNewUser", method=RequestMethod.GET)
	public String addNewUserGet(ModelMap model) {
		System.out.println("Add new user page");
		
		return "addNewUser";
	}

    @RequestMapping(value = "/addNewUser", method = RequestMethod.POST)
    public /*@ResponseBody*/ String addNewUserPost(
    		@RequestParam(value="login", required=true) String login,
    		@RequestParam(value="password", required=true) String password,
    		@RequestParam(value="confirmPassword", required=true) String confirmPassword,
    		@RequestParam(value="username", required=true) String username,
           Model model) {
    	System.out.println("addNewUser post method" );
    	System.out.println("Login:" + login);
    	System.out.println("Password:" + password);
    	System.out.println("ConfirmPassword:" + confirmPassword);
    	System.out.println("Username:" + username);
   
    	User user = new User(login, password, username);
    	userService.addNewUser(user);
    	
    	return "redirect:login.html";
 }
	
	
}
