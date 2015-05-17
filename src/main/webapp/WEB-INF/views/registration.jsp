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
        <%@ include file="../../resources/css/box.css" %>

        #center {
            margin-left: auto;
            margin-right: auto;
            margin-top: 5%;
            width: 25%;
        }

        #border {
            border-radius: 4px;
        }

        #center_input {

        }

        .panel-registratin {
            border-color: #ccc;
            -webkit-box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.2);
            -moz-box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.2);
            box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.2);
        }

    </style>

    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
</head>
<body>
<div class="container">



    <div class="row">
        <div class="col-md-6 col-md-offset-3">

            <c:if test="${!empty someError }">
                <div class="bs-example">
                    <div class="alert alert-danger alert-error">
                        <a href="#" class="close" data-dismiss="alert">&times;</a>
                        <strong>Error!</strong> Some errors in your registration data.
                    </div>
                </div>
            </c:if>

            <c:if test="${!empty userExist }">
                <div class="error"><c:out value="${userExist}"/></div>
                <div class="bs-example">
                    <div class="alert alert-danger alert-error">
                        <a href="#" class="close" data-dismiss="alert">&times;</a>
                        <strong>Error!</strong> <c:out value="${userExist}"/>
                    </div>
                </div>
            </c:if>

            <div class="panel panel-login">
                <div class="panel-heading">
                    <div class="row">
                        <h3 align="center">Registration</h3>
                    </div>
                    <hr>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form:form action="createUser" commandName="customer" method="post" role="form"
                                       style="display: block;">
                                <!--
                                <div class="form-group">
                                <input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="">
                                </div>
                                <div class="form-group">
                                <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password">
                                </div>
                                -->

                                <div class="form-group error">
                                    <label>Username:</label>
                                    <input type="text" name="username" class="form-control"
                                           placeholder="Enter Username">
                                    <span class="help-inline" style="color: red"><form:errors path="username"/></span>
                                </div>
                                <div class="form-group error">
                                    <label>Password:</label>
                                    <input type="password" name="password" class="form-control"
                                           placeholder="Enter password">
                                    <span class="help-inline" style="color: red"><h5><form:errors path="username"/></h5></span>

                                </div>

                                <div class="form-group text-center">

                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 ">
                                            <!--
                                            <input type="submit" name="login-submit" id="login-submit" tabindex="4"
                                                   class="form-control btn btn-primary" value="Registration">
                                            <a href="/registration" class="form-control btn btn"> Login </a>
                                            -->

                                            <button type="submit" name="submit" value="submit" class="btn btn-default">Submit</button>

                                            <input type="hidden" name="${_csrf.parameterName}" class="btn btn-primary"
                                                   value="${_csrf.token}"/>

                                            <a href="/login" class="btn btn-primary"> Login </a>
                                        </div>
                                    </div>
                                </div>
                                <!--<div class="form-group">
                                    <div class="row">
                                        <div class="col-lg-12">

                                        </div>
                                    </div>
                                </div>-->
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
