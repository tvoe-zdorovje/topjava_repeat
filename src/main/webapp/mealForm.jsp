<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${requestScope.get("action")} meal</title>
    <link rel="stylesheet" href="css/stylesheet.css">
</head>
<body>
<h2>${requestScope.get("action")}</h2>
<form method="post" action="${pageContext.request.contextPath}/meals">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo" scope="request">${requestScope.meal}</jsp:useBean>
    <label>
        <input type="hidden" name="id" value="${meal.id}">
    </label>
    <label>dateTime<br>
        <input type="datetime-local" name="dateTime" value="${meal.dateTime}" required><br>
    </label>
    <label>Description<br>
        <input type="text" name="description" value="${meal.description}" required><br>
    </label>
    <label>Calories<br>
        <input type="number" name="calories" value="${meal.calories}" min="10" max="5000" required><br>
    </label>
    <input type="submit">
</form>
</body>
</html>
