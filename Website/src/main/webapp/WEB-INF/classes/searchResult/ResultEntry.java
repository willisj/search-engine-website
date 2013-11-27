package classes.searchResult;

public class ResultEntry {
	public String summary, url,title = "Page Title";
	public long globalIn, globalOut, localIn, localOut;
	public double pageRank;
	public int compareTo(ResultEntry entry2) {
		if(pageRank < entry2.pageRank)
			return 1;
		else if (pageRank == entry2.pageRank)
			return 0;
		else return -1;
	}
}
