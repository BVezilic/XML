package entitymanager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;

import conversion.XMLMarshaller;
import model.akt.Akt;
import transformations.XSLFOTransformator;
import util.ConnectionUtils;
import util.ConnectionUtils.ConnectionProperties;
import util.SearchResultsUtil;

public class EntityManager<T, ID extends Serializable> {

	private static DatabaseClient client;
	private static ConnectionProperties props;
	
	public EntityManager()
	{
		super();
	}
	
	public List<Object> findByKeyWord(String keyword) throws IOException
	{
		List<Object> arl = new ArrayList<Object>();
		props = ConnectionUtils.loadProperties();
		
		if (props.database.equals("")) {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}
		
		QueryManager queryManager = client.newQueryManager();
		
		// Query definition is used to specify Google-style query string
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();
		
		// Set the criteria
		queryDefinition.setCriteria(keyword);
		
		SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());
		
		// Serialize search results to the standard output
		MatchDocumentSummary matches[] = results.getMatchResults();

		MatchDocumentSummary result;
		
		EvalResultIterator response = null;
		
		for (int i = 0; i < matches.length; i++) {
			result = matches[i];
			
			ServerEvaluationCall invoker = client.newServerEval();
			String query = "declare namespace sk = \"http://www.ftn.uns.ac.rs/skupstina\";"
						   + "let $x := fn:doc(\"###\")/sk:Akt/sk:Naslov return fn:string($x)";
			query = query.replace("###",result.getUri());
			invoker.xquery(query);
			response = invoker.eval();
			String name = "";
			if (response.hasNext()) {
				for (EvalResult rs : response) {
					System.out.println(rs.getString());
					name = rs.getString();
				}
			}
			arl.add(new SearchResultsUtil(name,result.getUri(),""));
		}
		
		// Release the client
		client.release();
		return arl;
	}
	public List<Object> findByMetaData(String metadata)
	{
		return null;
	}
	
	public Object findById(String id) throws IOException, JAXBException, UnsupportedEncodingException
	{
		props = ConnectionUtils.loadProperties();
		
		if (props.database.equals("")) {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();
		JAXBContext context = JAXBContext.newInstance("model.akt");
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		JAXBHandle content = new JAXBHandle(context);
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		xmlManager.read(id, metadata, content);
		Akt doc = (Akt)content.get();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		marshaller.marshal(doc, baos);
		InputStream isFromFirstData = new ByteArrayInputStream(baos.toByteArray());
		String retVal = XSLFOTransformator.aktToHTMLStream(isFromFirstData);
		// Release the client
		client.release();
		return retVal;
	}
	
	public List<Object> findAll() throws IOException
	{
		List<Object> arl = new ArrayList<Object>();
		props = ConnectionUtils.loadProperties();
		
		if (props.database.equals("")) {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}
		
		QueryManager queryManager = client.newQueryManager();
		
		// Query definition is used to specify Google-style query string
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();
		
		// Set the criteria
		String criteria = "";
		queryDefinition.setCriteria(criteria);
		
		SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());
		
		// Serialize search results to the standard output
		MatchDocumentSummary matches[] = results.getMatchResults();

		MatchDocumentSummary result;
		
		EvalResultIterator response = null;
		
		for (int i = 0; i < matches.length; i++) {
			result = matches[i];
			
			ServerEvaluationCall invoker = client.newServerEval();
			String query = "declare namespace sk = \"http://www.ftn.uns.ac.rs/skupstina\";"
						   + "let $x := fn:doc(\"###\")/sk:Akt/sk:Naslov return fn:string($x)";
			query = query.replace("###",result.getUri());
			invoker.xquery(query);
			response = invoker.eval();
			String name = "";
			if (response.hasNext()) {
				for (EvalResult rs : response) {
					System.out.println(rs.getString());
					name = rs.getString();
				}
			}
			arl.add(new SearchResultsUtil(name,result.getUri(),""));
		}
		
		// Release the client
		client.release();
		return arl;
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
		
		InputStreamHandle handle = new InputStreamHandle(XMLMarshaller.objectoToXML(entity));
		
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add("u_proceduri");
		
		xmlManager.write(id ,metadata ,handle);
		
		client.release();
		
	}
	public void delete(String resourceId)
	{
		
	}
	public void update(Object entity, String resourceId)
	{
		
	}
}
