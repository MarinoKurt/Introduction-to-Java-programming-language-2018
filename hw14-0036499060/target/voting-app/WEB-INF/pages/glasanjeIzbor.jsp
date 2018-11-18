<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<title>${poll.title}</title>
<meta charset="utf-8">
</head>

<body bgcolor="cyan">
	<h1>${poll.title}</h1>
	<p>${poll.message}</p>
	<ol>
		<c:forEach var="option" items="${options}">
			<li><a href="glasanje-glasaj?id=${option.id}">${option.optionTitle}</a></li>
		</c:forEach>
	</ol>
	<br>
	<a href="..">Home</a>
</body>
</html>