<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page session="true" %>

<html>
<head>
    <title>Login Page</title>
    <style>
        <%@ include file="../../resources/css/bootstrap.css" %>
        <%@ include file="../../resources/css/custom.css" %>
        <%@ include file="../../resources/css/box.css" %>
    </style>
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
</head>
<body>

<div class="container">

    <div class="row">
        <div class="col-md-6 col-md-offset-3">

            <c:if test="${not empty error}">
                <div class="bs-example">
                    <div class="alert alert-danger alert-error">
                        <a href="#" class="close" data-dismiss="alert">&times;</a>
                        <strong>Error!</strong> <c:out value="${error}"/>
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty success}">
                <div class="bs-example">
                    <div class="alert alert-success">
                        <a href="#" class="close" data-dismiss="alert">&times;</a>
                        <strong>Success!</strong> <c:out value="${success}"/>
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty logout}">
                <div class="bs-example">
                    <div class="alert alert-success">
                        <a href="#" class="close" data-dismiss="alert">&times;</a>
                        <strong>Success!</strong> <c:out value="${logout}"/>
                    </div>
                </div>
            </c:if>

            <div class="panel panel-login">
                <div class="panel-heading">
                    <div class="row">
                        <h3 align="center">Login</h3>
                    </div>
                    <hr>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form role="form"
                                  action="<c:url value='/j_spring_security_check' />" method="post">

                                <div class="form-group">
                                    <label>Username:</label>
                                    <input type="text" name="username" class="form-control" placeholder="Enter Username">
                                </div>
                                <div class="form-group">
                                    <label>Password:</label>
                                    <input type="password" class="form-control" name="password" placeholder="Enter password">
                                </div>

                                <div class="form-group text-center">

                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 ">

                                            <button type="submit" name="submit" value="submit" class="btn btn-default">Submit</button>

                                            <input type="hidden" name="${_csrf.parameterName}" class="btn btn-primary"
                                                   value="${_csrf.token}"/>

                                            <a href="/registration" class="btn btn-primary"> Registration </a>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>