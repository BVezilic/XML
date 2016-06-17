package daobeans.implementation;

import java.io.IOException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBException;

import daobeans.generic.GenericDao;

@Stateless
@Local(AktDaoLocal.class)
public class AktDaoBean extends GenericDao<Object, String> implements AktDaoLocal {

	@Override
	public byte[] getPDFAkt(String uri) {
		try {
			return em.getAktToPDF(uri);
		} catch (IOException | JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

}
