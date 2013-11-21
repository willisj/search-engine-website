<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true"%>
<%@attribute name="footer" fragment="true"%>



<html>
<head>

</head>
<body>

	<div id="wrapper"
		style="width: 720px; margin: 0 auto; padding: 15px 20px; border: 5px solid #ddd; background: #eee; min-height: 600px">
		<div id="pageheader">
			<h1>| grep The Internet</h1>
			<jsp:invoke fragment="header" />
		</div>
		<div id="body">
			<jsp:doBody />
		</div>
		<div id="pagefooter">
			<jsp:invoke fragment="footer" />
		</div>
	</div>
</body>
</html>