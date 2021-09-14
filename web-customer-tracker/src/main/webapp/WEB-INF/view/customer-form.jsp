<%@ taglib prefix = "form" uri = "http://www.springframework.org/tags/form" %>

<!DOCTYPE html>

<html>

<head>

	<title>Save Customer</title>

	<link type = "text/css"
		  rel = "stylesheet"
		  href = "${pageContext.request.contextPath}/resources/css/style.css"/>

	<link type = "text/css"
		  rel = "stylesheet"
		  href = "${pageContext.request.contextPath}/resources/css/add-customer-style.css"/>
</head>

<body>

	<div id = "wrapper">
		<div id = "header">
			<h2>Customer Relationship Manager</h2>
		</div>
	</div>
	
	<div id = "container">
		<h3>Save Customer</h3>
		
		<!-- Send Form Data to Spring MVC Mapping "saveCustomer" -->
		<form:form action = "saveCustomer" modelAttribute = "customer" method = "POST" >
		
			<!-- Need to associate this data with appropriate customer id -->
			<!-- so that our back end system (DB) can perform the Update Operation on correct Customer -->
			<!-- When form is loaded, calls customer.getID() -->
			<!-- When form is submitted, calls customer.setID -->
			<form:hidden path = "id" />
		
			<table>
				<tbody>
					<tr>
						<td>First Name</td>
						<td><form:input path = "firstName" /> </td>
					</tr>
					
					<tr>
						<td>Last Name</td>
						<td><form:input path = "lastName" /> </td>
					</tr>
					
					<tr>
						<td>Email</td>
						<td><form:input path = "email" /> </td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type = "submit" value = "Save" class = "save" /> </td>
					</tr>
				</tbody>
			</table>
		
		</form:form>
		
		<div style = "clear"; both;"></div>
		
		<p>
			<a href = "${pageContext.request.contextPath}/customer/list" >Back to Customer List</a>
		</p>
	</div>

</body>

</html>