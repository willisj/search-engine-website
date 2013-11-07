
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:genericpage>
	<jsp:attribute name="header">
      
    </jsp:attribute>
	<jsp:attribute name="footer">
      
    </jsp:attribute>
	<jsp:body>
        		<form action="SearchResultServlet" method="POST">
			<p>
				<input type="submit" name="Submit" value="Search" /><input
					type="text" name="query" />

			</p>
		</form>
		<div id="results"
			style="border: 1px solid #ddd; padding: 5px 10px; margin: auto; min-height: 550px">

			<p>
				<strong>Query String: </strong>	${SearchQuery}
			</p>
			<hr style="color: #ddd;">
			${SearchResult}

		</div>
    </jsp:body>
</t:genericpage>