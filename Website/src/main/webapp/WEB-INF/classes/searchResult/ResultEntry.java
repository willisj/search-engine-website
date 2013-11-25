package classes.searchResult;

public class ResultEntry {
	public String summary, url,title = "Page Title";
	public long globalIn, globalOut, localIn, localOut;
	public int compareTo(ResultEntry entry2) {
		return (globalIn >= entry2.globalIn?-1:1);
	}
}
