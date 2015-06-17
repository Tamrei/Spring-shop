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
    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script>

    </script>
    <title>My purchase</title>
</head>

<body>

<div class="container">

    <jsp:include page="static/navbar.jsp" flush="true"/>

    <div class="panel panel-login">
        <div class="panel-body">

            <!-- Page Header -->
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"> Order </h2>
                </div>
            </div>

            <div class="table">
                <table class="table table-curved">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Item Name</th>
                        <th>Amount</th>
                        <th>Price</th>
                    </tr>
                    </thead>
                    <tbody>

                    <script>
                        var price = 0;
                    </script>

                    <c:forEach items="${purchases}" var="purchase">
                        <tr>
                            <td><img src="/shop/img/${purchase.key.itemID}" alt="image with rounded corners" width="120" height="75"></td>
                            <td> ${purchase.key.itemName} </td>
                            <td> ${purchase.value.amount} </td>
                            <td>
                                <p> Price for ${purchase.value.amount}
                                    is ${purchase.key.price * purchase.value.amount} </p>

                                <p style="color: green"> Current price per one: ${purchase.key.price} </p>
                            </td>
                        </tr>

                        <script>
                            var pricePerOne = ${purchase.key.price};
                            var amount = ${purchase.value.amount};
                            var totalPrice = pricePerOne * amount;

                            price += totalPrice;

                        </script>

                    </c:forEach>

                    </tbody>
                </table>
            </div>

            <h3 id="totalPrice" style="color: darkgreen"> Empty </h3>
            <script>
                $("#totalPrice").text("Total price: " + price + "$");
            </script>
            <div class="table">
                <table class="table table-curved">
                    <thead>
                    <tr>
                        <th>Address ID</th>
                        <th>City</th>
                        <th>Street</th>
                        <th>Customer Name</th>
                    </tr>
                    </thead>
                    <tbody>

                    <tr>
                        <td> ${address.addressID} </td>
                        <td> ${address.city} </td>
                        <td> ${address.street} </td>
                        <td> ${address.ownerUsername} </td>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>