package com.example.ActivityStreamExample.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ActivityStreamExample.model.Group;
import com.example.ActivityStreamExample.repository.GroupRepository;

@Service("groupService")
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;
	
	public List<Group> getAllGroups() {
		
		/*
		List<Group> groups = new ArrayList<Group>();
		groups.add(new Group(1L, "Group#1"));
		groups.add(new Group(2L, "Group#2"));
		groups.add(new Group(3L, "Group#3"));
		groups.add(new Group(4L, "Group#4"));
		groups.add(new Group(5L, "Group#5"));
		groups.add(new Group(6L, "Group#6"));
		groups.add(new Group(7L, "Group#7"));
		*/
		return groupRepository.findAll();
	}

	public Group addNewGroup(Group group) {
		
		return groupRepository.saveAndFlush(group);
	}

	public Group save(Group group) {
		return groupRepository.save(group);
	}

	public void delete(Group group) {
		groupRepository.delete(group);
	}

	public Group getGroupById(Long id) {
		// TODO Auto-generated method stub
		return groupRepository.findOne(id);
	}

	public boolean deleteGroup(Group group) {
		// TODO Auto-generated method stub
		return false;
	}

}
