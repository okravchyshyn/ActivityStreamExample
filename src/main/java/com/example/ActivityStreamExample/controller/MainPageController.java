package com.example.ActivityStreamExample.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import com.example.ActivityStreamExample.model.Activity;
import com.example.ActivityStreamExample.model.ActivityFeed;
import com.example.ActivityStreamExample.model.Group;
import com.example.ActivityStreamExample.model.User;
import com.example.ActivityStreamExample.services.ActivityService;
import com.example.ActivityStreamExample.services.GroupService;
import com.example.ActivityStreamExample.services.UserService;

@Controller
public class MainPageController {

	
	public MainPageController() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("MainPageController ctr");
	}
	
	@Autowired
	UserService userService;
	
	@Autowired
	ActivityService activityService;

	@Autowired
	GroupService groupService;
	
	@RequestMapping(value="/mainPage", method=RequestMethod.GET)
	public String mainPage(ModelMap model, HttpServletRequest request) {
		System.out.println("Main page");
        if(request.getUserPrincipal() != null) {
            String loginName = request.getUserPrincipal().getName();
            System.out.println("loginName : " + loginName );
            User user = userService.getUser(loginName);

            model.addAttribute("userName", user.getUsername() != null && user.getUsername().length() > 0 ? user.getUsername() : loginName );
        }
        
		
		
		return "mainPage";
	}

	@RequestMapping(value="/updateActivities", method=RequestMethod.GET)
	public @ResponseBody List<Activity> updateActivities(@RequestParam("id") Long id,
			@RequestParam("groupid") String groupid,
			ModelMap model, HttpServletRequest request) {
		System.out.println("updateActivities id=" + id + " groupid=" + groupid);
        if(request.getUserPrincipal() != null) {
            String loginName = request.getUserPrincipal().getName();
            System.out.println("loginName : "+ loginName );
            Group group = groupService.getGroupById(Long.parseLong(groupid));
            List<Activity> activities = activityService.getNewActivitiesFromId(id, group );
            return activities;
        }
		
		return null;
	}

	 	@ExceptionHandler(MethodArgumentNotValidException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ResponseBody
	    public String processValidationError(MethodArgumentNotValidException ex) {
	        BindingResult result = ex.getBindingResult();
	        List<FieldError> fieldErrors = result.getFieldErrors();
	 
	        return "Error";
	    }
	
	@RequestMapping(value="/addNewActivity", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Activity> addNewActivities(@Valid @RequestBody ActivityFeed activityFeed, ModelMap model, HttpServletRequest request) {
		System.out.println("addNewActivities");
		System.out.println("inputActivity: " + activityFeed.getActivityText());
		//List<Activity> activities = new LinkedList<Activity>();
		List<Activity> activities = new ArrayList<Activity>();
        if(request.getUserPrincipal() != null) {
            String loginName = request.getUserPrincipal().getName();
            System.out.println("loginName : " + loginName );

            User user = userService.getUser(loginName);
            Activity activity = new Activity();
            activity.setGroup( groupService.getGroupById( activityFeed.getGroupId() ) );
            activity.setText(activityFeed.getActivityText());
            activity.setUser(user);
            
            activityService.addActivity(activity);
            
            activities.add(activity);
            //return activity;
        }
        return activities;
        //return null;
	}

	@RequestMapping(value="/getGroupListForMainPage", method=RequestMethod.GET)
	public @ResponseBody List<Group> getGroupList(ModelMap model) {
		System.out.println("MainPageController::getGroupListForMainPage");
		List<Group> groups = groupService.getAllGroups();
		return groups;
	}
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(ModelMap model) {
		System.out.println("In the login method");
		
		return "redirect:mainPage.html";
	}

	
}
