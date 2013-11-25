package classes.config;

public class MasterConfig {
	
	
	 // * Values for Windows Machine *//
	//private static char sep = '\\';
	
	//public static final String searchEngineRoot = "C:\\Users\\Jordan\\Documents\\Store\\searchengine\\";

	
	// *Values for Ubuntu Machine *//
	 private static char sep = '/';
	 public static final String searchEngineRoot = "/home/jordan/searchengine/";

	
	public static final String luceneStorePath = searchEngineRoot
			+ "lucenestore";
	public static final String crawlerStorePath = searchEngineRoot + "store";
	public static final String topologyLinkFile = searchEngineRoot
			+ "batch1"+sep+"linkFile";
	
	//* Crawling Config *//
	public final static int maxPagesBeforeSorting = 200;
	
	
	
}
