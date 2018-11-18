<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Registration</title>

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


		<body>
			<h1>New user</h1>


			<form action="register" method="POST">

				<div>
					<div>
						<span class="formLabel">First name</span><input type="text"
							name="firstName" value='<c:out value="${form.firstName}"/>'
							size="20">
					</div>
					<c:if test="${form.hasMisstep('firstName')}">
						<div class="greska">
							<c:out value="${form.getMisstep('firstName')}" />
						</div>
					</c:if>
				</div>

				<div>
					<div>
						<span class="formLabel">Last name</span><input type="text"
							name="lastName" value='<c:out value="${form.lastName}"/>'
							size="20">
					</div>
					<c:if test="${form.hasMisstep('lastName')}">
						<div class="greska">
							<c:out value="${form.getMisstep('lastName')}" />
						</div>
					</c:if>
				</div>

				<div>
					<div>
						<span class="formLabel">EMail</span><input type="text"
							name="email" value='<c:out value="${form.email}"/>' size="50">
					</div>
					<c:if test="${form.hasMisstep('email')}">
						<div class="greska">
							<c:out value="${form.getMisstep('email')}" />
						</div>
					</c:if>
				</div>

				<div>
					<div>
						<span class="formLabel">Nickname</span><input type="text"
							name="nick" value='<c:out value="${form.nick}"/>' size="20">
					</div>
					<c:if test="${form.hasMisstep('nick')}">
						<div class="greska">
							<c:out value="${form.getMisstep('nick')}" />
						</div>
					</c:if>
				</div>

				<div>
					<div>
						<span class="formLabel">Password</span><input type="password"
							name="password" value='<c:out value=""/>' size="20">
					</div>
					<c:if test="${form.hasMisstep('password')}">
						<div class="greska">
							<c:out value="${form.getMisstep('password')}" />
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
	</c:when>
	<c:otherwise>
			Logged in users can't register, silly. 
	</c:otherwise>
</c:choose>


</html>
