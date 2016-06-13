package jaxbtest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;

import example2.ObjectFactory;
import example2.OdsekType;
import example2.OdsekType.Studenti;
import example2.PolozenIspit;
import example2.Student;
import model.akt.Akt;
import model.akt.Stav;
import model.amandman.Amandman;
import transformations.XSLFOTransformator;
import util.ConnectionUtils;
import util.ConnectionUtils.ConnectionProperties;
import util.NSPrefixMapper;
import util.OperationType;
import util.StringConstants;

public class Example2Marshalling {
	
	private DatabaseClient client;
	
	public void test(ConnectionProperties props) throws Exception {
		try {
			System.out.println("[INFO] Example 2: JAXB unmarshalling/marshalling.\n");
			
			// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("model.amandman");
			
			// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();
			//SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			//Schema schema = schemaFactory.newSchema(new File("./data/xsd/amandman.xsd"));
            
			// Podešavanje unmarshaller-a za XML schema validaciju
			//unmarshaller.setSchema(schema);
			//zakon_o_izvrsenju_i_obezbedjenju.xml
			//Akt amandman = (Akt) unmarshaller.unmarshal(new File("./data/xml/zakon_o_izvrsenju_i_obezbedjenju.xml"));
			
			
			
			Amandman amandman = (Amandman) unmarshaller.unmarshal(new File("./data/xml/amandman_zakona_o_izvrsenju.xml"));
			
			System.out.println(amandman.getKontekst());
			System.out.println(amandman.getOperacija());
			System.out.println(amandman.getSadrzaj().getStav().getContent().get(0));
			
			if (props.database.equals("")) {
				System.out.println("[INFO] Using default database.");
				client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
			} else {
				System.out.println("[INFO] Using \"" + props.database + "\" database.");
				client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
			}
			
			XMLDocumentManager xmlManager = client.newXMLDocumentManager();
			
			Marshaller marshaller = context.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
			marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			
			StringWriter str = new StringWriter();
			XMLOutputFactory output = XMLOutputFactory.newInstance();
			XMLStreamWriter writer = output.createXMLStreamWriter(str);
		         writer.setNamespaceContext(new NamespaceContext() {
		            public Iterator getPrefixes(String namespaceURI) {
		                return null;
		            }

		            public String getPrefix(String namespaceURI) {
		                return "sk";
		            }

		            public String getNamespaceURI(String prefix) {
		                return null;
		            }
		        });
			QName qName = new QName("http://www.ftn.uns.ac.rs/skupstina", "Stav");
			JAXBElement<Stav> sdr = new JAXBElement<Stav>(qName, Stav.class, amandman.getSadrzaj().getStav());
			// Umesto System.out-a, može se koristiti FileOutputStream
			marshaller.marshal(sdr, writer);
			
			System.out.println(str.toString());
		
			
			String docId = "/test/xml";
//			EditableNamespaceContext namespaces = new EditableNamespaceContext();
//			namespaces.put("sk", "http://www.ftn.uns.ac.rs/skupstina");
//			
//			DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
//			patchBuilder.setNamespaces(namespaces);
//			
//			patchBuilder.insertFragment(amandman.getKontekst(),Position.LAST_CHILD,str.toString());
//			
//			DocumentPatchHandle patchHandle = patchBuilder.build();
//			
//			xmlManager.patch(docId, patchHandle);
			String query = StringConstants.getExecutable(OperationType.insertBefore);
			query = query.replace("replace1", docId);
			query = query.replace("replace2",amandman.getKontekst().getValue() );
			query = query.replace("replace3", "<sk:Clan Brojcana_oznaka=\"40\" Naziv=\"Врсте извршних исправа\"><sk:Stav>Ovo je neki jebeni stav</sk:Stav></sk:Clan>");
			
			System.out.println(query);
			ServerEvaluationCall invoker = client.newServerEval();
			invoker.xquery(query);
			
			EvalResultIterator response = invoker.eval();
			System.out.print("[INFO] Response: ");
			
			if (response.hasNext()) {

				for (EvalResult result : response) {
					System.out.println("\n" + result.getString());
				}
			} else { 		
				System.out.println("your query returned an empty sequence.");
			}
			
			JAXBHandle content = new JAXBHandle(context);
			
			// A metadata handle for metadata retrieval
			DocumentMetadataHandle metadata = new DocumentMetadataHandle();
			
			xmlManager.read(docId, metadata, content);
			
			// Retrieving a document node form DOM handle.
			Akt doc = (Akt)content.get();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			marshaller.marshal(doc, baos);
			InputStream isFromFirstData = new ByteArrayInputStream(baos.toByteArray());
			XSLFOTransformator.aktToHTMLStream(isFromFirstData);
			// Release the client
			client.release();
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	private OdsekType createOdsek(String id, String naziv) {

		// Eksplicitno instanciranje OdsekType klase
		OdsekType odsek = new OdsekType();
		odsek.setId(id);
		odsek.setNaziv(naziv);

		// Generišu se studenti
		odsek.setStudenti(createStudenti());
		
		return odsek;
	}
	
	private Studenti createStudenti() {

		// Instanciranje Studenti klase posredstvom ObjectFactory-a
		ObjectFactory factory = new ObjectFactory();
		Studenti studenti = factory.createOdsekTypeStudenti();
		
		// Generiše se novi student
		studenti.getStudent().add(createStudent(12345, "Tijana", "Novkovic"));
		
		return studenti;
	}
	
	private Student createStudent(int brojIndeksa, String ime, String prezime) {
		
		ObjectFactory factory = new ObjectFactory();
		Student student = factory.createStudent();
		student.setBrojIndeksa(brojIndeksa);
		student.setIme(ime);
		student.setPrezime(prezime);
		
		// Generiše položeni ispit
		student.getPolozenIspit().add(createPolozenIspit("Dizajn", "Stevan Simic", 10));
		
		return student;
	}
	
	private PolozenIspit createPolozenIspit(String predmet, String nastavnik, int ocena) {
		try {
			
			ObjectFactory factory = new ObjectFactory();
			PolozenIspit polozenIspit = factory.createPolozenIspit();
			
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			
			polozenIspit.setDatum(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			polozenIspit.setNastavnik(nastavnik);
			polozenIspit.setOcena((short) ocena);
			polozenIspit.setPredmet(predmet);
			
			return polozenIspit;
			
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
    public static void main( String[] args ) throws Exception {
    	Example2Marshalling test = new Example2Marshalling();
    	test.test(ConnectionUtils.loadProperties());
    }
}
