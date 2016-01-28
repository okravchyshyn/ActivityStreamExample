package com.example.ActivityStreamExample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="activities")
public class Activity {
	@Id 
	@GeneratedValue
	private Long id;

	@ManyToOne
	@NotNull
	private User user;

	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	@ManyToOne
	@NotNull
	private Group group;

	@Size(min=3, max=200)//min=3 for testing purpose, e.g. try to input string less than 3 
	private String text;


	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public User getUser() {
		return user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
