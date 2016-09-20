package com.saitech;

//import javax.jws.WebService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;

/*import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import java.util.List;

import javax.ws.rs.core.Response;*/


@Controller
@RequestMapping("/greet")
public class Welcome {
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public String Greet(){
		
		
		UserDetails user1 = new UserDetails();
		user1.setUserId(45);
		user1.setUsername("First user");
		
		sessionFactory = createSessionFactory();
		
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(user1);
		session.getTransaction().commit();
		
		session.close();
		
		
		return "Hi , Check table";
	}
	
	
	
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	@SuppressWarnings("deprecation")
	public static SessionFactory createSessionFactory() {
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    
	    serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
	            configuration.getProperties()).build();
	    
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    
	    return sessionFactory;
	}

}
