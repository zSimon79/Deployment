<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Hikes List</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>

<h1>Hikes List</h1>

<table>
    <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Difficulty</th>
            <th>Location</th> <!-- Assuming location details are now included -->
            <th>Distance (km)</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="hike" items="${hikes}">
            <tr>
                <td>${hike.name}</td>
                <td>${hike.description}</td>
                <td>${hike.difficultyLevel}</td>
                <td>
                    Start: ${hike.location.startPoint}<br>
                    End: ${hike.location.endPoint}
                </td>
                <td>${hike.distance}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<form action="logout" method="post">
    <input type="submit" value="Logout">
</form>
</body>
</html>
