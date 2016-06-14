package conversion;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.amandman.Amandman;

public class XMLUnmarshaller {

	public static Object XMLtoObject(InputStream toUnmarshall) throws JAXBException
	{
		JAXBContext context = JAXBContext.newInstance("model.amandman");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Amandman amandman = (Amandman) unmarshaller.unmarshal(toUnmarshall);
		return amandman;
	}
}
