package daobeans.generic;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBException;

public interface GenericDaoLocal<T, ID extends Serializable> {
	
	public T findById(String id, String tip) throws JAXBException, IOException;

	public List<T> findAll() throws IOException, JAXBException;
	
	public List<Object> findByKeyWord(String keyword) throws IOException;
	
	public List<Object> findByMetaData(String dateFrom, String dateTo) throws IOException;

	public T persist(T entity, String id) throws JAXBException, IOException;

	public T merge(String id) throws IOException, JAXBException;

	public void remove(ID id) throws IOException;
	
	public InputStream findBy(String xQuery, boolean wrap) throws IOException;
	
	public void changeCollection(String id, String[] Collections) throws IOException;
	
	public List<Object> findAllAmandmani() throws IOException;
}
