
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:genericpage>
	<jsp:attribute name="header">
      
    </jsp:attribute>
	<jsp:attribute name="footer">
      
    </jsp:attribute>
	<jsp:body>
        		<form action="SearchResultServlet" method="POST">
			<p>
				cat /mnt/internet | grep " 
				<input value="${SearchQuery}" type="text" name="query" /> "
				<input type="submit" name="Submit" value="Search" />

			</p>
		</form>
		<div id="results"
			style="border: 1px solid #ddd; padding: 5px 10px; margin: auto; min-height: 550px">
			<p>
			</p>
			
			${SearchResult}

		</div>
    </jsp:body>
</t:genericpage>