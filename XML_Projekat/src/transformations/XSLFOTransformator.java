package transformations;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

import net.sf.saxon.TransformerFactoryImpl;

public class XSLFOTransformator {

	public XSLFOTransformator() {
		super();
	}

	@Deprecated
	public static void aktToHTML(String xmlName) {
		try {
	        TransformerFactory factory = TransformerFactory.newInstance();
	        Source xslt = new StreamSource(new File("WebContent/data/xslt/akt.xsl"));
	        Transformer transformer = factory.newTransformer(xslt);
	        Source text = new StreamSource(new File("WebContent/data/xml/"+xmlName+".xml"));
	        transformer.transform(text, new StreamResult(new File("WebContent/gen/html/"+xmlName+".html")));
	        System.out.println("Uspesno zavrsena transformacija "+xmlName+ " u html, na putanji: WebContent/gen/"+xmlName+".html");
		} catch (TransformerConfigurationException e) {			
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	@Deprecated
	public static void amandmanToHTML(String xmlName) {
		try {
	        TransformerFactory factory = TransformerFactory.newInstance();
	        Source xslt = new StreamSource(new File("data/xslt/amandman.xsl"));
	        Transformer transformer = factory.newTransformer(xslt);
	        Source text = new StreamSource(new File("data/xml/"+xmlName+".xml"));
	        transformer.transform(text, new StreamResult(new File("WebContent/gen/html/"+xmlName+".html")));
	        System.out.println("Uspesno zavrsena transformacija "+xmlName+ " u html, na putanji: WebContent/gen/"+xmlName+".html");
		} catch (TransformerConfigurationException e) {			
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static String dokumentToHTMLStream(InputStream xmlName, String tip) throws UnsupportedEncodingException {
		try {
	        TransformerFactory factory = TransformerFactory.newInstance();
	        Source xslt = new StreamSource(new File("../standalone/deployments/XML_Projekat.war/data/xslt/"+tip+".xsl"));
	        Transformer transformer = factory.newTransformer(xslt);
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        Source text = new StreamSource(xmlName);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        transformer.transform(text, new StreamResult(baos));
	        System.out.println("Uspesno zavrsena transformacija u html");
	        return baos.toString("UTF-8");
		} catch (TransformerConfigurationException e) {			
			e.printStackTrace();
			return "error";
		} catch (TransformerException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@Deprecated
	public static String amandmanToHTMLStream(InputStream xmlName) throws UnsupportedEncodingException {
		try {
	        TransformerFactory factory = TransformerFactory.newInstance();
	        Source xslt = new StreamSource(new File("../standalone/deployments/XML_Projekat.war/data/xslt/amandman.xsl"));
	        Transformer transformer = factory.newTransformer(xslt);
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        Source text = new StreamSource(xmlName);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        transformer.transform(text, new StreamResult(baos));
	        System.out.println("Uspesno zavrsena transformacija u html");
	        return baos.toString("UTF-8");
		} catch (TransformerConfigurationException e) {			
			e.printStackTrace();
			return "error";
		} catch (TransformerException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	public static byte[] aktToPDFStream(InputStream xmlName) {	
		FopFactory fopFactory;
		TransformerFactory transformerFactory;
		try {
			// Initialize FOP factory object
			fopFactory = FopFactory.newInstance(new File("../standalone/deployments/XML_Projekat.war/config/fop.xconf"));
			// Setup the XSLT transformer factory
			transformerFactory = new TransformerFactoryImpl();
			// Point to the XSL-FO file
			File xsltFile = new File("../standalone/deployments/XML_Projekat.war/data/xslt/akt_fo.xsl");
			// Create transformation source
			StreamSource transformSource = new StreamSource(xsltFile);
			// Initialize the transformation subject
			StreamSource source = new StreamSource(xmlName);
			// Initialize user agent needed for the transformation
			FOUserAgent userAgent = fopFactory.newFOUserAgent();
			// Create the output stream to store the results
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			// Initialize the XSL-FO transformer object
			Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
			// Construct FOP instance with desired output format
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
			// Resulting SAX events 
			Result res = new SAXResult(fop.getDefaultHandler());
			// Start XSLT transformation and FOP processing
			xslFoTransformer.transform(source, res);
			// Generate PDF file
//			File pdfFile = new File("WebContent/gen/pdf/"+xmlName+".pdf");
//			OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
//			out.write(outStream.toByteArray());
//			out.close();
			System.out.println("Uspesno zavrsena transformacija u pdf");
			return outStream.toByteArray();
		} catch (Exception e) {
			System.out.println("greska prilikom generisanja pdf fajla");
			return null;
		}	
	}
	
	public static void aktToPDF(String xmlName) {	
		FopFactory fopFactory;
		TransformerFactory transformerFactory;
		try {
			// Initialize FOP factory object
			fopFactory = FopFactory.newInstance(new File("src/fop.xconf"));
			// Setup the XSLT transformer factory
			transformerFactory = new TransformerFactoryImpl();
			// Point to the XSL-FO file
			File xsltFile = new File("WebContent/data/xslt/akt_fo.xsl");
			// Create transformation source
			StreamSource transformSource = new StreamSource(xsltFile);
			// Initialize the transformation subject
			StreamSource source = new StreamSource(new File("WebContent/data/xml/"+xmlName+".xml"));
			// Initialize user agent needed for the transformation
			FOUserAgent userAgent = fopFactory.newFOUserAgent();
			// Create the output stream to store the results
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			// Initialize the XSL-FO transformer object
			Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
			// Construct FOP instance with desired output format
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
			// Resulting SAX events 
			Result res = new SAXResult(fop.getDefaultHandler());
			// Start XSLT transformation and FOP processing
			xslFoTransformer.transform(source, res);
			// Generate PDF file
			File pdfFile = new File("WebContent/gen/pdf/"+xmlName+".pdf");
			OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
			out.write(outStream.toByteArray());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (FOPException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} 
			
		System.out.println("Uspesno zavrsena transformacija "+xmlName+ " u pdf, na putanji: WebContent/gen/"+xmlName+".pdf");
	}
	public static void main(String[] args) {
//		XSLFOTransformator.aktToHTML("zakon_o_zastiti_zemljista");
//		XSLFOTransformator.aktToHTML("zakon_o_izvrsenju_i_obezbedjenju");
//		XSLFOTransformator.aktToHTML("zakon_o_policiji");
//		XSLFOTransformator.aktToHTML("zakon_o_ozakonjenju_objekta");
		
		XSLFOTransformator.aktToPDF("zakon_o_zastiti_zemljista");
		XSLFOTransformator.aktToPDF("zakon_o_izvrsenju_i_obezbedjenju");
		XSLFOTransformator.aktToPDF("zakon_o_policiji");
		XSLFOTransformator.aktToPDF("zakon_o_ozakonjenju_objekta");
	}
}
