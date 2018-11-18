<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Entry form</title>

<style type="text/css">
.greska {
	font-family: "Arial Black", Gadget, sans-serif;
	font-weight: bold;
	font-size: 0.9em;
	color: #FF0000;
	padding-left: 110px;
}

.formLabel {
	font-family: "Lucida Console", Monaco, monospace;
	display: inline-block;
	width: 100px;
	font-weight: bold;
	text-align: right;
	padding-right: 10px;
}

.formControls {
	margin-top: 10px;
}
</style>
</head>
	<c:choose>
		<c:when test="${empty sessionScope['current.user.nick']}">
		Not logged in.
		</c:when>
		<c:otherwise>
			Logged in as 
			<c:out value="${sessionScope['current.user.fn']}" />
			<c:out value="${sessionScope['current.user.ln']}" />
			<br>
			<a href=${pageContext.request.contextPath}/logout>Logout</a>

		</c:otherwise>
	</c:choose>

<body>

	<form action="./${type}" method="POST">
<br><br>
		<div>
			<div>
				<span class="formLabel">Title</span><input type="text" name="title"
					value='<c:out value="${entryForm.title}"/>' size="20">
			</div>
			<c:if test="${entryForm.hasMisstep('title')}">
				<div class="greska">
					<c:out value="${entryForm.getMisstep('title')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Text</span>
				<textarea name="text" rows="5" cols="20">${entryForm.text}</textarea>
			</div>
			<c:if test="${entryForm.hasMisstep('text')}">
				<div class="greska">
					<c:out value="${entryForm.getMisstep('text')}" />
				</div>
			</c:if>
		</div>

		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="method" value="Submit"> <input type="submit"
				name="method" value="Cancel">
		</div>

	</form>
</body>


</html>
