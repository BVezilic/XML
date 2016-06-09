package crud.test;

import java.io.FileInputStream;
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
import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;

import util.ConnectionUtils;
import util.ConnectionUtils.ConnectionProperties;

public class SingleFieldSearchExample6 {

	private static DatabaseClient client;
	
	private static TransformerFactory transformerFactory;
	
	private static final int NUMBER_OF_SAMPLES = 10;
	
	private static final String COLLECTION = "/ftn/bookshelf/test";
	
	static {
		transformerFactory = TransformerFactory.newInstance();
	}
	
	public static void run(ConnectionProperties props) throws FileNotFoundException {
		
		System.out.println("[INFO] " + SingleFieldSearchExample6.class.getSimpleName());
		
		// Initialize the database client
		if (props.database.equals("")) {
			System.out.println("[INFO] Using default database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			System.out.println("[INFO] Using \"" + props.database + "\" database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}
		
		// Populate database
		populateDatabase(NUMBER_OF_SAMPLES);
		
		// Initialize query manager
		QueryManager queryManager = client.newQueryManager();
		
		// Query definition is used to specify Google-style query string
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();
		
		// Set the criteria
		String criteria = "angularjs OR test AND \"Kurt Cagle\"";
		queryDefinition.setCriteria(criteria);
		
		// Search within a specific collection
		queryDefinition.setCollections(COLLECTION);
		
		// Perform search
		DOMHandle results = queryManager.search(queryDefinition, new DOMHandle());
		
		// Serialize search results to the standard output
		Document resultsDocument = results.get();
		System.out.println("[INFO] Showing the results for: " + criteria + "\n");
		transform(resultsDocument, System.out);
		
		// Release the client
		client.release();
		
		System.out.println("[INFO] End.");
	}
	
	private static void populateDatabase(int numberOfSamples) throws FileNotFoundException {

		XMLDocumentManager xmlManager = client.newXMLDocumentManager();
		
		// Generate URI for each document
		DocumentUriTemplate template = xmlManager.newDocumentUriTemplate("xml");
		template.setDirectory("/example/xquery/search/");
		
		// Assign each document to the collection
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add(COLLECTION);

		InputStreamHandle content;
		
		for (int i = 0; i < numberOfSamples; i++) {
		
			// Create an input stream handle to hold XML content.
			if (i % 3 == 0)
				content = new InputStreamHandle(new FileInputStream("data/books_modified.xml"));
			else
				content = new InputStreamHandle(new FileInputStream("data/books.xml"));
			
			// Write XML document to the database
			DocumentDescriptor desc = xmlManager.create(template, metadata, content);
			System.out.println("[INFO] Populating database with: \"" + desc.getUri() + "\".");
			
		}
		System.out.println();
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