<%@page import="org.apache.taglibs.standard.tag.el.core.ImportTag"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="hr.fer.zemris.java.p12.model.Poll"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>

<head>
<title>Rezultati glasanja</title>
<meta charset="utf-8">
</head>

<body bgcolor="cyan">
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>

	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Opcija</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="option" items="${options}">
				<tr>
					<td align="center" valign="middle">${option.optionTitle}</td>
					<td align="center" valign="middle">${option.votesCount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img src="glasanje-grafika?pollID=${pollID}">

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${pollID}">ovdje</a>
	</p>
	<h2>Razno</h2>
	<p>Linkovi do pobjednika:</p>

	<ul>
		<c:forEach var="bestOption" items="${bestOptions}">
			<li><a href="${bestOption.optionLink}" target="_blank">${bestOption.optionTitle}</a></li>
		</c:forEach>
	</ul>
	<br>
	<a href="./index.html">Home</a>
</body>
</html>