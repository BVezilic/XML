package conversion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import model.akt.Akt;
import model.amandman.Amandman;
import util.NSPrefixMapper;

public class XMLMarshaller {

	public static InputStream objectoToXML(Object toConvert)
	{
		InputStream retVal = null;
		try{
			if(toConvert instanceof Akt)
			{
				JAXBContext context = JAXBContext.newInstance("model.akt");
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				marshaller.marshal(toConvert, baos);
				retVal = new ByteArrayInputStream(baos.toByteArray());
				return retVal;
			}else
			{
				JAXBContext context = JAXBContext.newInstance("model.amandman");
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				marshaller.marshal(toConvert, baos);
				retVal = new ByteArrayInputStream(baos.toByteArray());
				return retVal;
			}
		}catch(Exception e)
		{
			return null;
		}
	}
	
	public static void objectToFile(Object toConvert)
	{
		try{
			if(toConvert instanceof Akt)
			{
				JAXBContext context = JAXBContext.newInstance("model.akt");
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
				marshaller.marshal(toConvert, new FileOutputStream(new File("temp.xml")));
			}else
			{
				JAXBContext context = JAXBContext.newInstance("model.amandman");
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
				marshaller.marshal(toConvert, new FileOutputStream(new File("temp.xml")));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String amandmanConversion(Amandman amd) throws JAXBException, XMLStreamException
	{
		JAXBContext context = JAXBContext.newInstance("model.amandman");
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
        switch(amd.getSadrzaj().getTipSadrzaja())
        {
        case "clan":
        	marshaller.marshal(amd.getSadrzaj().getClan(), writer);
        	break;
        case "stav":
        	marshaller.marshal(amd.getSadrzaj().getStav(), writer);
        	break;
        case "tacka":
        	marshaller.marshal(amd.getSadrzaj().getTacka(), writer);
        	break;
        case "podtacka":
        	marshaller.marshal(amd.getSadrzaj().getPodtacka(), writer);
        	break;
        case "alineja":
        	marshaller.marshal(amd.getSadrzaj().getAlineja(), writer);
        	break;
        }
        return str.toString();
	    
	}
}
