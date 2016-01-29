package com.example.ActivityStreamExample.model;

public class UserRegistration {

	private String login;
	private String password;
	private String passwordConf;
	private String userName;
	
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public String getPasswordConf() {
		return passwordConf;
	}
	public String getUserName() {
		return userName;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPasswordConf(String passwordConf) {
		this.passwordConf = passwordConf;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
