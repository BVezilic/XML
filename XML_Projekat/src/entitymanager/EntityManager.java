package entitymanager;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

public class EntityManager<T, ID extends Serializable> {

	public T find(ID resourceId)
	{
		return null;
	}
	public List<T> findAll()
	{
		return null;
	}
	public InputStream executeQuery(String xQuery, boolean wrap)
	{
		return null;
	}
	public void persist(T entity, Long id)
	{
		
	}
	public void delete(ID resourceId)
	{
		
	}
	public void update(T entity, ID resourceId)
	{
		
	}
}
