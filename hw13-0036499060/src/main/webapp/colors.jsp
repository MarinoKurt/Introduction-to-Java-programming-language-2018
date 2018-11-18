<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<title>Color picker</title>
<meta charset="utf-8">
</head>

<body bgcolor="${pickedBgCol}">

	<h1>Background color picker</h1>

	<a href="setcolor?color=white">WHITE</a>
	<br>
	<a href="setcolor?color=red">RED</a>
	<br>
	<a href="setcolor?color=green">GREEN</a>
	<br>
	<a href="setcolor?color=cyan">CYAN</a>
	<br>
	<br>
	<br>
	<br>
	<a href=".">Home</a>
	<br>
</body>


</html>
