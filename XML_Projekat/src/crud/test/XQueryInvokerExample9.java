package crud.test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;

import util.ConnectionUtils;
import util.ConnectionUtils.ConnectionProperties;
import util.OperationType;
import util.StringConstants;

public class XQueryInvokerExample9 {

	private static DatabaseClient client;

	private static final String prefix = "data/";
	
	public static void run(ConnectionProperties props) throws IOException {
		
		System.out.println("[INFO] " + XQueryInvokerExample9.class.getSimpleName());
		
		// Initialize the database client
		if (props.database.equals("")) {
			System.out.println("[INFO] Using default database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			System.out.println("[INFO] Using \"" + props.database + "\" database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}

		// Script file which is to be invoked
		String filePath = prefix + "delete.xqy";
		
		System.out.println("[INFO] Invoking: " + filePath);
		
		// Initialize XQuery invoker object
		ServerEvaluationCall invoker = client.newServerEval();
		
		// Read the file contents into a string object
		//String query = readFile(filePath, StandardCharsets.UTF_8);
		
		String query = StringConstants.getExecutable(OperationType.deleteNode);
		query = query.replace("replace1", "/test/xml");
		query = query.replace("replace2", "//sk:Clan[@Brojcana_oznaka=41]");
		//String query = "import module namespace search = \"http://marklogic.com/appservices/search\"at \"/MarkLogic/appservices/search/search.xqy\";search:search(\"Everyday\")";
		
		// Invoke the query
		invoker.xquery(query);
		
		// Interpret the results
		EvalResultIterator response = invoker.eval();
		System.out.print("[INFO] Response: ");
		
		if (response.hasNext()) {

			for (EvalResult result : response) {
				System.out.println("\n" + result.getString());
			}
		} else { 		
			System.out.println("your query returned an empty sequence.");
		}
//		
		// Release the client
		client.release();
		
		System.out.println("[INFO] End.");
	}
	
	/**
	 * Convenience method for reading file contents into a string.
	 */
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static void main(String[] args) throws IOException {
		run(ConnectionUtils.loadProperties());
	}

}
