<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
<title>Trigonometry</title>
<meta charset="utf-8">
</head>

<body bgcolor="${pickedBgCol}">

	<a href=".">Home</a>
	<br>
	<br>
	<p>Displaying value of sin and cos for degrees from ${a} to ${b}.</p>

	<table>
		<thead>
			<tr>
				<th>Angle</th>
				<th>sin</th>
				<th>cos</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entry" items="${data}">
				<tr>
					<td>${entry.angle}</td>
					<td><fmt:formatNumber type="number" maxFractionDigits="5" value="${entry.sin}"/></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="5" value="${entry.cos}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>


</html>
