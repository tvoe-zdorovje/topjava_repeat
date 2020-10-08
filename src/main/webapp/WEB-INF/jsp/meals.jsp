<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meals.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-4">
    <div class="container">
        <div class="text-center">
            <h3><spring:message code="meal.title"/></h3>
        </div>
        <div class="card border-dark">
            <div class="card-body pb-0">
                <form class="filter">
                    <div class="form-row align-items-center">
                        <div class="form-group col-md-3">
                            <label for="startDate" class="col-form-label"> <spring:message code="meal.startDate"/>:</label>
                            <input id="startDate" class="form-control" type="date" name="startDate">
                        </div>
                        <div class="form-group col-md-3">
                            <label for="endDate" class="col-form-label"> <spring:message code="meal.endDate"/>:</label>
                            <input id="endDate" class="form-control" type="date" name="endDate">
                        </div>
                        <div class="form-group col-md-3">
                            <label for="startTime" class="col-form-label"> <spring:message
                                    code="meal.startTime"/>:</label>
                            <input id="startTime" class="form-control" type="time" name="startTime">
                        </div>
                        <div class="form-group col-md-3">
                            <label for="endTime" class="col-form-label"> <spring:message code="meal.endTime"/>:</label>
                            <input id="endTime" class="form-control" type="time" name="endTime">
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-footer text-right">
                <button class="btn btn-danger" onclick="resetFilter()">
                    <span class="fa fa-remove"></span> <spring:message code="common.cancel"/>
                </button>
                <button class="btn btn-primary" onclick="filter()">
                    <span class="fa fa-filter"></span> <spring:message code="meal.filter"/>
                </button>
            </div>
        </div>
        <br>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span> <spring:message code="common.add"/></button>
        <table id="datatable" class="table table-striped">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>

    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title"><spring:message code="meal.add"/></h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="detailsForm" class="was-validated">
                    <input type="hidden" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="meal.dateTime"/></label>
                        <input type="datetime-local" class="form-control" id="dateTime" name="dateTime"
                               placeholder="<spring:message code="meal.dateTime"/>" required>
                    </div>
                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message code="meal.description"/></label>
                        <input type="text" class="form-control" id="description" name="description"
                               placeholder="<spring:message code="meal.description"/>" required>
                    </div>
                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                        <input type="number" min="10" max="5000" class="form-control" id="calories" name="calories"
                               placeholder="<spring:message code="meal.calories"/>" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-secondary" data-dismiss="modal"><spring:message code="common.cancel"/></button>
                <button class="btn btn-primary" onclick="save()"><spring:message code="common.save"/></button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>