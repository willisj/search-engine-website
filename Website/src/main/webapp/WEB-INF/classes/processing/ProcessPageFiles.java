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
	static final String fsPath = "C:\\Users\\Jordan\\workspace\\Website\\importPageFiles";

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
		if(source == null)
			return 0;
		if(!source.exists()){
			System.err.println("Improper Page import directory set. DNE");
		}
		
		Page p;
		long count = 0;
		
		for (File file : source.listFiles()) {
			if (file.isFile()) {
				p = Page.load(new File(file.getAbsolutePath()));

				System.out.println(p.getUrl().toString());
				PageHandler.pageFirstAnalysis(p.getUrl().toString(),
						p.getRawSource(), p.getCrawlDepth());

				++count;

			} else
				count += processDir(file);
		}
		return count;
	}

}
