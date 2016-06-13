package rest;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/services")
public class Rest {

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Test";
	}

	@POST
	@Path("/loginUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean loginUser(/* User u */) {

		int temp = 1;
		if (temp == 1/* pronadjes usera u bazi sa istim username i passwordom */) {
			return true;
		} else {
			return false;
		}
	}

	@GET
	@Path("/vote/{za}/{uzdrzano}/{protiv}")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean vote(@PathParam("za") String za, @PathParam("uzdrzano") String uzdrzano,
			@PathParam("protiv") String protiv) {
		try {
			Integer z = Integer.parseInt(za);
			Integer u = Integer.parseInt(uzdrzano);
			Integer p = Integer.parseInt(protiv);
			
			if ( z > (z + u + p) / 2){
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
