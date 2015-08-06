<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Registration</title>
    <jsp:include page="static/staticFiles.jsp"/>
    <script src="<c:url value="/resources/js/validation/registrationFormValidator.js" />"></script>
    <script src="<c:url value="/resources/js/validation/validationMarkup.js" />"></script>
    <script>
        $(document).ready(function () {
            validateRegistrationForm();
        });
    </script>
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

            <div class="bs-example hidden" id="FormsAreEmpty">
                <div class="alert alert-danger alert-error">
                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                    <strong>Error!</strong> Please fill all fields.
                </div>
            </div>

            <div class="panel">
                <!-- Page header -->
                <div class="panel-heading">
                    <div class="row">
                        <h3 align="center">Registration</h3>
                    </div>
                    <hr>
                </div>
                <!-- Page header -->

                <div class="panel-body">

                    <form action="createUser" commandName="customer" method="post" role="form"
                          id="registration-form">

                        <div class="form-group has-feedback" id="username-form">
                            <label class="control-label" for="username" id="username-label">Username:</label>

                            <div class="controls">
                                <input type="text" name="username" class="form-control"
                                       placeholder="Enter Username" id="username">
                            </div>
                        </div>

                        <div class="form-group has-feedback" id="password-form">
                            <label class="control-label" for="password" id="password-label">Password:</label>

                            <div class="controls">
                                <input type="password" name="password" class="form-control"
                                       placeholder="Enter Password" id="password">
                            </div>
                        </div>

                        <div class="form-group has-feedback" id="confirmPassword-form">
                            <label class="control-label" for="confirmPassword" id="confirmPassword-label">Confirm Password:</label>

                            <div class="controls">
                                <input type="password" class="form-control"
                                       placeholder="Enter Password" id="confirmPassword">
                            </div>
                        </div>

                        <br>

                        <div class="form-group">
                            <button type="submit" name="submit" value="submit" class="btn btn-primary btn-block">
                                Submit
                            </button>

                            <!--
                            <input type="hidden" name="${_csrf.parameterName}" class="btn btn-primary"
                                   value="${_csrf.token}"/>
                            -->

                            <a href="/login" style="text-align: center; display: block; padding-top: 15px">
                                Login </a>
                        </div>
                        <hr>

                    </form>
<!--

-->
                </div>
            </div>
        </div>
    </div>
</div>


<jsp:include page="static/footer.jsp" flush="true"/>

</body>
</html>
