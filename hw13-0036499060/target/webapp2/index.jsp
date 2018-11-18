<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<title>Home</title>
<meta charset="utf-8">
</head>



<body bgcolor="${pickedBgCol}">

	<h1>Home</h1>

	<p>
		<br> <a href="setcolor">Background color chooser</a> <br>
		
		<a href="trigonometric?a=0&b=90">Simple trigonometry</a> <br>
		
		<a href="stories/funny.jsp">Humor at its finest</a> <br>

		<a href="./report.jsp">OS usage</a> <br>
		
		<a href="./appinfo.jsp">App info</a> <br>
		
		<a href="powers?a=1&b=100&n=3">Default powers</a> <br>

		<a href="glasanje">Glasanje</a> <br> <br>
		
		<form action="trigonometric" method="GET">
			Početni kut:<br> 
			<input type="number" name="a" min="0" max="360"	step="1" value="0"><br> 
			
			Završni kut:<br> 
			<input type="number" name="b" min="0" max="360" step="1" value="360"><br>
			
			<input type="submit" value="Tabeliraj"><input type="reset"	value="Reset">
		</form>
	
	</p>



</body>



</html>
