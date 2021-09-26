<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Login</title>

<link rel='stylesheet'
	href='http://codepen.io/assets/libs/fullpage/jquery-ui.css'>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/login.css"
	media="screen" type="text/css" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/message.css"
	type="text/css" />

</head>

<body>

	<div class="login-card">
		<h1>Login</h1>
		<br>
		<form:form
			action="${pageContext.request.contextPath}/authenticateUser"
			method="POST">
			<!-- Check for Login Error and display Error Message -->
			<c:if test="${param.error != null}">
				<i class="failed">Invalid username and password</i>
			</c:if>

			<!-- Check for Logout Parameter and display Logout Message -->
			<c:if test="${param.logout != null}">
				<i class="logout">You have been logged out</i>
			</c:if>

			<input type="text" name="username" placeholder="Username">
			<input type="password" name="password" placeholder="Password">
			<input type="submit" name="login" class="login login-submit"
				value="login">
		</form:form>

		<div class="login-help">
			<a href="${pageContext.request.contextPath}/register/showRegistrationForm">Register</a>
		</div>

	</div>

</body>

</html>