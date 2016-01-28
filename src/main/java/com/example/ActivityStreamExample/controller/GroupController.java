package com.example.ActivityStreamExample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ActivityStreamExample.model.Group;
import com.example.ActivityStreamExample.model.GroupAction;
import com.example.ActivityStreamExample.services.GroupService;

@Controller
public class GroupController {

	@Autowired
	GroupService groupService;
	
	@RequestMapping(value="/groupsPage", method=RequestMethod.GET)
	public String logout(ModelMap model) {
		System.out.println("groupPage");
		return "groupsPage";
	}
	
	@RequestMapping(value="/getGroupList", method=RequestMethod.GET)
	public @ResponseBody List<Group> getGroupList(ModelMap model) {
		System.out.println("getGroupList");
		List<Group> groups = groupService.getAllGroups();
		return groups;
	}


	@RequestMapping(value="/changeGroup", method=RequestMethod.POST)
	public @ResponseBody List<Group> updateGroup(@RequestBody GroupAction group) {
		System.out.println("changeGroup");
		System.out.println("GroupName: " + group.getAction());
		System.out.println("GroupName: " + group.getName());
		System.out.println("GroupId: " + group.getId());

		switch(group.getAction()) {
			case CREATE_GROUP:
				groupService.addNewGroup(group.getGroup());
				break;
			case UPDATE_GROUP:
				groupService.save(group.getGroup());
				break;
			case DELETE_GROUP:
				groupService.delete(group.getGroup());
				break;
		}
		
		List<Group> groups = groupService.getAllGroups();
		return groups;
	}

	
}
