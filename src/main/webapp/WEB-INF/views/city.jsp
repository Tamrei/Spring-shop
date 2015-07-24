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
    <title>Available Cities</title>
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
                    <h2 class="page-header"> City </h2>
                </div>
            </div>
            <!-- Page Header -->

            <div class="table">
                <table class="table table-curved table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>City</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${cities}" var="city">
                        <tr id="order${purchase.purchaseID}">
                            <td id="cityID"> ${city.id} </td>
                            <td> ${city.cityName} </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>

            <hr>

            <a id="makePurchase" class="btn btn-primary" data-toggle="modal" href="#addCity"> Add City </a><br>

        </div>
    </div>

    <div id="addCity" class="modal fade">
        <div class="modal-dialog" style="padding-top: 165px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">x
                    </button>
                    <h4 id="modal-label">Add new City </h4>
                </div>
                <div class="modal-body">
                    <form:form method="post" action="/city/add"
                               commandName="city" enctype="multipart/form-data">

                        <div class="form-group">
                            <label> City </label>
                            <input type="text" name="cityName" class="form-control" placeholder="City">
                        </div>

                        <hr>

                        <button type="submit" class="btn btn-primary" id="sub">Submit</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>

</div>

<jsp:include page="static/footer.jsp" flush="true"/>

</body>
</html>
