<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add new item</title>
    <jsp:include page="static/staticFiles.jsp"/>
    <script src="<c:url value="/resources/js/validation/addNewItemFormValidator.js" />"></script>
    <script>
        $(document).ready(function () {
            validateAddNewItemForm();
        });
    </script>
</head>
<body>
<div class="container">

    <jsp:include page="static/navbar.jsp"/>

    <div class="col-md-6 col-md-offset-3">

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

        <div class="panel">

            <div class="panel-heading">
                <div class="row">
                    <h3 align="center">Add new item</h3>
                </div>
                <hr>
            </div>

            <div class="panel-body">
                <form:form method="post" action="createItem" commandName="item" role="form"
                           enctype="multipart/form-data" id="item-form">

                    <div class="form-group has-feedback" id="itemName-form">
                        <label class="control-label" for="itemName" id="itemName-label"> Item Name: </label>
                        <input type="text" name="itemName" class="form-control" placeholder="Enter item name"
                               id="itemName">
                    </div>

                    <div class="form-group has-feedback" id="type-form">
                        <label class="control-label" for="type" id="type-label"> Item Type: </label>
                        <input type="text" name="type" class="form-control" placeholder="Enter type" id="type">
                    </div>

                    <div class="form-group has-feedback" id="price-form">
                        <label class="control-label" for="price" id="price-label"> Item price (by default 0): </label>
                        <input type="number" name="price" class="form-control bfh-number" placeholder="Enter Item Price"
                               value="1" id="price">
                    </div>

                    <div class="form-group has-feedback" id="leftOnStore-form">
                        <label class="control-label" for="leftOnStore" id="leftOnStore-label"> Items in stock (by
                            default 0): </label>
                        <input type="number" name="leftOnStore" class="form-control bfh-number" placeholder="" value="0"
                               id="leftOnStore">
                    </div>

                    <div class="form-group has-feedback" id="image-form">
                        <label class="control-label" for="image" id="image-label">Item Image:</label>
                        <input type="file" name="file" class="form-control" accept="image/*" id="image">
                    </div>

                    <button type="submit" class="btn btn-primary" name="submit" value="submit">Submit</button>
                </form:form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="static/footer.jsp" flush="true"/>

</body>
</html>
