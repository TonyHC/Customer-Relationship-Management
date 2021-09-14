<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "form" uri = "http://www.springframework.org/tags/form" %>

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
        
            <!-- Add HTML Table -->
            <table>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
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
                        </td>
                        
                    </tr>
                </c:forEach>
            </table>
            
            
            <!-- Add Button: Add Customer -->
        	<!-- "showFormToAddCustomer" calls the Spring Controller Mapping -->
        	<input type = "button" value = "Add Customer"
        		onclick = "window.location.href = 'showFormToAddCustomer'; return false;"
        		class = "add-button"/>
        		
        </div>
    </div>

</body>

</html>