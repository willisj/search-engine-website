package classes.processing;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.searchResult.PageHandler;
import crawler.Page;

/**
 * Servlet implementation class ProcessPageFiles
 */
public class ProcessPageFiles extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String fsPath = "/home/jordan/searchengine/store/";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProcessPageFiles() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("PageProcessCount", processDir(new File(fsPath))); // path
																				// to
																				// importPagefiles
																				// directory
		request.getRequestDispatcher("ProcessPageFiles.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("PageProcessCount", processDir(new File(fsPath))); // path
																				// to
																				// importPagefiles
																				// directory
		request.getRequestDispatcher("ProcessPageFiles.jsp").forward(request,
				response);
	}

	private long processDir(File source) {
		TopologyImporter topo = new TopologyImporter();
		try {
			topo.importTopology("/home/jordan/searchengine/batch1/linkFile");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (source == null)
			return 0;
		if (!source.exists()) {
			System.err.println("Improper Page import directory set. DNE");
		}

		Page p;
		long count = 0;

		for (String f : source.list()) {
			System.out.println(f);
			for (File file : new File(source.getAbsolutePath() + "/" + f)
					.listFiles()) {

				if (file.isFile()) {
					processFile(file,topo);
				}
			}
		}
		return count;
	}

	private void processFile(File file, TopologyImporter topo) {
		Page p = Page.load(file);

		System.out.println(p.getUrl().toString());
		PageHandler.pageFirstAnalysis(p.getUrl(), p.getRawSource(),
				p.getCrawlDepth(),topo);

	}

}
