package rdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.FileHandle;
import com.marklogic.client.semantics.GraphManager;
import com.marklogic.client.semantics.RDFMimeTypes;

import util.ConnectionUtils;
import util.ConnectionUtils.ConnectionProperties;

/**
 * Inicijalizacija RDF store-a.
 * 
 * Primenom GRDDL transformacije vrši se ekstrakcija RDF tripleta iz XML 
 * dokumenta "data/xml/contacts.xml" i inicijalizacija imenovanog grafa
 * "example/sparql/metadata" ekstrahovanim tripletima.
 * 
 */
public class RDFInitializationExample1 {

	private static DatabaseClient client;
	
	private static final String SPARQL_NAMED_GRAPH_URI = "example/sparql/metadata";
	
	public static void run(ConnectionProperties props) throws IOException, SAXException, TransformerException {
		
		System.out.println("[INFO] " + RDFInitializationExample1.class.getSimpleName());
		
		// Initialize the database client
		if (props.database.equals("")) {
			System.out.println("[INFO] Using default database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			System.out.println("[INFO] Using \"" + props.database + "\" database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}
		
		// Create a document manager to work with XML files.
		GraphManager graphManager = client.newGraphManager();
		
		// Set the default media type (RDF/XML)
		graphManager.setDefaultMimetype(RDFMimeTypes.RDFXML);
		
		// Referencing XML file with RDF data in attributes
		String xmlFilePath = "data/rdf/zakon_o_izvrsenju_i_obezbedjenju.xml";
		
		String rdfFilePath = "data/rdf/zakon.rdf";
		
		// Automatic extraction of RDF triples from XML file
		MetadataExtractor metadataExtractor = new MetadataExtractor();
		
		metadataExtractor.extractMetadata(
				new FileInputStream(new File(xmlFilePath)), 
				new FileOutputStream(new File(rdfFilePath)));
				
		// A handle to hold the RDF content.
		FileHandle rdfFileHandle =
				new FileHandle(new File(rdfFilePath))
				.withMimetype(RDFMimeTypes.RDFXML);
		
		// Writing the named graph
		System.out.println("[INFO] Overwriting triples to a named graph \"" + SPARQL_NAMED_GRAPH_URI + "\".");
		graphManager.write(SPARQL_NAMED_GRAPH_URI, rdfFileHandle);
		
		
		// Read the triples from the named graph
		System.out.println();
		System.out.println("[INFO] Retrieving triples from RDF store.");
		System.out.println("[INFO] Using \"" + SPARQL_NAMED_GRAPH_URI + "\" named graph.");

		// Define a DOM handle instance to hold the results 
		DOMHandle domHandle = new DOMHandle();
		
		// Retrieve RDF triplets in format (RDF/XML) other than default
		graphManager.read(SPARQL_NAMED_GRAPH_URI, domHandle).withMimetype(RDFMimeTypes.RDFXML);
		
		// Serialize document to the standard output stream
		System.out.println("[INFO] Rendering triples as \"application/rdf+xml\".");
		DOMUtil.transform(domHandle.get(), System.out);
		
		// Release the client
		client.release();
		
		System.out.println("[INFO] End.");
	}
	
	public static void main(String[] args) throws Exception {
		run(ConnectionUtils.loadProperties());
	}

}
