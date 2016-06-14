package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.akt.Akt;
import model.akt.Clan;
import model.amandman.Amandman;
import model.korisnik.Korisnici;
import model.korisnik.Korisnik;

@Stateless
@Path("/services")
public class Rest {

	
	@EJB
	DataTest data;
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Test";
	}

	@GET
	@Path("/login/{username}/{password}")
	
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik loginUser(@PathParam("username") String username, @PathParam("password") String password) {

		Korisnici korisnici = new Korisnici();
		
		Korisnik k1 = new Korisnik();
		Korisnik k2 = new Korisnik();
		Korisnik k3 = new Korisnik();
		
		k1.setKorisnickoIme("user1");
		k2.setKorisnickoIme("2");
		k3.setKorisnickoIme("user3");

		k1.setLozinka("pass1");
		k2.setLozinka("2");
		k3.setLozinka("pass3");
		
		k1.setUloga("gradjanin");
		k2.setUloga("predsednikVlade");
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
	@Path("/vote/{za}/{uzdrzano}/{protiv}/{amandman}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> vote(@PathParam("za") String za, @PathParam("uzdrzano") String uzdrzano,
			@PathParam("protiv") String protiv, @PathParam("amandman") String amandman) {
		try {
			Integer z = Integer.parseInt(za);
			Integer u = Integer.parseInt(uzdrzano);
			Integer p = Integer.parseInt(protiv);
			
			if ( z > u + p){
				for (Amandman a : data.getPredlozeniAmandmani()) {
					if (a.getOperacija().equals(amandman)){
						data.getAmandmani().add(a);
						data.getPredlozeniAmandmani().remove(a);
						break;
					}
				}
			}else
			{
				for (Amandman a : data.getPredlozeniAmandmani()) {
					if (a.getOperacija().equals(amandman)){
						data.getAmandmani().add(a);
						data.getPredlozeniAmandmani().remove(a);
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return getNasloviPredlozenihAmandmana();
	}
	
	@GET
	@Path("/akti")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getNasloviAkata(){
		List<Akt> akti = data.getAkti();
		System.out.println("Akti: " + data.getAkti().toString());
		List<String> naslovi = new ArrayList<String>();
		for (Akt akt : akti) {
			naslovi.add(akt.getNaslov());
		}
		System.out.println("Naslovi: " + naslovi);
		return naslovi;
	}
	
	@GET
	@Path("/akt/{naslov}")
	@Produces(MediaType.APPLICATION_JSON)
	public Clan getClanByNaslovAkta(@PathParam("naslov") String naslov){
		List<Akt> akti = data.getAkti();
		for (Akt akt : akti) {
			if (akt.getNaslov().equals(naslov)){
				return akt.getClan().get(0);
			}
		}
		
		return null;
	}
	
	@GET
	@Path("/akt/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> filterAkta(@PathParam("filter") String filter){
		System.out.println("Filtriram po: " + filter);
		return getNasloviAkata();
	}

	@GET
	@Path("/amandmani")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getNasloviAmandmana(){
		List<Amandman> amandman = data.getAmandmani();
		List<String> naslovi = new ArrayList<String>();
		for (Amandman a : amandman) {
			naslovi.add(a.getOperacija());
		}
		return naslovi;
	}
	
	@GET
	@Path("/predlozeniAmandmani")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getNasloviPredlozenihAmandmana(){
		List<Amandman> amandman = data.getPredlozeniAmandmani();
		List<String> naslovi = new ArrayList<String>();
		for (Amandman a : amandman) {
			naslovi.add(a.getOperacija());
		}
		return naslovi;
	}
	
	@GET
	@Path("/amandman/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> filterAmandmana(@PathParam("filter") String filter){
		System.out.println("Filtriram po: " + filter);
		return getNasloviAmandmana();
	}
	
	@GET
	@Path("/predlozeniAmandman/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> filterPredlozenihAmandmana(@PathParam("filter") String filter){
		System.out.println("Filtriram po: " + filter);
		return getNasloviPredlozenihAmandmana();
	}
	
	@GET
	@Path("/odabraniAkti")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getNasloviOdabranihAkata(){
		List<Akt> akti = data.getOdabraniAkti();
		List<String> naslovi = new ArrayList<String>();
		for (Akt akt : akti) {
			naslovi.add(akt.getNaslov());
		}
		return naslovi;
	}
	
	
	
	
	
	@PUT
	@Path("/odabraniAkti/add/{naslov}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> dodajUOdabranjeAkte(@PathParam("naslov") String naslov){
		List<Akt> akti = data.getAkti();
		for (Akt akt : akti) {
			if (akt.getNaslov().equals(naslov)){
				data.getOdabraniAkti().add(akt);
			}
		}
		return getNasloviOdabranihAkata();
	}
	
	@DELETE
	@Path("/odabraniAkti/remove/{naslov}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> obrisiOdabraniAkt(@PathParam("naslov") String naslov){
		List<Akt> akti = data.getAkti();
		for (Akt akt : akti) {
			if (akt.getNaslov().equals(naslov)){
				data.getOdabraniAkti().remove(akt);
				break;
			}
		}
		return getNasloviOdabranihAkata();
	}
	
	@PUT
	@Path("/amandman/add/{naslovAkta}/{naslovAmandmana}/{content}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> dodajNoviAmandman(@PathParam("naslovAkta") String naslovAkta, @PathParam("naslovAmandmana") String naslovAmandmana, @PathParam("content") String content){
		
		Amandman a = new Amandman();
		a.setOperacija(naslovAkta);
		data.getPredlozeniAmandmani().add(a);
		
		System.out.println("Content dodatog amandmana: " + content);
		
		return getNasloviPredlozenihAmandmana();
	}
	
	
}
