<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true" %>
<%@taglib prefix="sec"
          uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="h3" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="td" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <style>
        <%@ include file="../../resources/css/bootstrap.css" %>
        <%@ include file="../../resources/css/custom.css" %>
        <%@ include file="../../resources/css/panel.css" %>

    </style>

    <style>

    </style>

    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>

    <title> Store </title>
</head>

<body>

<div class="container">

    <jsp:include page="static/navbar.jsp" flush="true"/>

    <div class="panel">
        <div class="panel-body">

            <!-- Page Header -->
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"> Order </h2>
                </div>
            </div>

            <input type="search" id="searchByItemName" class="form-control" style="margin-bottom:15px;"
                   placeholder="Search by item name" onkeyup="searchValue('#itemName', this.id)">


            <c:forEach items="${items}" var="item">
                <div class="panel panel-default">
                    <div class="panel-heading">

                        <div id="flip${item.itemID}">
                            <table class="table borderless">
                                <tr>
                                    <td><img src="/shop/img/${item.itemID}" width="120" height="75"></td>
                                    <td><strong>ID: </strong> ${item.itemID} </td>
                                    <td id="itemName"><strong>Item Name: </strong> ${item.itemName} </td>
                                    <td><strong>Left on store: </strong> ${item.leftOnStore} </td>
                                    <div id="test">
                                    <td><a data-toggle="modal" role="button" class="btn btn-default"
                                           href="#addNew${item.itemID}"> Add delivery </a>

                                        <a data-toggle="modal" role="button" class="btn btn-default"
                                           href="#addNew${item.itemID}"> Add delivery </a>

                                    </td>
                                    </div>
                                </tr>
                            </table>
                        </div>
                    </div>

                    <div id="panel${item.itemID}">
                        <table class="table">

                            <th>Product</th>
                            <th>Price</th>
                            <c:forEach items="${item.itemDeliveries}" var="deliveries">

                                <tr>
                                    <td>${deliveries.itemsLeft}</td>
                                    <td>${deliveries.dateOfDelivery}</td>
                                </tr>

                            </c:forEach>

                        </table>

                    </div>

                </div>
                <script>
                    $("#flip${item.itemID}").not("#test").click(function () {
                        $("#panel${item.itemID}").slideToggle("slow");
                    });
                </script>


                <!-- out update form -->
                <div id="addNew${item.itemID}" class="modal fade">
                    <div class="modal-dialog" style="padding-top: 165px;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-hidden="true">x
                                </button>
                                <h4 id="modal-label">Add new </h4>
                            </div>
                            <div class="modal-body">
                                <form:form method="post" action="/store/add/${item.itemID}"
                                           commandName="itemDelivery" enctype="multipart/form-data">

                                    <div class="form-group">
                                        <label> Amount </label>
                                        <input type="number" name="itemQuantity" class="form-control"
                                               placeholder="Quntity">
                                    </div>

                                    <button type="submit" class="btn btn-default" id="sub">Submit</button>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- out update form -->

            </c:forEach>


        </div>
    </div>
</div>

</body>
</html>