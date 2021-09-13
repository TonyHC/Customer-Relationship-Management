<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>

   	<title>Customers Licenses</title>

    <!-- Reference the Style Sheet from Resources Directory -->
    <link type = "text/css"
        rel = "stylesheet"
        href = "${pageContext.request.contextPath}/resources/css/style.css" >
</head>

<body>

	<div class="container">
	
		<h2>Customer License</h2>
		
		<table class="table table-bordered table-striped">
			<thead class="table-primary">
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>License Name</th>
					<th>Start Date</th>
					<th>Expiration Date</th>										
				</tr>
			</thead>
			
			<tbody>
					<!-- loop over and print our customers -->
					<c:forEach var = "license" items = "${licenses}">
										
						<tr>
							<td> ${customer.firstName} </td>
							<td> ${customer.lastName} </td>
							<td> ${license.licenseName} </td>	
							<td> ${license.startDate} </td>	
							<td> ${license.expirationDate} </td>				
						</tr>
					
					</c:forEach>
			</tbody>		
		</table>
		
	</div>
	
</body>
</html>