package testController;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import com.example.ActivityStreamExample.model.Activity;
import com.example.ActivityStreamExample.model.ActivityFeed;
import com.example.ActivityStreamExample.model.Group;
import com.example.ActivityStreamExample.model.User;
import com.example.ActivityStreamExample.services.ActivityService;
import com.example.ActivityStreamExample.services.GroupService;
import com.example.ActivityStreamExample.services.UserService;

import utility.TestUtil;

import org.springframework.security.core.userdetails.memory.InMemoryDaoImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.mock.web.MockHttpSession;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/security-configMockTest.xml", "file:src/main/webapp/WEB-INF/config/servlet-config.xml","classpath:jpaContextMockTest.xml"})
@WebAppConfiguration
public class MainPageControllerTest {

	
	final static MockHttpSession session = TestUtil.buildHttpSession();
	final static User user = TestUtil.createUser();
	final static Group group = TestUtil.createGroup();
	
    private MockMvc mockMvc;
    
    //SecurityContext mockSecurityContext;
    
    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
	UserService userServiceMock;
	
	@Autowired
	ActivityService activityServiceMock;

	@Autowired
	GroupService groupServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() throws Exception {
		
		System.out.println("@Before setUp");
		
	      Mockito.reset(userServiceMock);
	      Mockito.reset(activityServiceMock);
	      Mockito.reset(groupServiceMock);
	      
	      mockMvc = MockMvcBuilders
	    		  	.webAppContextSetup(webApplicationContext)
	    		  	.addFilters(springSecurityFilterChain)
	    		  	.build();
		

	      
//			HttpSession session = mockMvc.perform(post("/login-process").param("j_username", "user1").param("j_password", "user1"))
//					.andExpect(status().is(HttpStatus.FOUND.value()))
//					.andExpect(redirectedUrl("/"))
//					.andReturn()
//					.getRequest()
//					.getSession();		
	      
	}

	//@Test
	public void test() {
		try {
			when(userServiceMock.getUser("user")).thenReturn(user);

			ResultActions rs = mockMvc.perform(get("/mainPage.html").session(session));
			rs.andExpect(status().isOk())
			.andExpect( model().attribute("userName", is("username")))
			.andExpect(forwardedUrl("/WEB-INF/jsp/mainPage.jsp"));
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			
		}
	}
	
	@SuppressWarnings("serial")
	//@Test
	public void testGetActivities() {
		List<Activity> activities = new ArrayList<Activity>() {{
			add(TestUtil.ActivityBuilder(1L, "msg1"));
			add(TestUtil.ActivityBuilder(2L, "msg2"));
		}};
		
		when(groupServiceMock.getGroupById(1L)).thenReturn(group);
		when(activityServiceMock.getNewActivitiesFromId(0L, group)).thenReturn(activities);
		when(userServiceMock.getUser("user")).thenReturn(user);
		
		ResultActions rs;
		try {
			rs = mockMvc.perform(get("/updateActivities.json?id=0&groupid=1").session(session));
			rs.andExpect(status().isOk())
            .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].text", is("msg1")))
            .andExpect(jsonPath("$[1].id", is(2)))
            .andExpect(jsonPath("$[1].text", is("msg2")))
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void testPostAddActivity() {
		ActivityFeed activity = new ActivityFeed() {{
			setActivityText("new activity");
			setGroupId(1L);
		}};

		when(userServiceMock.getUser("user")).thenReturn(user);
		
		try {
				ResultActions rs = mockMvc.perform(post("/addNewActivity.json").session(session)
			            .contentType(TestUtil.APPLICATION_JSON_UTF8)
			            .content(TestUtil.convertObjectToJsonBytes(activity)));
				rs.andExpect(status().isOk())
	            .andExpect(jsonPath("$", hasSize(1)))
	            .andExpect(jsonPath("$[0].id").value(null))
	            .andExpect(jsonPath("$[0].text", is("new activity")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIncorectActivityTextSize() {
		ActivityFeed activity = new ActivityFeed() {{
			setActivityText("1");
			setGroupId(1L);
		}};

		when(userServiceMock.getUser("user")).thenReturn(user);
		
		try {
				ResultActions rs = mockMvc.perform(post("/addNewActivity.json").session(session)
			            .contentType(TestUtil.APPLICATION_JSON_UTF8)
			            .content(TestUtil.convertObjectToJsonBytes(activity)));
				rs.andExpect(status().is(400));
				rs.andExpect(content().string("Error"));
	            //.andExpect(jsonPath("$", hasSize(1)))
	            //.andExpect(jsonPath("$[0].id").value(null))
	            //.andExpect(jsonPath("$[0].text", is("new activity")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
