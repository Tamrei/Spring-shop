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
    <title>My purchase</title>
    <jsp:include page="static/staticFiles.jsp"/>
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
            <!-- Page Header -->

            <div class="col-md-9 content" style="margin-top: 20px">
                <div class="table">
                    <table class="table table-curved">
                        <thead>
                        <tr>
                            <th></th>
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
                                <td><img src="/shop/img/${purchase.key.itemID}" alt="image with rounded corners"
                                         width="120" height="75"></td>
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

                <hr>

            </div>
            <div class="col-md-3 sidebar" style="margin-top: 45px">
                <h4><strong> Address ID: </strong> ${address.addressID} </h4>
                <h4><strong> City: </strong> ${address.city}</h4>
                <h4><strong> Street: </strong> ${address.street}</h4>
                <h4><strong> Customer Name: </strong> ${address.ownerUsername}</h4>

                <h3 id="totalPrice" style="color: darkgreen"> Empty </h3>
                <script>
                    $("#totalPrice").text("Total price: " + price + "$");
                </script>

            </div>
        </div>
    </div>
</div>

<jsp:include page="static/footer.jsp" flush="true"/>

</body>
</html>