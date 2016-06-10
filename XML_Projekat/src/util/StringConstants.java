package util;

public class StringConstants {

	public static String xqueryVersion = "xquery version \"1.0-ml\";";
	public static String nameSpace = "declare namespace sk = \"http://www.ftn.uns.ac.rs/skupstina\";";
	
	public static String getExecutable(OperationType ot)
	{
		String retVal = "";
		retVal += xqueryVersion;
		retVal += nameSpace;
		switch(ot)
		{
			case createDocument:
				retVal += "xdmp:document-insert(\"replace1\",replace2);";
				return retVal;
			case deleteNode:
				retVal += "xdmp:node-delete(doc(\"replace1\")replace2)";
				return retVal;
			case insertBefore:
				retVal += "xdmp:node-insert-before(doc(\"replace1\")replace2,replace3)";
				return retVal;
			case insertAfter:
				retVal += "xdmp:node-insert-after(doc(\"replace1\")replace2,replace3)";
				return retVal;
			case deleteDocument:
				retVal += "xdmp:document-delete(\"replace1\")";
				return retVal;
			case insertChild:
				retVal += "xdmp:node-insert-child(doc(\"replace1\")replace2,replace3)";
				return retVal;
			case replaceNode:
				retVal += "xdmp:node-replace(doc(\"replace1\")replace2,replace3)";
				return retVal;
			case assignCollection:
				retVal += "xdmp:document-set-collections(\"replace1\", \"replace2\")";
				return retVal;
			case allDocuments:
				retVal += "fn:doc()";
				return retVal;
			case document:
				retVal += "fn:doc(\"replace1\")";
				return retVal;
			case listCollections:
				retVal += "xdmp:document-get-collections(\"replace1\")";
				return retVal;
			case dropDatabase:
				retVal+= "for $doc in doc() return xdmp:document-delete(xdmp:node-uri($doc))";
				return retVal;
			default:
				return retVal;
		}
	}
}
