package classes.searchResult;

public class ResultEntry {
	public String summary, url,title = "Page Title";
	public long globalIn, globalOut, localIn, localOut;
	public int compareTo(ResultEntry entry2) {
		if(globalIn < entry2.globalIn)
			return 1;
		else if (globalIn == entry2.globalIn)
			return 0;
		else return -1;
	}
}
