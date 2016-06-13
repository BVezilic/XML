package daobeans.generic;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBException;

import entitymanager.EntityManager;

public abstract class GenericDao <T, ID extends Serializable> implements GenericDaoLocal<T, ID> {

	public EntityManager<T, ID> em;
	
	@Override
	public T findById(ID id) throws JAXBException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAll() throws IOException, JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T persist(T entity, String id) throws JAXBException, IOException {
		em.persist(entity, id);
		return entity;
	}

	@Override
	public T merge(T entity, String id) throws IOException, JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(ID id) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStream findBy(String xQuery, boolean wrap) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
