package crud.test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.InputStreamHandle;

import util.ConnectionUtils;
import util.ConnectionUtils.ConnectionProperties;

public class XMLWriterUriTemplateExample2 {

	private static DatabaseClient client;
	
	public static void run(ConnectionProperties props) throws FileNotFoundException {
		
		System.out.println("[INFO] " + XMLWriterUriTemplateExample2.class.getSimpleName());
		
		// Initialize the database client
		if (props.database.equals("")) {
			System.out.println("[INFO] Using default database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			System.out.println("[INFO] Using \"" + props.database + "\" database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}
		
		// Create a document manager to work with XML files.
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();
		
		// Insert a document with generated URI (specifying the suffix and prefix)
		DocumentUriTemplate template = xmlManager.newDocumentUriTemplate("xml");
		template.setDirectory("/example/xquery/");
		
		// Create an input stream handle to hold XML content.
		InputStreamHandle handle = new InputStreamHandle(new FileInputStream("data/xml/zakon_o_zastiti_zemljista.xml"));
		
		// Write the document to the database
		System.out.println("[INFO] Inserting \"data/books.xml\" to \"Documents\" database.");
		DocumentDescriptor desc = xmlManager.create(template, handle);
		
		System.out.println("[INFO] Generated URI: " + desc.getUri());
		System.out.print("[INFO] Verify the content at: ");
		System.out.println("http://" + props.host + ":8000/v1/documents?database=" + props.database + "&uri=" + desc.getUri());
		
		// Release the client
		client.release();
		
		System.out.println("[INFO] End.");
	}

	public static void main(String[] args) throws IOException {
		run(ConnectionUtils.loadProperties());
	}

}
