package com.example.ActivityStreamExample.model;

import javax.validation.constraints.Size;

public class ActivityFeed {
	
	@Size(min=3, max=200)//min=3 for testing purpose, e.g. try to input string less than 3 
	private String activityText;

	private Long groupId;
	
	
	public String getActivityText() {
		return activityText;
	}
	public void setActivityText(String activityText) {
		this.activityText = activityText;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	
}
