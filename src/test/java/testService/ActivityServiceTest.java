package testService;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.ActivityStreamExample.model.Activity;
import com.example.ActivityStreamExample.model.Group;
import com.example.ActivityStreamExample.repository.ActivityRepository;
import com.example.ActivityStreamExample.services.ActivityService;
import com.example.ActivityStreamExample.services.ActivityServiceImpl;

import utility.TestUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ActivityServiceTest {
	
	final static Group group = TestUtil.createGroup();
	
	@Configuration
	static class AccountServiceTestContextConfiguration {
		@Bean
		public ActivityService accountService() {
			return new ActivityServiceImpl();
		}
		@Bean
		public ActivityRepository accountRepository() {
			return Mockito.mock(ActivityRepository.class);
		}
	}
	 
	//We Autowired the AccountService bean so that it is injected from the configuration
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ActivityRepository accountRepository;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		//public List<Activity> getNewActivitiesFromId(Long id, Group group) {
		//	return activityRepository.getNewActivitiesFromId(id, group);
		//}
		List<Activity> activities = new ArrayList();
		
		when(accountRepository.getNewActivitiesFromId(0L, group)).thenReturn(user);

		fail("Not yet implemented");
	}

}
