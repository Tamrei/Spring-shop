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
    <jsp:include page="static/staticFiles.jsp"/>
    <script src="<c:url value="/resources/js/view/user.js" />"></script>
</head>
<body>

<div class="container">

    <jsp:include page="static/navbar.jsp" flush="true"/>

    <div class="panel">
        <div class="panel-body">

            <!-- Page Header -->
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header">Home page controller
                        <small></small>
                    </h2>
                </div>
            </div>
            <!-- Page Header -->

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
                        <tr id="table${user.id}">
                            <td> ${user.id} </td>
                            <td id="username"> ${user.username} </td>
                            <sec:authorize access="hasRole('ADMIN')">
                                <td> ${user.password} </td>
                            </sec:authorize>
                            <td> ${user.role} </td>

                            <td id="status${user.id}"></td>

                            <script>
                                function setStatusColor() {
                                    var status = $("#status${user.id}");

                                    if (${user.enabled}) {
                                        status.css("color", "darkgreen").text("enabled");
                                    } else {
                                        status.css("color", "darkred").text("disabled");
                                    }
                                }
                            </script>

                            <script>
                                setStatusColor();
                            </script>


                            <sec:authorize access="hasRole('ADMIN')">
                                <c:set var="userName"><security:authentication property="name"/></c:set>
                                <!-- control panel -->
                                <td>
                                    <c:choose>
                                        <c:when test="${userName == user.username}">
                                            <p>Sorry, but you cant delete or disable yourself.</p>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="dropdown">
                                                <button class="btn btn-default dropdown-toggle" type="button"
                                                        data-toggle="dropdown"> <span class="glyphicon glyphicon-cog"></span> Options
                                                    <span class="caret"></span></button>
                                                <ul class="dropdown-menu">
                                                    <li onclick="deleteUser(${user.id})"><a href="#">Delete</a></li>
                                                    <li onclick="enableDisableUser(${user.id})"><a href="#">Enable/Disable</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </c:otherwise>

                                    </c:choose>
                                    <!-- control panel -->
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

<jsp:include page="static/footer.jsp" flush="true"/>

</body>
</html>

