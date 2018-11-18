<%@page import="org.apache.taglibs.standard.tag.el.core.ImportTag"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="hr.fer.zemris.java.webapp.Band"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>

<head>
<title>Rezultati glasanja</title>
<meta charset="utf-8">
</head>

<body bgcolor="${pickedBgCol}">
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>

	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="band" items="${bands}">
				<tr>
					 <td align="center" valign="middle">${band.name}</td>
					 <td align="center" valign="middle">${band.votes}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img src="glasanje-grafika">

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>
	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>

	<ul>
		<c:forEach var="bestBand" items="${bestBands}">
			<li><a href="${bestBand.songLink}" target="_blank">${bestBand.name}</a></li>
		</c:forEach>
	</ul>
	<br>
	<a href=".">Home</a>
</body>
</html>