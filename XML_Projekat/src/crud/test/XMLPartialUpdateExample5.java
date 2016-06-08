package crud.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.DocumentPatchBuilder.Position;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;

import util.ConnectionUtils;
import util.ConnectionUtils.ConnectionProperties;

public class XMLPartialUpdateExample5 {

	private static DatabaseClient client;
	
	public static void run(ConnectionProperties props) throws FileNotFoundException {

		System.out.println("[INFO] " + XMLPartialUpdateExample5.class.getSimpleName());
		
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
		String docId = "data/xml/zakon_o_izvrsenju_i_obezbedjenju.xml";

		// Defining namespace mappings
		EditableNamespaceContext namespaces = new EditableNamespaceContext();
		namespaces.put("b", "http://www.ftn.uns.ac.rs/xpath/examples");
		namespaces.put("fn", "http://www.w3.org/2005/xpath-functions");
		namespaces.put("sk", "http://www.ftn.uns.ac.rs/skupstina");
		
		// Assigning namespaces to patch builder
		DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
		patchBuilder.setNamespaces(namespaces);

		// Creating an XML patch
		/*
				<b:book category="TEST">
					<b:title lang=\"en\">Test book</b:title>
					<b:author>Test Author</b:author>
					<b:year>2016</b:year>
					<b:price>59.99</b:price>
				</b:book>
		 */
		/*
		 
		 	        <sk:Stav>
                        Ovo je dodatni stav ubacen za svrhe testova
                    </sk:Stav>
		 
		 */
		
		String patch = "\t\t\t\t<sk:Stav>"+
				"\t\t\t\t\tOvo je dodatni stav ubacen za svrhe testova"+
				"\t\t\t\t</sk:Stav>";
		
		// Defining XPath context
		String contextXPath1 = "//sk:Clan[@Brojcana_oznaka=41]";
		

		// Insert fragments
		patchBuilder.insertFragment(contextXPath1, Position.BEFORE, patch);
		patchBuilder.insertFragment(contextXPath1, Position.AFTER, patch);
		
		
		DocumentPatchHandle patchHandle = patchBuilder.build();
		
		System.out.println("[INFO] Inserting nodes to \"" + docId + "\".");
		xmlManager.patch(docId, patchHandle);
		
		System.out.println("[INFO] Verify the content at: http://" + props.host + ":8000/v1/documents?database=" + props.database + "&uri=" + docId);
		
		// Release the client
		
		//replace whole clan
		
		patchBuilder = xmlManager.newPatchBuilder();
		patchBuilder.setNamespaces(namespaces);
		
		String patch2 = "\t\t\t\t<sk:Clan>"+
				"\t\t\t\t\t<sk:Stav>Ovo je dodatni stav ubacen za svrhe testova</sk:Stav>"+
				"\t\t\t\t</sk:Clan>";
		
		patchBuilder.replaceFragment(contextXPath1, patch2);
		
		// Remove fragment
		//patchBuilder.delete(contextXPath1);

		patchHandle = patchBuilder.build();
		
		System.out.println("[INFO] Replacing nodes in \"" + docId + "\".");
		xmlManager.patch(docId, patchHandle);	
		
		client.release();
		
		System.out.println("[INFO] End.");
	}
	
	public static void main(String[] args) throws IOException {
		run(ConnectionUtils.loadProperties());
	}

}
