package com.saitech;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/greet")
public class restExample {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendGreetMsg() {
		String result = "Hi, hava a nice day"; 
		return Response.status(200).entity(result).build();
	}

}
