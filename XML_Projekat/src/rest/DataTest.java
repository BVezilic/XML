package rest;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import model.akt.Akt;
import model.akt.Clan;
import model.amandman.Amandman;
import model.amandman.Sadrzaj;

@Startup
@Singleton
public class DataTest {

	private ArrayList<Amandman> amandmani = new ArrayList<Amandman>();
	private ArrayList<Akt> akti = new ArrayList<Akt>();
	private ArrayList<Amandman> predlozeniAmandmani = new ArrayList<Amandman>();
	private ArrayList<Akt> odabraniAkti = new ArrayList<Akt>();
	
	@PostConstruct
	void init(){
		
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
		
		akti.add(ak1);
		akti.add(ak2);
		
		
		
		System.out.println("Syso u PostConstruct: " + akti.toString());
	}

	public ArrayList<Amandman> getAmandmani() {
		return amandmani;
	}

	public void setAmandmani(ArrayList<Amandman> amandmani) {
		this.amandmani = amandmani;
	}

	public ArrayList<Akt> getAkti() {
		return akti;
	}

	public void setAkti(ArrayList<Akt> akti) {
		this.akti = akti;
	}

	public ArrayList<Amandman> getPredlozeniAmandmani() {
		return predlozeniAmandmani;
	}

	public void setPredlozeniAmandmani(ArrayList<Amandman> predlozeniAmandmani) {
		this.predlozeniAmandmani = predlozeniAmandmani;
	}

	public ArrayList<Akt> getOdabraniAkti() {
		return odabraniAkti;
	}

	public void setOdabraniAkti(ArrayList<Akt> odabraniAkti) {
		this.odabraniAkti = odabraniAkti;
	}
	
	
}
