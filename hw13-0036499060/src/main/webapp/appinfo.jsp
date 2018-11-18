<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.concurrent.TimeUnit"%>

<%!private void calculateRuntime(javax.servlet.jsp.JspWriter out, long startTime) throws java.io.IOException {
		long now = System.currentTimeMillis();
		long result = now - startTime;

		out.print(formatInterval(result));
	}%>

<%!private static String formatInterval(final long l) {
		final long d = TimeUnit.MILLISECONDS.toDays(l);
		final long hr = TimeUnit.MILLISECONDS.toHours(l - TimeUnit.DAYS.toMillis(d));
		final long min = TimeUnit.MILLISECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(hr));
		final long sec = TimeUnit.MILLISECONDS
				.toSeconds(l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
		final long ms = TimeUnit.MILLISECONDS.toMillis(
				l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));

		return String.format("%02d days %02d hours %02d minutes %02d seconds %03d miliseconds", d, hr, min, sec, ms);
	}%>


<!DOCTYPE html>
<html>

<head>
<title>App info</title>
<meta charset="utf-8">
</head>

<body bgcolor="${pickedBgCol}">
	<%
		long startTime = Long.parseLong(request.getServletContext().getAttribute("time").toString());
	%>

	<a href=".">Home</a>
	<br>
	<h1>App info</h1>
	<%
		calculateRuntime(out, startTime);
	%>



</body>


</html>
