<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>

<html>
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
<body>


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

	<c:choose>
		<c:when test="${author==null}">
      No such author!
    </c:when>
		<c:otherwise>
			<h1>
				<c:out value="Author ${author.nick}" />
			</h1>
			<c:if test="${!author.entries.isEmpty()}">
				<ol>
					<c:forEach var="e" items="${author.entries}">
						<li><div style="font-weight: bold">
								<a href=${pageContext.request.contextPath}/servleti/author/${author.nick}/${e.id}>${e.title}</a> <br>
							</div>
					</c:forEach>
				</ol>
			</c:if>
			<c:if test="${author.entries.isEmpty()}">
				<c:out value="Author has no posts..yet." />
			</c:if>
		</c:otherwise>
	</c:choose>

	<%
		BlogUser author = (BlogUser) request.getAttribute("author");
		if (author != null) {
			String currentNick = (String) session.getAttribute("current.user.nick");
			if (author.getNick().equals(currentNick)) {
	%>
	<br>
	<br>
	<br>
	<a href=${pageContext.request.contextPath}/servleti/author/${author.nick}/new>Create a new entry</a>
	<br>
	<%
		}
		}
	%>
	<a href="${pageContext.request.contextPath}/servleti/main">Home</a>

</body>
</html>
