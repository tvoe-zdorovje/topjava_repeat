<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }

        ul {
            list-style: none;
            display: inline-flex;
            padding: 10px;
        }
        li {
            padding: 5px 20px;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <form method="get">
        <ul>
            <li><label for="startDate">с даты (включая)</label><br>
                <input type="date" name="startDate" id="startDate" class="filter" value="${param.getOrDefault('startDate', "")}"></li>
            <li><label for="endDate">до даты (включая)</label><br>
                <input type="date" name="endDate" id="endDate" class="filter" value="${param.getOrDefault('endDate', "")}"></li>
            <li><label for="startTime">с времени (включая)</label><br>
                <input type="time" name="startTime" id="startTime" class="filter" value="${param.getOrDefault('startTime', "")}"></li>
            <li><label for="endTime">до времени</label><br>
                <input type="time" name="endTime" id="endTime" class="filter" value="${param.getOrDefault('endTime', "")}"></li>
        </ul>
        <br>
        <button type="submit" name="action" value="filter">Найти</button>
    </form>
        <a href="meals"><button>Отмена</button></a>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>