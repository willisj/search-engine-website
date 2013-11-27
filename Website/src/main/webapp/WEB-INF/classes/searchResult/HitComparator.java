package classes.searchResult;

import java.util.Comparator;

import org.apache.lucene.document.Document;

public class HitComparator implements Comparator<Document> {
	    public int compare(Document d1, Document d2) {
	    	ResultEntry entry1 = new ResultEntry(),entry2=new ResultEntry();
	    	
			if(d1.getField("pagerank") != null)
				entry1.pageRank = d1.getField("pagerank").numericValue().doubleValue();
			if(d2.getField("pagerank") != null)
				entry2.pageRank =  d2.getField("pagerank").numericValue().doubleValue();
			
	        return entry1.compareTo(entry2);
	    }
	
}
