<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
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


			<h1>Login</h1>

			<form action="main" method="post">

				<div>
					<div>
						<span class="formLabel">Nickname</span><input type="text"
							name="nick" value='<c:out value="${attemptedLogin.nick}"/>'
							size="20">
					</div>
					<c:if test="${attemptedLogin.hasMisstep('nick')}">
						<div class="greska">
							<c:out value="${attemptedLogin.getMisstep('nick')}" />
						</div>
					</c:if>
				</div>

				<div>
					<div>
						<span class="formLabel">Password</span><input type="password"
							name="password" value='' size="20">
					</div>
					<c:if test="${attemptedLogin.hasMisstep('password')}">
						<div class="greska">
							<c:out value="${attemptedLogin.getMisstep('password')}" />
						</div>
					</c:if>
				</div>


				<div class="formControls">
					<span class="formLabel">&nbsp;</span> <input type="submit"
						name="method" value="Submit"> <input type="submit"
						name="method" value="Reset">
				</div>

			</form>

			<br>
			<a href="./register">Register</a>
			<br>

		</c:when>
		<c:otherwise>
			Logged in as 
			<c:out value="${sessionScope['current.user.fn']}" />
			<c:out value="${sessionScope['current.user.ln']}" />
			<br>
			<a href="../logout">Logout</a>
			
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${authors==null and !authors.isEmpty()}">
      No authors available..yet.
    </c:when>
		<c:otherwise>
			<h1>Authors:</h1>
				<ul>
				<c:forEach var="author" items="${authors}">
					<a href="./author/${author.nick}">${author.nick}</a>
					<br>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>


</body>
</html>
