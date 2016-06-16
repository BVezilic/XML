package daobeans.implementation;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBException;

import daobeans.generic.GenericDao;
import model.korisnik.Korisnici;

@Stateless
@Local(KorisnikDaoLocal.class)
public class KorisnikDaoBean extends GenericDao<Object, String> implements KorisnikDaoLocal {

	@Override
	public Korisnici getUsers() {
		try {
			return em.getKorisnici();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

}
