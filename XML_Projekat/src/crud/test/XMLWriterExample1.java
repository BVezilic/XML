package crud.test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.InputStreamHandle;

import util.ConnectionUtils;
import util.ConnectionUtils.ConnectionProperties;

public class XMLWriterExample1 {

private static DatabaseClient client;
	
	public static void run(ConnectionProperties props) throws FileNotFoundException {
		
		System.out.println("[INFO] " + XMLWriterExample1.class.getSimpleName());
		
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
		
		// Define a URI value for a document.
		String docId = "zakon_o_izvrsenju_i_obezbedjenju.xml";
		String testDocId = "/example/test/books.xml";
		
		// Create an input stream handle to hold XML content.
		//String exampleString = "<asd><dsa></dsa></asd>";
		//InputStream stream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));
		InputStreamHandle handle = new InputStreamHandle(new FileInputStream("data/xml/zakon_o_izvrsenju_i_obezbedjenju.xml"));
		//InputStreamHandle handle = new InputStreamHandle(stream);
		
		
		// Write the document to the database
		System.out.println("[INFO] Inserting \"" + docId + "\" to \"" + props.database + "\" database.");
		xmlManager.write(docId, handle);
		
		// Write the same document with a new id 
		System.out.println("[INFO] Inserting \"" + testDocId + "\" to \"" + props.database + "\" database.");
		handle = new InputStreamHandle(new FileInputStream("data/xml/zakon_o_izvrsenju_i_obezbedjenju.xml"));
		xmlManager.write(testDocId, handle);
		
		// Document deletion
		System.out.println("[INFO] Removing \"" + testDocId + "\" from \"" + props.database + "\" database.");
		xmlManager.delete(testDocId);
		
		System.out.println("[INFO] Verify the content at: http://" + props.host + ":8000/v1/documents?database=" + props.database + "&uri=" + docId);
		// Release the client
		client.release();
		
		System.out.println("[INFO] End.");
	}

	public static void main(String[] args) throws IOException {
		run(ConnectionUtils.loadProperties());
	}

}
