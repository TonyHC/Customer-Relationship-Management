<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
            <h2>List of Licenses</h2>
        </div>
    </div>
    
    <div id = "wrapper">
        <div id = content>
        
            <!-- Construct an Sort Link for Customer First Name -->
            <c:url var = "sortLicenses" value = "/customer/licenses" >
                <c:param name = "sort" value = "<%= Integer.toString(SortUtils.LICENSE_NAME) %>" />
            </c:url>
                    
            <!-- Construct an Sort Link for Customer Last Name -->
            <c:url var = "sortStartDate" value = "/customer/licenses" >
                <c:param name = "sort" value = "<%= Integer.toString(SortUtils.START_DATE) %>" />
            </c:url>
                    
            <!-- Construct an Sort Link for Customer Email -->
            <c:url var = "sortExpirationDate" value = "/customer/licenses" >
                <c:param name = "sort" value = "<%= Integer.toString(SortUtils.EXPIRATION_DATE) %>" />
            </c:url>
            
            <!-- Add HTML Table -->
            <table>
                <tr>
                    <th><a href = "${sortLicenses}">License Name</a></th>
                    <th><a href = "${sortStartDate}">Start Date</a></th>
                    <th><a href = "${sortExpirationDate}">Expiration Date</a></th>
                    <th>Action</th>
                </tr>
                
                <!-- Loop Through and Print Out Customers Info -->
                <c:forEach var = "license" items = "${licenses}">
                    <!-- Construct an "Delete" Link with License ID -->
                    <c:url var = "deleteLink" value = "/customer/deleteLicense">
                        <c:param name = "licenseID" value = "${license.id}" />
                    </c:url>
                
                
                    <tr>
                        <td> ${license.licenseName} </td>
                        <td> ${license.startDate} </td>
                        <td> ${license.expirationDate} </td>
                        <td><a href = "${deleteLink}"
                            onclick = "if(!(confirm('Do you want to delete this License?'))) return false">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
            
        <p>
            <a href = "${pageContext.request.contextPath}/customer/list" >Back to Customer List</a>
        </p>
            
        </div>
    </div>

</body>

</html>