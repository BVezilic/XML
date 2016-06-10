package searchtest;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.MatchSnippet;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;

import util.ConnectionUtils;
import util.ConnectionUtils.ConnectionProperties;

public class ProccessingSearchResultsExample8 {

	private static DatabaseClient client;
	
	private static final String COLLECTION = "/ftn/bookshelf/test";
	
	public static void run(ConnectionProperties props) throws FileNotFoundException {
		
		System.out.println("[INFO] " + ProccessingSearchResultsExample8.class.getSimpleName());
		
		// Initialize the database client
		if (props.database.equals("")) {
			System.out.println("[INFO] Using default database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			System.out.println("[INFO] Using \"" + props.database + "\" database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}
		
		// Initialize query manager
		QueryManager queryManager = client.newQueryManager();
		
		// Query definition is used to specify Google-style query string
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();
		
		// Set the criteria
		String criteria = "";
		queryDefinition.setCriteria(criteria);
		
		// Search within a specific collection
		//queryDefinition.setCollections(COLLECTION);
		
		// Perform search
		SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());
		
		// Serialize search results to the standard output
		MatchDocumentSummary matches[] = results.getMatchResults();
		System.out.println("[INFO] NUM MTC " + matches.length);
		System.out.println("[INFO] Showing the results for: " + criteria + "\n");

		MatchDocumentSummary result;
		MatchLocation locations[];
		String text;
		
		/**
		 * sranje
		 */
		
		EvalResultIterator response = null;
		/**
		 * sranje
		 */
		
		for (int i = 0; i < matches.length; i++) {
			result = matches[i];
			
			System.out.println((i+1) + ". RESULT DETAILS: ");
			System.out.println("Result URI: " + result.getUri());
			
			/**
			 * sranje
			 */
			ServerEvaluationCall invoker = client.newServerEval();
			String query = "declare namespace b = \"http://www.ftn.uns.ac.rs/xpath/examples\";"
						   + "let $x := fn:doc(\"###\")/b:bookstore/b:bsname return fn:string($x)";
			query = query.replace("###",result.getUri());
			invoker.xquery(query);
			response = invoker.eval();
			if (response.hasNext()) {
				for (EvalResult rs : response) {
					System.out.println(rs.getString());
				}
			} else { 		
				System.out.println("your query returned an empty sequence.");
			}
			/**
			 * sranje
			 */
			
			
			locations = result.getMatchLocations();
			System.out.println("Document locations matched: " + locations.length + "\n");

			for (MatchLocation location : locations) {
				
				System.out.print(" - ");
				for (MatchSnippet snippet : location.getSnippets()) {
					text = snippet.getText().trim();
					if (!text.equals("")) {
						System.out.print(snippet.isHighlighted()? text.toUpperCase() : text);
						System.out.print(" ");
					}
				}
				System.out.println("\n - Match location XPath: " + location.getPath());
				System.out.println();
			}
			
			System.out.println();
		}
		
		// Release the client
		client.release();
		
		System.out.println("[INFO] End.");
	}
	
	
	public static void main(String[] args) throws IOException {
		run(ConnectionUtils.loadProperties());
	}

}