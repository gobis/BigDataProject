package com.saitech;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

@Path("/billdetail")
public class BillDetailService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response PersistBillDetails(BillDetail billDetail) {
		// String result = "Hi, hava a nice day"; // customerBo.getMsg();

		sessionFactory = createSessionFactory();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.save(billDetail);
		session.getTransaction().commit();

		session.close();

		return Response.status(200).entity("Ok").build();
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
