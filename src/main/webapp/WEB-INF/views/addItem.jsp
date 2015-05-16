<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Registration</title>
    <style>
        <%@ include file="../../resources/css/bootstrap.css" %>
        <%@ include file="../../resources/css/custom.css" %>

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

    <h3>Add new Item</h3>

        <form:form method="post" action="createItem" commandName="item" enctype="multipart/form-data">
            <div class="form-group">
                <label>Item Name: </label>
                <input type="text" name="itemName" class="form-control" placeholder="Enter item name">
            </div>

            <div class="form-group">
                <label>Item Type: </label>
                <input type="text" name="type" class="form-control" placeholder="Enter item name">
            </div>

            <div class="form-group">
                <label>Item price: </label>
                <input type="number" name="price" class="form-control bfh-number" placeholder="Enter Item Price">
            </div>

            <div class="form-group">
                <label>Item Image:</label>
                <input type="file" name="file" class="form-control">
            </div>

            <button type="submit" class="btn btn-primary">Submit</button>
        </form:form>

</div>
</body>
</html>
