<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Registration</title>
    <style>
        <%@ include file="../../resources/css/bootstrap.min.css" %>
        <%@ include file="../../resources/css/custom.css" %>
        <%@ include file="../../resources/css/box.css" %>

        .success-color {
            border-color: #3c763d;
        }

        .error-color {
            border-color: #a94442;
        }

    </style>

    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
    <script src="<c:url value="/resources/js/jquery.validate.js" />"></script>
    <script>
        $(document).ready(function () {

            var usernameForm = $('#username-form');
            var isNameValid = false;

            var passwordForm = $('#password-form');
            var isPasswordValid = false;

            var isFormValid = false;

            /**
             *  username form
             */
            $('#username').on('input', function () {
                var input = $(this);
                var is_name = input.val();

                isNameValid = (is_name.length > 2);

                if (isNameValid) {
                    usernameForm.removeClass('has-error').addClass('has-success');
                    input.css("border-color", "#3c763d");
                }
                else {
                    usernameForm.removeClass('has-success').addClass('has-error');
                    input.css("border-color", "#a94442");
                }
            });
            /**
             *  password form
             */
            $('#password').on('input', function () {
                var input = $(this);
                var is_name = input.val();

                isPasswordValid = (is_name.length > 3);

                if (isPasswordValid) {
                    passwordForm.removeClass('has-error').addClass('has-success');
                    input.css("border-color", "#3c763d");
                }
                else {
                    passwordForm.removeClass('has-success').addClass('has-error');
                    input.css("border-color", "#a94442");
                }
            });

            $("#registration-form").submit(function (e) {
                isFormValid = isNameValid && isPasswordValid;
                return isFormValid;
            });
        });

    </script>
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
                            <form action="createUser" commandName="customer" method="post" role="form"
                                  id="registration-form" >

                                <div class="form-grou[ has-feedback" id="username-form">
                                    <label for="username">Username:</label>

                                    <div class="controls">
                                        <input type="text" name="username" class="form-control"
                                               placeholder="Enter Username" id="username">
                                    </div>
                                    <!--<span class="glyphicon glyphicon-remove form-control-feedback"></span>-->
                                </div>

                                <div class="form-group has-feedback" id="password-form">
                                    <label for="password">Password:</label>

                                    <div class="controls">
                                        <input type="password" name="password" class="form-control"
                                               placeholder="Enter Password" id="password">
                                    </div>
                                </div>


                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 ">

                                            <button type="submit" name="submit" value="submit" class="btn btn-primary">
                                                Submit
                                            </button>

                                            <input type="hidden" name="${_csrf.parameterName}" class="btn btn-primary"
                                                   value="${_csrf.token}"/>

                                            <a href="/login" class="btn btn-default"> Login </a>
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
