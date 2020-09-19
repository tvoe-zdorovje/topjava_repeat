<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/stylesheet.css">
</head>
<body>
<h1>MEALS</h1>
<h3><a href="${pageContext.request.contextPath}/meals?action=create">Add</a> </h3>
<table>
    <thead>
    <tr>
        <th>время и дата</th>
        <th>описание</th>
        <th>калории</th>
    </tr>
    </thead>
    <c:forEach items="${requestScope.get('meals')}" var="meal">
        <tr excess="${meal.excess}">
            <td>${fn:formatLocalDateTime(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="${pageContext.request.contextPath}/meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="${pageContext.request.contextPath}/meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
