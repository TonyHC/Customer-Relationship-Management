<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "form" uri = "http://www.springframework.org/tags/form" %>
<%@ page import = "com.java.springdemo.utils.SortUtils" %>

<!DOCTYPE html>

<html>

<head>

	<title>List Customers</title>

    <!-- Reference the Style Sheet from Resources Directory -->
    <link type = "text/css"
        rel = "stylesheet"
        href = "${pageContext.request.contextPath}/resources/css/style.css" >

</head>

<body>

    <!-- div is a container for HTML Context -->
    <div id = "wrapper">
        <div id = "header">
            <h2>Customer Relationship Manager</h2>
        </div>
    </div>
    
    <div id = "wrapper">
        <div id = content>
        
            <!-- Add a Search Box -->
            <form:form action = "searchCustomer" method = "GET" >
                Search Customer: <input type = "text" name = "searchName" />
                <input type = "submit" value = "Search" class = "add-button" />
            </form:form>
            
            <!-- Construct an Sort Link for Customer First Name -->
            <c:url var = "sortFirstName" value = "/customer/list" >
                <c:param name = "sort" value = "<%= Integer.toString(SortUtils.FIRST_NAME) %>" />
            </c:url>
                	
            <!-- Construct an Sort Link for Customer Last Name -->
            <c:url var = "sortLastName" value = "/customer/list" >
                <c:param name = "sort" value = "<%= Integer.toString(SortUtils.LAST_NAME) %>" />
            </c:url>
                    
            <!-- Construct an Sort Link for Customer Email -->
            <c:url var = "sortEmail" value = "/customer/list" >
                <c:param name = "sort" value = "<%= Integer.toString(SortUtils.EMAIL) %>" />
            </c:url>
        
            <!-- Add HTML Table -->
            <table>
                <tr>
                    <th><a href = "${sortFirstName}">First Name</a></th>
                    <th><a href = "${sortLastName}">Last Name</a></th>
                    <th><a href = "${sortEmail}">Email</a></th>
                    <th>Action</th>
                </tr>
                
                <!-- Loop Through and Print Out Customers Info -->
                <c:forEach var = "customer" items = "${customers}">
                
                	<!-- Construct an "Update" Link with Customer ID -->
                	<c:url var = "updateLink" value = "/customer/showFormForUpdatingCustomer">
                		<c:param name = "customerID" value = "${customer.id}" />
                	</c:url>
                	
                	<!-- Construct an "Delete" Link with Customer ID -->
                	<c:url var = "deleteLink" value = "/customer/deleteCustomer">
                		<c:param name = "customerID" value = "${customer.id}" />
                	</c:url>
                	
                	<!-- Construct an Customer License Link embedded with Customer ID -->
                    <c:url var = "customerLicenseLink" value = "/customer/license" >
                        <c:param name = "customerID" value = "${customer.id}" />
                    </c:url>
                
                    <tr>
                        <td> ${customer.firstName} </td>
                        <td> ${customer.lastName} </td>
                        <td> ${customer.email} </td>
                        
                        <td> 
                        	<!-- Display the Update Link -->
                        	<a href = "${updateLink}">Update</a> 
                        	|
                        	<!-- Display the Delete Link -->
                        	<!-- Add JS to prompt the User: confirm(...) - displays a confirmation popup dialog -->
                        	<a href = "${deleteLink}"
                        		onclick = "if(!(confirm('Do you want to delete this Customer?'))) return false">Delete</a>
                        	|
                        	<!-- Display the License Link -->
                        	<a href = "${customerLicenseLink}">License</a>
                        </td>
                        
                    </tr>
                </c:forEach>
            </table>
            
            
            <!-- Add Button: Add Customer -->
        	<!-- "showFormToAddCustomer" calls the Spring Controller Mapping -->
        	<input type = "button" value = "Add Customer"
        		onclick = "window.location.href = 'showFormToAddCustomer'; return false;"
        		class = "add-button"/>
        	
        	<br>
     
            <a href = "${pageContext.request.contextPath}/license/list" >Licenses</a>
            
            
            
            <!-- Add a logout button -->
			<form:form action="${pageContext.request.contextPath}/logout"
				method="POST">
		
				<input type="submit" value="Logout" class = "add-button"/>
			</form:form>
          
        </div>
    </div>

</body>

</html>