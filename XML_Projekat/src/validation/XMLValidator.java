package validation;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import model.akt.Akt;
import model.amandman.Amandman;

public class XMLValidator {

	public static Object validateXML(String tipDokumenta, String document)
	{
		Object retVal = null;
		try {
			JAXBContext context = JAXBContext.newInstance("model."+tipDokumenta);
			
			// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// XML schema validacija
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = null;
			
	        switch(tipDokumenta)
	        {
		        case "akt":
		        	schema = schemaFactory.newSchema(new File("../standalone/deployments/XML_Projekat.war/data/xsd/akt.xsd"));
		        	unmarshaller.setSchema(schema);
		        	retVal = (Akt) unmarshaller.unmarshal(new ByteArrayInputStream(document.getBytes(StandardCharsets.UTF_8)));
		        	break;
		        case "amandman":
		        	schema = schemaFactory.newSchema(new File("../standalone/deployments/XML_Projekat.war/data/xsd/amandman.xsd"));
		        	unmarshaller.setSchema(schema);
		        	retVal = (Amandman) unmarshaller.unmarshal(new ByteArrayInputStream(document.getBytes(StandardCharsets.UTF_8)));
		        	break;
	        }
		} catch (JAXBException e) {
			System.out.println("Neispravan XML, JAXBException");
			return null;
		} catch (SAXException e) {
			System.out.println("Neispravan XML, SAXException");
			return null;
		}
		return retVal;
	}
	
}
