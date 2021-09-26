<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<html>

<head>
<title>Home Page</title>

<link type = "text/css"
		  rel = "stylesheet"
		  href = "${pageContext.request.contextPath}/resources/css/style.css"/>
</head>

<body>

	<h2>Home Page</h2>
	<hr>

	<p>Welcome to Home Page</p>

	<!-- Display Username and Role -->
	<p>
		User: <security:authentication property="principal.username" />
		<br>
		Role(s): <security:authentication property="principal.authorities" />
		<br><br>
		First Name: ${user.firstName}
		<br>
		Last Name: ${user.lastName}
		<br>
		Email: ${user.email}
	</p>

	<security:authorize access = "hasRole('EMPLOYEE')">

	<a href="${pageContext.request.contextPath}/customer/list">Customers</a>
	
	</security:authorize>
	
	<br>
	<br>
	
	<!-- Add a logout button -->
	<form:form action="${pageContext.request.contextPath}/logout"
		method="POST">

		<input type="submit" value="Logout" />
	</form:form>
</body>

</html>