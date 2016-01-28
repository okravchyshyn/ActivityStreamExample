package com.example.ActivityStreamExample.services;

import java.util.List;

import com.example.ActivityStreamExample.model.Activity;
import com.example.ActivityStreamExample.model.Group;

public interface ActivityService {
	
	List<Activity> getAllActivities();
	List<Activity> getNewActivitiesFromId(Long id, Group group);
	Activity addActivity(Activity actitivity);
	
}
