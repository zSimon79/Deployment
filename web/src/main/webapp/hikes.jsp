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
            <th>Start Point</th>
            <th>End Point</th>
            <th>Distance (km)</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="hike" items="${hikes}">
            <tr>
                <td>${hike.name}</td>
                <td>${hike.description}</td>
                <td>${hike.difficulty}</td>
                <td>${hike.startPoint}</td>
                <td>${hike.endPoint}</td>
                <td>${hike.distance}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<form action="Logout" method="post">
    <input type="submit" value="Logout">
</form>
</body>
</html>
