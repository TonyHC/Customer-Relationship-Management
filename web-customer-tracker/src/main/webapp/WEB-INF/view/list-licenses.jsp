<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

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
            
            <!-- Add HTML Table -->
            <table>
                <tr>
                    <th>License Name</th>
                    <th>Start Date</th>
                    <th>Expiration Date</th>
                </tr>
                
                <!-- Loop Through and Print Out Customers Info -->
                <c:forEach var = "license" items = "${licenses}">
                    <tr>
                        <td> ${license.licenseName} </td>
                        <td> ${license.startDate} </td>
                        <td> ${license.expirationDate} </td>
                    </tr>
                </c:forEach>
            </table>
            
        </div>
    </div>

</body>

</html>