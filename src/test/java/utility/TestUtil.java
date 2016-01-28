package utility;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.example.ActivityStreamExample.model.Activity;
import com.example.ActivityStreamExample.model.Group;
import com.example.ActivityStreamExample.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
 
public class TestUtil {
 
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
 
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
 
    public static String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();
 
        for (int index = 0; index < length; index++) {
            builder.append("a");
        }
 
        return builder.toString();
    }
    
	public static Activity ActivityBuilder(final Long i,final String msg) {
		return new Activity() {{
			setId(i);
			setText(msg);
		}};
	}

	@SuppressWarnings("serial")
	public static MockHttpSession buildHttpSession() {
  	    return new MockHttpSession() {{

  	    	UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("user"
  	    			, "password"
  	    			, new ArrayList<GrantedAuthority>() {{
  	    					add(new SimpleGrantedAuthority("ROLE_USER"));
  	    			}}
  	    		);

  		    SecurityContext securityContext = SecurityContextHolder.getContext();
  		    securityContext.setAuthentication(token);

  	    	setAttribute(
    	                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
    	                securityContext);
  	    }};
	}
	
	public static User createUser() {
		return  new User() {{
			setId(1);
			setLogin("user");
			setPassword("password");
			setUsername("username");
			
		}};
	}
	
	public static Group createGroup() {
		return new Group() {{
			setId(1L);
			setName("group");
		}};
	}
   
}