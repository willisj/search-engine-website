
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:genericpage>
	<jsp:attribute name="header">
      
    </jsp:attribute>
	<jsp:attribute name="footer">
      
    </jsp:attribute>
	<jsp:body>
		<div id="results"
			style="border: 1px solid #ddd; padding: 5px 10px; margin: auto; min-height: 550px">

			<p>
				<strong>Status: </strong>	${status}
			</p>

		</div>
    </jsp:body>
</t:genericpage>