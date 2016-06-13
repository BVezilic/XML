package conversion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import model.akt.Akt;
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
}
