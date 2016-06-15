package rest;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import model.korisnik.Korisnik;

@Singleton
public class LoginDB {
	
	public Korisnik user;

	@PostConstruct
	void init()
	{
		user = new Korisnik();
		user.setUloga("odbornik");
	}
	
	public Korisnik getUser() {
		return user;
	}

	public void setUser(Korisnik user) {
		this.user = user;
	}
	
}
