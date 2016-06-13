package entitymanager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.InputStreamHandle;

import util.ConnectionUtils;
import util.ConnectionUtils.ConnectionProperties;

public class EntityManager<T, ID extends Serializable> {

	private static DatabaseClient client;
	private static ConnectionProperties props;
	
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
	public void persist(T entity, String id) throws IOException
	{
		props = ConnectionUtils.loadProperties();
		
		if (props.database.equals("")) {
			System.out.println("[INFO] Using default database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			System.out.println("[INFO] Using \"" + props.database + "\" database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}
		
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();
		
		InputStreamHandle handle = new InputStreamHandle((InputStream)entity);
		
		xmlManager.write(id, handle);
		
		client.release();
		
	}
	public void delete(ID resourceId)
	{
		
	}
	public void update(T entity, ID resourceId)
	{
		
	}
}
