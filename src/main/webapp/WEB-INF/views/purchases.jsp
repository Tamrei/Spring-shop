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
    <title>All Purchases</title>
    <jsp:include page="static/staticFiles.jsp"/>
</head>
<body>

<div class="container">

    <jsp:include page="static/navbar.jsp"/>

    <div class="panel">
        <div class="panel-body">

            <!-- Page Header -->
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"> All purchases </h2>
                </div>
            </div>
            <!-- Page Header -->

            <div class="table">
                <table class="table table-curved table-hover" id="purchase">
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
                            <td><a class="btn btn-default" data-toggle="modal" href="#update${purchase.purchaseID}"
                                   id="btn${purchase.purchaseID}">Change
                                status</a></td>
                        </tr>

                        <!-- Update purchase status modal -->
                        <div id="update${purchase.purchaseID}" class="modal fade">
                            <div class="modal-dialog" style="padding-top: 165px;">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-hidden="true">x
                                        </button>
                                        <h4 id="modal-label">Update purchase status</h4>
                                    </div>
                                    <div class="modal-body">
                                        <form:form method="post" action="purchases/update/${purchase.purchaseID}">
                                            <select name="status" class="form-control">
                                                <c:forEach items="${orderStatus}" var="status">
                                                    <option value="${status}">${status}</option>
                                                </c:forEach>
                                            </select>
                                            <br>
                                            <div class="modal-footer">
                                                <button id="sub" class="btn btn-primary" type="submit"> Submit</button>
                                            </div>
                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Update purchase status modal -->

                        <script>
                            var isThisAStatusButton = false;

                            $('#btn${purchase.purchaseID}').on('click', function () {
                                isThisAStatusButton = true;
                            });

                            $("#order${purchase.purchaseID}").on('click', function () {
                                if (!isThisAStatusButton) {
                                    window.location = "purchases/${purchase.purchaseID}";
                                }
                                isThisAStatusButton = false;
                            });

                            //isThisAStatusButton = false;
                        </script>
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
