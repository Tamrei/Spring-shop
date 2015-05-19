<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="true" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <style>
        <%@ include file="../../resources/css/bootstrap.css" %>
        <%@ include file="../../resources/css/custom.css" %>
    </style>
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
    <script src="<c:url value="/resources/js/search.js" />"></script>

    <title>All Purchases</title>
</head>

<body style="padding: 1rem;">

<div class="container">

    <jsp:include page="${request.contextPath}/navbar"></jsp:include>

    <div class="panel panel-login">
        <div class="panel-body">

            <!-- Page Header -->
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"> All purchases </h2>
                </div>
            </div>

            <div class="table">
                <table class="table table-curved table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Street</th>
                        <th>City</th>
                        <th>Customer Name</th>
                        <th>Status</th>

                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${purchases}" var="purchase">
                        <tr id="order${purchase.purchaseID}">
                            <td id="purchaseID"> ${purchase.purchaseID} </td>
                            <td> ${purchase.address.street} </td>
                            <td> ${purchase.address.city} </td>
                            <td> ${purchase.ownerUsername} </td>
                            <td> ${purchase.status}
                            <td><a class="btn btn-default" data-toggle="modal" href="#update${purchase.purchaseID}">Change
                                status</a><br></td>
                        </tr>


                        <!-- out update form -->
                        <div id="update${purchase.purchaseID}" class="modal fade">
                            <div class="modal-dialog" style="padding-top: 165px;">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-hidden="true">x
                                        </button>
                                        <h4 id="modal-label">Update</h4>
                                    </div>
                                    <div class="modal-body">
                                        <form:form method="post" action="address/update/${purchase.purchaseID}">

                                            <select name="status">
                                                <c:forEach items="${orderStatus}" var="status">
                                                    <option value="${status}">${status}</option>
                                                </c:forEach>
                                            </select>
                                            <br>

                                            <button class="btn btn-default" type="submit" id="sub">Submit</button>
                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- out update form -->

                        <script>
                            $("#order${purchase.purchaseID}").on('click', function () {
                                window.location = "purchases/${purchase.purchaseID}";
                            });

                        </script>

                    </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
