<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="a" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="td" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Users</title>
    <style>
        <%@ include file="../../resources/css/bootstrap.css" %>
        <%@ include file="../../resources/css/custom.css" %>
        <%@ include file="../../resources/css/panel.css" %>

    </style>

    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script src="<c:url value="/resources/js/search.js" />"></script>

</head>
<body>

<div class="container">

    <jsp:include page="static/navbar.jsp" flush="true"/>

    <div class="panel">
        <div class="panel-body">

            <input type="search" id="searchByUsername" class="form-control" style="margin-bottom:15px;"
                   placeholder="Search by username" onkeyup="searchValue('#username', this.id)">

            <div class="table">
                <table class="table table-curved table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <sec:authorize access="hasRole('ADMIN')">
                            <th>Password</th>
                        </sec:authorize>
                        <th>Role</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${users}" var="user">

                        <script>
                            alert('${user.username}' + " +");
                        </script>

                        <tr>
                            <td> ${user.id} </td>
                            <td id="username"> ${user.username} </td>
                            <sec:authorize access="hasRole('ADMIN')">
                                <td> ${user.password} </td>
                            </sec:authorize>
                            <td> ${user.role} </td>

                            <c:choose>
                                <c:when test="${user.enabled == true}">
                                    <td><font color="blue"> enabled </font></td>
                                </c:when>
                                <c:otherwise>
                                    <td><font color="red"> disabled </font></td>
                                </c:otherwise>
                            </c:choose>

                            <sec:authorize access="hasRole('ADMIN')">
                                <c:set var="userName"><security:authentication property="name"/></c:set>
                                <!-- control pane -->
                                <td>
                                    <c:choose>
                                    <c:when test="${userName == user.username}">
                                        <p>Sorry, but you cant </p>

                                        <p>delete or disable yourself</p>
                                    </c:when>
                                    <c:otherwise>
                                    <div class="btn-group">
                                        <form:form action="http://localhost:8080/users/${user.id}">
                                        <input type="submit" name="submit" class="btn btn-default"
                                               value="enable/disable">
                                        </form:form>

                                        <form:form method="delete" action="users/delete/${user.id}">
                                        <input type="submit" name="submit" class="btn btn-default" value="delete">
                                        </form:form>
                                        <div class="btn-group">
                                            <!--<div class="btn-group">
                                    <button type="button" data-toggle="dropdown" class="btn btn-default"> Options <span
                                    class="caret"></span></button>
                                    <ul class="dropdown-menu">
                                    <li><a href="#">Action</a></li>
                                    <form:form action="http://localhost:8080/users/${user.id}" method="post"
                                               commandName="customer">
                                        <li><input type="submit" name="submit" class="btn btn-default"
                                        value="enable/disable"></li>
                                    </form:form>
                                    <li>

                                    </li>
                                    </ul>
                                    </div>-->

                                            </c:otherwise>

                                            </c:choose>
                                            <!-- control pane -->
                                </td>

                            </sec:authorize>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
</html>

