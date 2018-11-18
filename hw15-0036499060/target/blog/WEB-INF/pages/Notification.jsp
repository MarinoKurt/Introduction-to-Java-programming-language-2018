<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
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

	<p>
		<c:out value="${message}" />
	</p>
	
	<a href="${pageContext.request.contextPath}/servleti/main">Home</a>

</body>
</html>
