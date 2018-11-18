<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<title>Glasanje</title>
<meta charset="utf-8">
</head>

<body bgcolor="cyan">

	<h1>Odaberite anketu:</h1>
	<br>
	<c:forEach var="poll" items="${polls}">
		<li><a href="./glasanje?pollID=${poll.id}">${poll.title}</a></li>
	</c:forEach>

</body>
</html>

