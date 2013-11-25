package classes.searchResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.io.File;
import java.io.IOException;

import javax.servlet.jsp.jstl.core.Config;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;


public class PageHandler {
	final static Version matchVersion = Version.LUCENE_45;
	final static String fsPath = classes.config.MasterConfig.luceneStorePath;
	static Directory index;


	
	public static Vector<ResultEntry> search(String searchString, int maxHits)
			throws IOException, ParseException {
		StandardAnalyzer analyzer = new StandardAnalyzer(matchVersion);
		Vector<ResultEntry> results = new Vector<ResultEntry>();
		index = new SimpleFSDirectory(new File(fsPath));

		// Create the query

		Query q = new QueryParser(matchVersion, "src", analyzer)
				.parse(searchString);

		// perform the search

		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(
				fsPath)));
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(classes.config.MasterConfig.maxPagesBeforeSorting,
				true);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		Document[] hitDocs = new Document[hits.length];
		
		for (int i = 0; i < hits.length; ++i)
			hitDocs[i] = searcher.doc(hits[i].doc);
		
		Arrays.sort(hitDocs,new HitComparator());

		for (int i = 0; i < hitDocs.length; ++i) {
			Document d = hitDocs[i];
			ResultEntry entry = new ResultEntry();
			entry.summary = "";// d.get("src");;
			if(d.getField("globalIn") != null)
				entry.globalIn = d.getField("globalIn").numericValue().longValue();
			if(d.getField("globalOut") != null)
				entry.globalOut =  d.getField("globalOut").numericValue().longValue();
			if(d.getField("localIn") != null)
				entry.localIn =  d.getField("localIn").numericValue().longValue();
			if(d.getField("localOut") != null)
				entry.localOut = d.getField("localOut").numericValue().longValue();
			entry.url = d.get("url");
			entry.title= d.get("title");
			results.add(entry);
		}

		return results;
	}

}
