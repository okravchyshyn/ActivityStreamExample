package com.example.ActivityStreamExample.model;

public class GroupAction {
	
	public enum Action {
		CREATE_GROUP, UPDATE_GROUP, DELETE_GROUP
	};
	
	Long id;
	String name;
	Action action;
	
	public Action getAction() {
		return action;
	}
	public Group getGroup() {
		return new Group(id, name);
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
