package crud.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StructuredQueryBuilder;
import com.marklogic.client.query.StructuredQueryDefinition;
import com.marklogic.client.util.EditableNamespaceContext;

import util.ConnectionUtils;
import util.ConnectionUtils.ConnectionProperties;

public class StructuredQuerySearchExample7 {

	private static DatabaseClient client;
	
	private static TransformerFactory transformerFactory;
	
	private static final String COLLECTION = "/ftn/bookshelf/test";
	
	static {
		transformerFactory = TransformerFactory.newInstance();
	}
	
	public static void run(ConnectionProperties props) throws FileNotFoundException {
		
		System.out.println("[INFO] " + StructuredQuerySearchExample7.class.getSimpleName());
		
		// Initialize the database client
		if (props.database.equals("")) {
			System.out.println("[INFO] Using default database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			System.out.println("[INFO] Using \"" + props.database + "\" database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}
		
		// Initialize query manager
		QueryManager queryManager = client.newQueryManager();
		
		// Defining namespace mappings
		EditableNamespaceContext namespaces = new EditableNamespaceContext();
		namespaces.put("b", "http://www.ftn.uns.ac.rs/xpath/examples");

		// Initialize structured query builder
		StructuredQueryBuilder queryBuilder = queryManager.newStructuredQueryBuilder();
		queryBuilder.setNamespaces(namespaces);
		
		// Instantiate query definition
		StructuredQueryDefinition queryDefinition =
				queryBuilder.and(queryBuilder.term("test"),
				queryBuilder.or(queryBuilder.term("brian", "ford")),
				queryBuilder.collection(COLLECTION));

		// Perform search
		DOMHandle results = queryManager.search(queryDefinition, new DOMHandle());
		
		// Serialize search results to the standard output
		Document resultsDocument = results.get();
		transform(resultsDocument, System.out);
		
		// Release the client
		client.release();
		
		System.out.println("[INFO] End.");
	}
	
	/**
	 * Serializes DOM tree to an arbitrary OutputStream.
	 *
	 * @param node a node to be serialized
	 * @param out an output stream to write the serialized 
	 * DOM representation to
	 * 
	 */
	private static void transform(Node node, OutputStream out) {
		try {

			// Kreiranje instance objekta zaduzenog za serijalizaciju DOM modela
			Transformer transformer = transformerFactory.newTransformer();

			// Indentacija serijalizovanog izlaza
			transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			// Nad "source" objektom (DOM stablo) vrši se transformacija
			DOMSource source = new DOMSource(node);

			// Rezultujuæi stream (argument metode) 
			StreamResult result = new StreamResult(out);

			// Poziv metode koja vrši opisanu transformaciju
			transformer.transform(source, result);
			
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		run(ConnectionUtils.loadProperties());
	}

}