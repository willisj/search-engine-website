package classes.searchResult;

import java.util.Comparator;

import org.apache.lucene.document.Document;

public class HitComparator implements Comparator<Document> {
	    public int compare(Document d1, Document d2) {
	    	ResultEntry entry1 = new ResultEntry(),entry2=new ResultEntry();
	    	
			if(d1.getField("globalIn") != null)
				entry1.globalIn = d1.getField("globalIn").numericValue().longValue();
			if(d1.getField("globalOut") != null)
				entry1.globalOut =  d1.getField("globalOut").numericValue().longValue();
			if(d2.getField("globalIn") != null)
				entry2.globalIn = d2.getField("globalIn").numericValue().longValue();
			if(d2.getField("globalOut") != null)
				entry2.globalOut =  d2.getField("globalOut").numericValue().longValue();
	    	
	        return entry1.compareTo(entry2);
	    }
	
}
