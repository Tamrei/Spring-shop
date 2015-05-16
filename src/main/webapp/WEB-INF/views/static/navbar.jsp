<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true" %>
<%@taglib prefix="sec"
          uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <style>
        <%@ include file="../../../resources/css/bootstrap.min.css" %>
        <%@ include file="../../../resources/css/custom.css" %>
    </style>
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>

</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <ul class="nav navbar-nav">

            <li role="presentation"><a href="/shop">Shop</a></li>

            <li role="presentation"><a href="/cart">Cart <sec:authorize access="isAuthenticated()"> <span
                    class="badge"> ${cartCount} </span> </sec:authorize> </a></li>

            <li role="presentation"><a href="/myPurchases">My purchases</a></li>

            <li role="presentation"><a href="/purchases">All purchases</a></li>
    <sec:authorize access="hasRole('ADMIN')">
            <li role="presentation"><a href="/addItem">Add new Item</a></li>
    </sec:authorize>
            <li role="presentation"><a href="/users">Users</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <c:choose>
                <c:when test="${empty pageContext.request.userPrincipal}">
                    <!--<li role="presentation"><a href="/login"> You are not Authorized! Pls Login!</a></li>-->
                    <li role="presentation"><a data-toggle="modal" href="/registration">Registration</a></li>
                    <li role="presentation"><a data-toggle="modal" href="#login">Login</a></li>
                </c:when>
                <c:otherwise>
                    <li role="presentation"><a href="#"> Hi, ${pageContext.request.userPrincipal.name} </a></li>
                    <li role="presentation"><a href="<c:url value="j_spring_security_logout"/>"> Logout </a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>



        <!-- our registration form -->
<div id="login" class="modal fade">
    <div class="modal-dialog" style="padding-top: 165px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">x
                </button>
                <h4 id="modal-label2">Login with username and password</h4>
            </div>
            <div class="modal-body">

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

                    <button type="submit" name="submit" value="submit" class="btn btn-default">Submit</button>

                    <input type="hidden" name="${_csrf.parameterName}" class="btn btn-primary"
                           value="${_csrf.token}"/>

                    <a href="/registration" class="btn btn-primary"> Registration </a>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- our buy form -->



</body>
</html>
