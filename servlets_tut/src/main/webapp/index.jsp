<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>Welcome to Servlet tut for YouTube</title>

<!--  including external css -->

<link rel="stylesheet"
	href=" <%=application.getContextPath()%>/css/style.css " />

</head>
<body>
	<div class="container">

		<%@include file="menu.jsp"%>

		<h1>Welcome to Servlet Crash Course</h1>

		<p>This is very information video about servlet</p>


		<form action="<%=application.getContextPath()%>/third" method="post">


			<input name="message" type="text" placeholder="enter text here" />

			<button type="submit">Submit</button>

		</form>

	</div>

	<script src="<%=application.getContextPath()%>/js/script.js">
		
	</script>
</body>
</html>