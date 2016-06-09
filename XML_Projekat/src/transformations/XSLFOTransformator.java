package transformations;

import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XSLFOTransformator {

	public XSLFOTransformator() {
		super();
	}

	public static void aktToHTML(String xmlName) {
		try {
	        TransformerFactory factory = TransformerFactory.newInstance();
	        Source xslt = new StreamSource(new File("data/xslt/akt.xsl"));
	        Transformer transformer = factory.newTransformer(xslt);
	        Source text = new StreamSource(new File("data/xml/"+xmlName+".xml"));
	        transformer.transform(text, new StreamResult(new File("WebContent/gen/"+xmlName+".html")));
	        System.out.println("Uspesno zavrsena transformacija u html");
		} catch (TransformerConfigurationException e) {			
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		XSLFOTransformator.aktToHTML("zakon_o_zastiti_zemljista");
		XSLFOTransformator.aktToHTML("zakon_o_izvrsenju_i_obezbedjenju");
	}
}
