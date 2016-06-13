package rest;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import model.akt.Akt;
import model.akt.Clan;
import model.amandman.Amandman;
import model.amandman.Sadrzaj;

@Singleton
@Startup
public class DataTest {

	public ArrayList<Amandman> amandmani = new ArrayList<Amandman>();
	public ArrayList<Akt> akti = new ArrayList<Akt>();
	public ArrayList<Amandman> predlozeniAmandmani = new ArrayList<Amandman>();
	public ArrayList<Akt> odabraniAkti = new ArrayList<Akt>();
	
	@PostConstruct
	public void setup(){
		Amandman a1 = new Amandman();
		Amandman a2 = new Amandman();
		
		Clan c1 = new Clan();
		c1.setNaziv("prvi clan");
		Clan c2 = new Clan();
		c2.setNaziv("drugi clan");
		
		Sadrzaj s1 = new Sadrzaj();
		Sadrzaj s2 = new Sadrzaj();
		
		s1.setClan(c1);
		s2.setClan(c2);
		
		a1.setSadrzaj(s1);
		a1.setOperacija("test operacija 1");
		
		a2.setSadrzaj(s2);
		a2.setOperacija("test operacija 2");
		
		Amandman a3 = new Amandman();
		Clan c3 = new Clan();
		c3.setNaziv("treci clan");
		
		Sadrzaj s3 = new Sadrzaj();
		
		s3.setClan(c3);
		a3.setSadrzaj(s3);
		
		Akt ak1 = new Akt();
		Akt ak2 = new Akt();
		
		ak1.setNaslov("Naslov akt 1");
		ak2.setNaslov("Naslov akt 2");
		
		ak1.getClan().add(c1);
		ak2.getClan().add(c2);
		
	}
}
