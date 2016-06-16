package daobeans.implementation;

import daobeans.generic.GenericDaoLocal;
import model.korisnik.Korisnici;

public interface KorisnikDaoLocal extends GenericDaoLocal<Object, String> {

	public Korisnici getUsers();
}
