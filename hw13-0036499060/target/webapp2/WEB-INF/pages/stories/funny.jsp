<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true" import="java.util.Random"%>
<!DOCTYPE html>

<%
	String[] colors = { "red", "blue", "yellow", "azure", "black", "brown", "purple", "RoyalBlue" };
	Random rand = new Random();
	int x = rand.nextInt(colors.length);
	String fontColor = colors[x];
%>
<html>

<head>
<title>Much fun</title>
<meta charset="utf-8">
</head>

<body bgcolor="${pickedBgCol}">

	<h1>Funny story</h1>
	<p>
		<font size="10" color="${fontColor}">My social life since
			March.</font>
	</p>
	<a href=".">Home</a>

</body>

</html>
