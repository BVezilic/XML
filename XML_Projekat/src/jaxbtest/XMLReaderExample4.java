package jaxbtest;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;

import example2.Fakultet;
import example2.OdsekType;
import example2.PolozenIspit;
import example2.Student;
import util.ConnectionUtils;
import util.ConnectionUtils.ConnectionProperties;

public class XMLReaderExample4 {

	private static DatabaseClient client;
	
	private static TransformerFactory transformerFactory;
	
	static {
		transformerFactory = TransformerFactory.newInstance();
	}
	
	public static void run(ConnectionProperties props) throws FileNotFoundException {
		
		System.out.println("[INFO] " + XMLReaderExample4.class.getSimpleName());
		
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

		// A handle to receive the document's content.
		//DOMHandle content = new DOMHandle();
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance("example2");
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JAXBHandle content = new JAXBHandle(context);
		
		// A metadata handle for metadata retrieval
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		
		// A document URI identifier. 
		String docId = "/test";
		
		// Write the document to the database
		System.out.println("[INFO] Retrieving \"" + docId + "\" from "
				+ (props.database.equals("") ? "default" : props.database)
				+ " database.");
		
		xmlManager.read(docId, metadata, content);
		
		// Retrieving a document node form DOM handle.
		Fakultet doc = (Fakultet)content.get();
		/*
		 * A collection defines a set of documents in the database. You can set
		 * documents to be in any number of collections either at the time the
		 * document is created or by updating it.
		 * 
		 */
		
		// Reading metadata
		System.out.println("[INFO] Assigned collections: " + metadata.getCollections());

		// Serializing DOM tree to standard output.
		System.out.println("[INFO] Retrieved content:");
		//transform(doc, System.out);
		System.out.println("NAZIV FAKULTETA: " + doc.getNaziv());
		for(OdsekType t : doc.getOdsek())
		{
			System.out.println("NAZIV ODSEKA: " + t.getNaziv());
			System.out.println("ID ODSEKA: " + t.getId());
			for(Student s: t.getStudenti().getStudent())
			{
				System.out.println("STUDENT: " + s.getBrojIndeksa() + " " + s.getIme() + " " + s.getPrezime());
				for(PolozenIspit p:s.getPolozenIspit())
				{
					System.out.println("POLOZENI ISPIT" + p.getPredmet() + " " + p.getNastavnik() + " " + p.getOcena());
				}
			}
		}
		content.set(doc);
		xmlManager.write("/sranje",content);
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
