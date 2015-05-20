<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add new item</title>
    <style>
        <%@ include file="../../resources/css/bootstrap.css" %>
        <%@ include file="../../resources/css/custom.css" %>
        <%@ include file="../../resources/css/box.css" %>

        #center {
            margin-left: auto;
            margin-right: auto;
            margin-top: 5%;
            width: 25%;
        }
    </style>

    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>

    <title> Add new item </title>
</head>
<body>
<div class="container" id="center">

    <c:if test="${not empty notAnImage}">
        <div class="bs-example">
            <div class="alert alert-danger alert-error">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>Error!</strong> <c:out value="${notAnImage}"/>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty invalidInputData}">
        <div class="bs-example">
            <div class="alert alert-danger alert-error">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>Error!</strong> <c:out value="${invalidInputData}"/>
            </div>
        </div>
    </c:if>

    <div class="panel panel-login">
        <div class="panel-heading">
            <div class="row">
                <h3 align="center">Add new item</h3>
            </div>
            <hr>
        </div>

        <div class="panel-body">
            <form:form method="post" action="createItem" commandName="item" role="form" enctype="multipart/form-data">
                <div class="form-group">
                    <label>Item Name: </label>
                    <input type="text" name="itemName" class="form-control" placeholder="Enter item name">
                    <span class="help-inline" style="color: red"><form:errors path="itemName"/></span>
                </div>

                <div class="form-group">
                    <label>Item Type: </label>
                    <input type="text" name="type" class="form-control" placeholder="Enter type">
                    <span class="help-inline" style="color: red"><form:errors path="type"/></span>
                </div>

                <div class="form-group">
                    <label>Item price: </label>
                    <input type="number" name="price" class="form-control bfh-number" placeholder="Enter Item Price">
                    <span class="help-inline" style="color: red"><form:errors path="price"/></span>
                </div>

                <div class="form-group">
                    <label>Item Image:</label>
                    <input type="file" name="file" class="form-control" accept="image/*">
                </div>

                <button type="submit" class="btn btn-primary">Submit</button>
            </form:form>
        </div>
    </div>

</div>
</body>
</html>
