package util;

public class SearchResultsUtil {

	private String documentName;
	private String documentURI;
	private String documentCollection;
	
	public SearchResultsUtil(String documentName, String documentURI, String documentCollection) {
		super();
		this.documentName = documentName;
		this.documentURI = documentURI;
		this.documentCollection = documentCollection;
	}
	public SearchResultsUtil() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDocumentURI() {
		return documentURI;
	}
	public void setDocumentURI(String documentURI) {
		this.documentURI = documentURI;
	}
	public String getDocumentCollection() {
		return documentCollection;
	}
	public void setDocumentCollection(String documentCollection) {
		this.documentCollection = documentCollection;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentCollection == null) ? 0 : documentCollection.hashCode());
		result = prime * result + ((documentName == null) ? 0 : documentName.hashCode());
		result = prime * result + ((documentURI == null) ? 0 : documentURI.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchResultsUtil other = (SearchResultsUtil) obj;
		if (documentCollection == null) {
			if (other.documentCollection != null)
				return false;
		} else if (!documentCollection.equals(other.documentCollection))
			return false;
		if (documentName == null) {
			if (other.documentName != null)
				return false;
		} else if (!documentName.equals(other.documentName))
			return false;
		if (documentURI == null) {
			if (other.documentURI != null)
				return false;
		} else if (!documentURI.equals(other.documentURI))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SearchResultsUtil [documentName=" + documentName + ", documentURI=" + documentURI
				+ ", documentCollection=" + documentCollection + "]";
	}
	
	
}
