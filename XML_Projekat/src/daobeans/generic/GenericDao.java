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
	
	public GenericDao()
	{
		super();
		em = new EntityManager<T,ID>();
	}
	
	@Override
	public T findById(String id, String tip) throws JAXBException, IOException {	
		return (T)em.findById(id, tip);
	}

	@Override
	public List<T> findAll() throws IOException, JAXBException {
		return (List<T>)em.findAll();
	}
	
	@Override
	public List<Object> findByMetaData(String dateFrom, String dateTo) throws IOException
	{
		return em.findByMetaData(dateFrom, dateTo);
	}
	
	@Override
	public List<Object> findByKeyWord(String keyword) throws IOException
	{
		return em.findByKeyWord(keyword);
	}

	@Override
	public T persist(T entity, String id) throws JAXBException, IOException {
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
		try {
			em.update(id);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (T)id;
	}

	@Override
	public void remove(String id) throws IOException {
		em.delete(id);
		
	}

	@Override
	public InputStream findBy(String xQuery, boolean wrap) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void changeCollection(String id, String[] Collections) throws IOException
	{		
		em.changeCollection(id, Collections);
	}
	@Override
	public List<Object> findAllAmandmani() throws IOException
	{
		return em.findAllAmandmani();
	}

}
