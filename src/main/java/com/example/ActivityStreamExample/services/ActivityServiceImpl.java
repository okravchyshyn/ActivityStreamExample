package com.example.ActivityStreamExample.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ActivityStreamExample.model.Activity;
import com.example.ActivityStreamExample.model.Group;
import com.example.ActivityStreamExample.repository.ActivityRepository;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	ActivityRepository activityRepository;
	
	public List<Activity> getAllActivities() {
		Sort sortByIdDesc = new Sort(Sort.Direction.DESC, "id");
		return activityRepository.findAll(sortByIdDesc);
	}

	public List<Activity> getNewActivitiesFromId(Long id, Group group) {
		return activityRepository.getNewActivitiesFromId(id, group);
	}

	public Activity addActivity(Activity actitivity) {
		return activityRepository.saveAndFlush(actitivity);
	}

}
