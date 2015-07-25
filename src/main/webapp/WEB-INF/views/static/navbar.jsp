<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@page session="true" %>

<html>
<head>
    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
    <script src="<c:url value="/resources/js/cartCount.js" />"></script>
</head>
<body>

<sec:authorize access="isAuthenticated()">
    <script>
        $(document).ready(function () {
            getCartCount('cartCount');
        });
    </script>
</sec:authorize>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">

        <div class="navbar-header">
            <a class="navbar-brand" href="/shop">Shop</a>
        </div>

        <ul class="nav navbar-nav">
            <!-- Popover -->
            <li role="presentation"><a href="/cart" id="navCart" data-original-title="" data-content="" data-placement="bottom" rel="popover">Cart <sec:authorize access="isAuthenticated()"> <span
                    class="badge" id="cartCount"> </span> </sec:authorize> </a></li>
            <!-- Popover -->

            <li role="presentation"><a href="/myPurchases">My purchases</a></li>

            <li role="presentation"><a href="/users">Users</a></li>

            <sec:authorize access="hasRole('ADMIN')">
                <li class="dropdown">
                    <a href="#" data-toggle="dropdown" class="dropdown-toggle"> Admin tools <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="/carouselController">Carousel controller</a></li>
                        <li><a href="/addItem">Add new item</a></li>
                        <li><a href="/purchases">All orders</a></li>
                        <li><a href="/city"> Available cities </a></li>
                        <li class="divider"></li>
                        <li><a href="/store">Store (alpha)</a></li>
                        <li><a href="/statistic">Statistic (alpha)</a></li>
                    </ul>
                </li>
            </sec:authorize>

        </ul>
        <ul class="nav navbar-nav navbar-right">
            <c:choose>
                <c:when test="${empty pageContext.request.userPrincipal}">
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


<!-- Login modal -->
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

                    <hr>

                    <button type="submit" name="submit" value="submit" class="btn btn-primary"> Submit </button>

                    <input type='hidden' name='spring-security-redirect' value='${params['spring-security-redirect']}'/>

                    <!--
                    <input type="hidden" name="${_csrf.parameterName}" class="btn btn-primary"
                           value="${_csrf.token}"/>
                           -->


                    <a href="/registration" class="btn btn-link"> Don't have account? Registration </a>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Login modal -->


</body>
</html>
