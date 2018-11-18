<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<c:when test="${blogEntry==null}">
      No such entry!
    </c:when>
		<c:otherwise>
			<h1>
				<c:out value="${blogEntry.title}" />
			</h1>
			<p>
				<c:out value="${blogEntry.text}" />
			</p>
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
	<a href="${blogEntry.id}/edit">Edit post</a>
	<br>
	<%
		}
		}
	%>

	Comment section:

	<ul>
		<c:forEach var="e" items="${blogEntry.comments}">
			<li><div style="font-weight: bold">
					[Korisnik=
					<c:out value="${e.usersEMail}" />
					]
					<c:out value="${e.postedOn}" />
				</div>
				<div style="padding-left: 10px;">
					<c:out value="${e.message}" />
				</div></li>
		</c:forEach>
	</ul>


	Comment here:
	<form action="" method="POST">

		<div>
			<div>
				<textarea name="message" rows="5" cols="20">${comment.message}</textarea>
			</div>
			<c:if test="${comment.hasMisstep('message')}">
				<div class="greska">
					<c:out value="${comment.getMisstep('message')}" />
				</div>
			</c:if>
		</div>

		<c:choose>
			<c:when test="${empty sessionScope['current.user.nick']}">

				<div>
					<div>
						<span class="formLabel">E-Mail:</span><input type="text"
							name="email" value='<c:out value="${comment.email}"/>' size="20">
					</div>
					<c:if test="${comment.hasMisstep('email')}">
						<div class="greska">
							<c:out value="${comment.getMisstep('email')}" />
						</div>
					</c:if>
				</div>
			</c:when>
		</c:choose>

		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="method" value="Comment">
		</div>

	</form>
	
	<a href="./">Back to author</a>
</body>
</html>
