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
    </style>
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
    <script src="<c:url value="/resources/js/search.js" />"></script>
</head>

<body style="padding: 1rem;">
<div class="container">

    <jsp:include page="static/navbar.jsp" flush="true"/>

    <input type="search" id="searchByItemName" class="form-control" style="margin-bottom:15px;"
           placeholder="Search by item name" onkeyup="searchValue('#itemName', this.id)">

    <div class="table">
        <table class="table table-curved">
            <thead>
            <tr>
                <th>IMG</th>
                <th>Name</th>
                <th>Count</th>
                <th>Price</th>
            </tr>
            </thead>
            <tbody>

            <script>
                var price = 0;
            </script>

            <c:forEach items="${carts}" var="purchase">
                <tr>
                    <td><img src="/shop/img/${purchase.key.itemID}" alt="image with rounded corners"
                             class="img-rounded" width="62" height="62"></td>
                    <td id="itemName"> ${purchase.key.itemName} </td>
                    <td> ${purchase.value.amount} </td>
                    <td>
                        <p>Price for ${purchase.value.amount} is ${purchase.key.price * purchase.value.amount}</p>
                        <p style="color: green"> Price per one: ${purchase.key.price} </p>
                    </td>

                    <td>
                        <form:form method="delete" action="cart/layOut/${purchase.value.cartID}">
                            <button type="submit" class="btn btn-default"> Lay out </button>
                        </form:form>
                        <a data-toggle="modal" class="btn btn-default" href="#update${purchase.value.cartID}">Edit form</a><br>
                    </td>
                </tr>

                <!-- out update form -->
                <div id="update${purchase.value.cartID}" class="modal fade">
                    <div class="modal-dialog" style="padding-top: 165px;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
                                <h4 id="update modal-label">Update ${purchase.key.itemName} | Amount
                                    : ${purchase.value.amount}</h4>
                            </div>
                            <div class="modal-body">
                                <form:form method="post" action="/cart/update/${purchase.value.cartID}">

                                    <div class="form-group">
                                        <label>Old Amount: ${purchase.value.amount} </label>
                                        <input type="number" name="amount" class="form-control bfh-number"
                                               value="${purchase.value.amount}">
                                    </div>

                                    <button type="submit" id="sub" class="btn btn-primary">Order</button>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>

                <script>
                    var pricePerOne = ${purchase.key.price};
                    var amount = ${purchase.value.amount};
                    var totalPrice = pricePerOne * amount;

                    price += totalPrice;

                    //$("#price${purchase.key.itemID}").text("Total price: " + totalPrice);

                </script>

            </c:forEach>

            </tbody>
        </table>
    </div>

    <h3 id="totalPrice" style="color: greenyellow"> Test </h3>
    <script>
        $("#totalPrice").text("Total price: " + price + "$");
    </script>

    <a id="makePurchase" class="btn btn-default" data-toggle="modal" href="#order">Make purchase</a><br>

    <script>
        if(price <= 0) {
            $('#makePurchase').hide();
            $('#searchByItemName').hide();
        }
    </script>

    <!-- out update form -->
    <div id="order" class="modal fade">
        <div class="modal-dialog" style="padding-top: 165px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
                    <h4 id="modal-label"> Order </h4>
                </div>
                <div class="modal-body">
                    <form:form action="makePurchase" commandName="address">

                        <div class="form-group">
                            <label> City: </label>
                            <input type="text" name="city" class="form-control"
                                   placeholder="Enter city name">
                        </div>

                        <div class="form-group">
                            <label> Street: </label>
                            <input type="text" name="street" class="form-control"
                                   placeholder="Enter street">
                        </div>

                        <button type="submit" class="btn btn-primary">Order</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <!-- out update form -->

</div>
</body>
</html>
