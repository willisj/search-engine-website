package classes.searchResult;

import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
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

import classes.processing.TopologyImporter;

public class PageHandler {
	final static Version matchVersion = Version.LUCENE_45;
	final static String fsPath = "/home/jordan/workspace/search-engine-website/Website/luceneStore";
	static Directory index;

	public static void pageFirstAnalysis(URL url, String source,
			int crawlDepth, TopologyImporter topo) {
		if (source == null)
			return;

		StandardAnalyzer analyzer = new StandardAnalyzer(matchVersion);
		try {
			index = new SimpleFSDirectory(new File(fsPath));
		} catch (IOException e1) {
			System.err.println("Error accessing lucenestore");
			e1.printStackTrace();
		}

		IndexWriterConfig config = new IndexWriterConfig(matchVersion, analyzer);
		try {
			IndexWriter w = new IndexWriter(index, config);
			addDoc(w, url, source, topo);
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void addDoc(IndexWriter w, URL url, String src,
			TopologyImporter topo) throws IOException {
		Document doc = new Document();
		// doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new StringField("url", url.toString(), Field.Store.YES));
		doc.add(new TextField("src", src, Field.Store.YES));

		doc.add(new NumericDocValuesField("globalIn", topo
				.getGlobalOutScore(url)));
		doc.add(new NumericDocValuesField("globalOut", topo
				.getGlobalInScore(url)));
		doc.add(new NumericDocValuesField("localIn", 0));
		doc.add(new NumericDocValuesField("localOut", 0));

		w.addDocument(doc);
	}

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
		TopScoreDocCollector collector = TopScoreDocCollector.create(maxHits,
				true);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			ResultEntry entry = new ResultEntry();
			entry.summary = "";// d.get("src");;
			entry.globalIn = d.getField("globalIn").numericValue().longValue();
			entry.globalOut =  d.getField("globalOut").numericValue().longValue();
			entry.localIn =  d.getField("localIn").numericValue().longValue();
			entry.localOut = d.getField("localOut").numericValue().longValue();
			entry.url = d.get("url");
			results.add(entry);
		}

		return results;
	}

}
