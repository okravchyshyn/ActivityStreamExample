package com.example.ActivityStreamExample.services;

import java.util.List;

import com.example.ActivityStreamExample.model.Group;

public interface GroupService {
	
	List<Group> getAllGroups();
	
	Group addNewGroup(Group group);

	Group save(Group group);

	void delete(Group group);

	Group getGroupById(Long id);

	boolean deleteGroup(Group group);

	
}
