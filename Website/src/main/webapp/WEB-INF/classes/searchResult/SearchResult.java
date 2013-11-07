package classes.searchResult;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.lucene.queryparser.classic.ParseException;

import classes.searchResult.PageHandler;

public class SearchResult extends SimpleTagSupport {

	// Persistent Fields:
	private String queryString;
	private Vector<String> results;

	// Constructors:
	public SearchResult(String queryString) {
		this.setQueryString(queryString);
		getResults();
	}

	public boolean getResults() {

		try {
			results = PageHandler.search(getQueryString(),30);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String formatResults() {

		String out = "<div id='resultWrapper'>";

		for (String r : results)
			out += "<div class='result'><strong>URL: </strong>" + r + "</div>";

		out += "</div>"; // close resultWrapper
		return out;
	}

	@Override
	public String toString() {
		return "Query: " + getQueryString();
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

}