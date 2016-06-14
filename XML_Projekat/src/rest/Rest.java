package rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

import daobeans.implementation.AktDaoLocal;
import model.akt.Akt;
import model.korisnik.Korisnici;
import model.korisnik.Korisnik;
import util.StringConstants;
import validation.XMLValidator;

@Stateless
@Path("/services")
public class Rest {

	@EJB
	AktDaoLocal adl;
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Test";
	}
	
	@GET
	@Path("/findAll")
	@Produces(MediaType.TEXT_PLAIN)
	public List<Object> findAll() throws IOException, JAXBException {
		return adl.findAll();
	}
	
	@GET
	@Path("/metaSearch")
	@Produces(MediaType.TEXT_PLAIN)
	public String metaSearch(@QueryParam("dateFrom")String dateFrom, @QueryParam("dateTo")String dateTo) throws IOException, JAXBException {
		System.out.println(dateFrom + " | " + dateTo);
		return null;
	}
	
	@POST
	@Path("/singleFieldSearch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object> singleFieldSearch(String keywords)
	{
		List<Object> retVal = null;
		try {
			retVal = new ArrayList<Object>(adl.findByKeyWord(keywords));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
	}
	
	@GET
	@Path("/getDocument")
	@Produces(MediaType.TEXT_HTML)
	public String getDocument(@QueryParam("uri")String uri) throws IOException, JAXBException {
		System.out.println("OK " + uri);
		//System.out.println((String)adl.findById(uri));
		return (String)adl.findById(uri);
	}
	
	@POST
	@Path("/persist")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean persist(String document)
	{
		Object retVal;
		if((retVal = XMLValidator.validateXML("akt", document)) == null)
			System.out.println("NO");
		else
		{
			if(retVal instanceof Akt)
			{
				System.out.println("AKT " + ((Akt)retVal).getNaslov());
				try {
					adl.persist(((Akt)retVal), StringConstants.formatName(((Akt)retVal).getNaslov()));
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else
				System.out.println("AMANDMAN");
		}
		return false;
	}
	
	@GET
	@Path("/login/{username}/{password}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_XML)
	public Korisnik loginUser(@PathParam("username") String username, @PathParam("password") String password) {

		Korisnici korisnici = new Korisnici();
		
		Korisnik k1 = new Korisnik();
		Korisnik k2 = new Korisnik();
		Korisnik k3 = new Korisnik();
		
		k1.setKorisnickoIme("user1");
		k2.setKorisnickoIme("user2");
		k3.setKorisnickoIme("user3");

		k1.setLozinka("pass1");
		k2.setLozinka("pass2");
		k3.setLozinka("pass3");
		
		k1.setUloga("obican");
		k2.setUloga("specijalan");
		k3.setUloga("najbolji");

		
		korisnici.getKorisnik().add(k1);
		korisnici.getKorisnik().add(k2);
		korisnici.getKorisnik().add(k3);
		
		
		for (Korisnik  k : korisnici.getKorisnik()) {
			if (k.getKorisnickoIme().equals(username) && k.getLozinka().equals(password)){
				return k;
			}
		}
		
		return null;
		
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
