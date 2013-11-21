package classes.processing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

public class TopologyImporter {

	// Domain:id
	HashMap<String, Integer> GlobalIDs;

	// DomainID:(URL:id)
	HashMap<Integer, HashMap<String, Integer>> LocalIDs;

	public HashMap<Integer, Integer> localCountIn, globalCountIn,
			localCountOut, globalCountOut;
	int nextGlobalURLID = 0;
	int nextLocalURLID = 0;

	final static boolean SKIP_LOCAL = true;

	//"/home/jordan/searchengine/batch1/linkFile"
	public void importTopology(String file) throws IOException {
		GlobalIDs = new HashMap<String, Integer>();
		LocalIDs = new HashMap<Integer, HashMap<String, Integer>>();

		localCountIn = new HashMap<Integer, Integer>();
		globalCountIn = new HashMap<Integer, Integer>();
		localCountOut = new HashMap<Integer, Integer>();
		globalCountOut = new HashMap<Integer, Integer>();

		BufferedReader br = new BufferedReader(new FileReader(file));

		String line = br.readLine();
		while (line != null) {
			String[] link = line.split("\t");
			if (link.length == 2) {
				URL src = new URL(link[0]);
				URL dest = new URL(link[1]);

				if (src != null && dest != null && src.getAuthority() != null
						&& dest.getAuthority() != null) {
					if (SKIP_LOCAL)
						incrementCount(getGlobalID(src),0,
								getGlobalID(dest),0);
					else
						incrementCount(getGlobalID(src), getLocalID(src),
								getGlobalID(dest), getLocalID(dest));
				}

			} else
				System.err.println("Error: bad line in file\n" + line);
			line = br.readLine();
		}

		br.close();
	}
	
	public int getGlobalInScore(URL u){
		if(GlobalIDs.containsKey(u.getAuthority()) && globalCountIn.containsKey(GlobalIDs.get(u.getAuthority())))
			return globalCountIn.get(GlobalIDs.get(u.getAuthority()));
		return 0;
	}
	
	public int getGlobalOutScore(URL u){
		if(GlobalIDs.containsKey(u.getAuthority()) && globalCountOut.containsKey(GlobalIDs.get(u.getAuthority())))
			return globalCountOut.get(GlobalIDs.get(u.getAuthority()));
		return 0;
	}
	
	private void writeFiles() throws IOException{
		writeGlobalHashMap(globalCountOut, new File(
				"/home/jordan/searchengine/topology/globalCountOut"));
		writeGlobalHashMap(globalCountIn, new File(
				"/home/jordan/searchengine/topology/globalCountIn"));
	}

	private void writeGlobalHashMap(HashMap<Integer, Integer> counts,
			File file) throws IOException {

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		for (Entry e : GlobalIDs.entrySet()) {
			bw.write((counts.get(e.getValue()) != null ? counts.get(e
					.getValue()) : "0") + "\t" + e.getKey() + "\n");
		}
		bw.close();
	}

	private int getGlobalID(URL url) {
		if (!GlobalIDs.containsKey(getDomain(url))) {
			GlobalIDs.put(getDomain(url), nextGlobalURLID);
			return nextGlobalURLID++;
		}

		return GlobalIDs.get(getDomain(url));
	}

	private int getLocalID(URL url) {
		int domain = getGlobalID(url);
		if (!LocalIDs.containsKey(domain)) {

			LocalIDs.put(domain, new HashMap<String, Integer>());
			LocalIDs.get(domain).put(url.toString(), nextLocalURLID);
			return nextLocalURLID++;
		}

		if (!LocalIDs.get(domain).containsKey(url)) {
			LocalIDs.get(domain).put(url.toString(), nextLocalURLID);
			return nextLocalURLID++;
		}

		return LocalIDs.get(domain).get(url);
	}

	private static String getDomain(URL url) {
		return url.getAuthority().toString();
	}

	private void incrementCount(int srcGlobal, int srcLocal,
			int destGlobal, int destLocal) {

		if (destGlobal != srcGlobal) {
			globalCountOut.put(
					srcGlobal,
					(globalCountOut.containsKey(srcGlobal) ? globalCountOut
							.get(srcGlobal) + 1 : 1));

			globalCountIn.put(
					destGlobal,
					(globalCountIn.containsKey(destGlobal) ? globalCountIn
							.get(destGlobal) + 1 : 1));

		} else if(!SKIP_LOCAL) {

			localCountOut.put(
					srcLocal,
					(localCountOut.containsKey(srcLocal) ? localCountOut
							.get(srcLocal) + 1 : 1));
			localCountIn.put(
					destLocal,
					(localCountIn.containsKey(srcLocal) ? localCountIn
							.get(destLocal) + 1 : 1));
		}
	}
}
