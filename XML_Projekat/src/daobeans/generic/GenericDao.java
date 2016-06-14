package daobeans.generic;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import entitymanager.EntityManager;

public abstract class GenericDao <T, ID extends Serializable> implements GenericDaoLocal<T, ID> {

	protected EntityManager<T,ID> em; 
	
	@Override
	public T findById(String id) throws JAXBException, IOException {
		em = new EntityManager<T,ID>();
		return (T)em.findById(id);
	}

	@Override
	public List<T> findAll() throws IOException, JAXBException {
		em = new EntityManager<T,ID>();
		return (List<T>)em.findAll();
	}
	
	@Override
	public List<Object> findByMetaData(String metadata) throws IOException
	{
		em = new EntityManager<T,ID>();
		return em.findByMetaData(metadata);
	}
	
	@Override
	public List<Object> findByKeyWord(String keyword) throws IOException
	{
		em = new EntityManager<T,ID>();
		return em.findByKeyWord(keyword);
	}

	@Override
	public T persist(T entity, String id) throws JAXBException, IOException {
		em = new EntityManager<T,ID>();
		try {
			em.persist(entity, id);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	@Override
	public T merge(String id) throws IOException, JAXBException {
		em = new EntityManager<T,ID>();
		try {
			em.update(id);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (T)id;
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
