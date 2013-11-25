package classes.searchResult;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.lucene.queryparser.classic.ParseException;

import classes.searchResult.PageHandler;

public class SearchResult extends SimpleTagSupport {

	String[] lorem = {
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. ---FILLER TEXT--- Cras eu odio quis urna vestibulum euismod eget sit amet nibh. Sed fringilla bibendum turpis non tincidunt. Nulla eu dignissim magna. Donec ut facilisis libero. Duis eget massa vel arcu fermentum tincidunt quis eu dui. ",
			"Fusce diam mi, sodales sit amet porta sit amet, commodo placerat eros. ---FILLER TEXT--- Sed mollis adipiscing ligula. Ut ut tristique purus, rhoncus fermentum metus. Maecenas mollis rutrum felis, a malesuada elit iaculis vitae. Fusce egestas interdum enim, a fringilla nunc tincidunt a. ",
			"Suspendisse volutpat, lorem a sollicitudin eleifend, lectus nisi gravida augue, at consequat urna tellus a turpis. ---FILLER TEXT--- Aenean at urna nulla. Nullam ut nisl vitae urna posuere ornare. Nunc et accumsan leo. Fusce ut leo libero. Cras mattis congue vehicula. Curabitur in lectus non mauris pulvinar volutpat. ",
			"Quisque mattis mauris vitae congue lacinia. ---FILLER TEXT--- Cras vestibulum dolor sit amet metus tempor consectetur. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Proin in diam a diam adipiscing fermentum sed sed massa. Suspendisse eu massa eros.",
			"Donec egestas lectus eu vehicula posuere. ---FILLER TEXT--- Aliquam velit ante, viverra in libero quis, pharetra pharetra risus. Nullam sagittis, nisl sed tincidunt tempor, augue orci hendrerit lorem, sit amet tempor augue sapien et est. Aenean in nisi sit amet nisl aliquet rhoncus sit amet eu mi. " };

	// Persistent Fields:
	private String queryString;
	private Vector<ResultEntry> results;
	private long gin, gout, lin, lout;

	// Constructors:
	public SearchResult(String queryString) {
		gin = gout = lin = lout = 0;
		this.setQueryString(queryString);
		getResults();
	}

	public boolean getResults() {

		try {
			results = PageHandler.search(getQueryString(), 30);
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
		int i = 0;

		for (ResultEntry r : results) {
			out += "<div class='result'>" + "<strong><a href='" + r.url + "'>"
					+ r.title + "</a></strong>" + "<br><sub><a href='" + r.url
					+ "'>" + r.url + "</a>"
					//+"Local(In: <i>"+ r.localIn + "</i> Out: <i>" + r.localOut + "</i>) |"
					+ "<span class='linkCounts'> Global(In: <i>" + r.globalIn + "</i> Out: <i>"
					+ r.globalOut + "</i>)</span></sub>" + "<p class='summaryText'>"
					+ lorem[i++] + "</p>" + "</div>";
			if (i == lorem.length)
				i = 0;
		}
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