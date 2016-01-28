package com.example.ActivityStreamExample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ActivityStreamExample.model.Activity;
import com.example.ActivityStreamExample.model.Group;

@Repository("activityRepository")
public interface ActivityRepository extends JpaRepository<Activity, Long> {

	@Query("Select a from Activity a where a.id > ?1 and a.group = ?2 order by a.id ") 
	List<Activity> getNewActivitiesFromId(Long id, Group groupid);

	
}
