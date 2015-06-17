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

        .btn-group {
            display: flex;
        }

        img {
            border-radius: 3px;
        }

        .test {
            background-color: #F2DEDE;
        }

    </style>
    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script src="<c:url value="/resources/js/search.js" />"></script>
    <script>

        $(document).ready(function () {
            var url = window.location.href; // get current url
            if (url.endsWith('#error')) {
                $('#order').modal('show');
                $('#invalidFormAlert').show();
            }
            else $('#invalidFormAlert').hide();
        });
    </script>
    <title> My Cart </title>
</head>

<body>
<div class="container">

    <jsp:include page="static/navbar.jsp" flush="true"/>

    <c:if test="${not empty errorE}">
        <div class="bs-example">
            <div class="alert alert-danger alert-error">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>Error!</strong> <c:out value="${errorE}"/>
            </div>
        </div>
    </c:if>

    <div class="bs-example hidden" id="itemsOutOfStore">
        <div class="alert alert-danger alert-error">
            <a href="#" class="close" data-dismiss="alert">&times;</a>
            <strong>Error!</strong> Sorry but you have more items in your cart than we have on our store ;(
        </div>
    </div>

    <div class="panel">
        <div class="panel-body">

            <!-- Page Header -->
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"> My cart </h2>
                </div>
            </div>

            <table class="table table-curved table-hover">

                <thead>
                <tr>
                    <th></th>
                    <th>Item</th>
                    <th>Amount</th>
                    <th>Price</th>
                </tr>
                </thead>

                <tbody>
                <script>
                    var price = 0;
                </script>

                <c:forEach items="${carts}" var="purchase">



                    <tr id="table${purchase.key.itemID}">
                        <td><img src="/shop/img/${purchase.key.itemID}" width="120" height="75"></td>
                        <td id="itemName"> ${purchase.key.itemName} </td>
                        <td> ${purchase.value.amount} </td>
                        <td>
                            <p>Price for ${purchase.value.amount}
                                is ${purchase.key.price * purchase.value.amount}</p>
                            <p style="color: green"> Price per one: ${purchase.key.price} </p>
                        </td>
<!--
                        <c:choose>
                            <c:when test="${purchase.value.amount > purchase.key.leftOnStore}">
                                <td>
                                        <strong style="color: darkred">${purchase.key.leftOnStore}</strong>
                                </td>
                            </c:when>
                        </c:choose>
-->
                        <td id="alert${purchase.key.itemID}">

                        </td>

                        <td>
                            <form:form method="delete" action="cart/layOut/${purchase.value.cartID}">
                                <button type="submit" class="btn btn-default btn-group"> Lay out</button>
                            </form:form>
                            <a data-toggle="modal" class="btn btn-default btn-group"
                                   href="#update${purchase.value.cartID}"> Edit form </a>

                        </td>
                    </tr>

                    <script>
                        var itemsInTheCart = ${purchase.value.amount};
                        var itemsLeftOnTheStore = ${purchase.key.leftOnStore};

                        var i = itemsInTheCart - itemsLeftOnTheStore;

                        if(itemsInTheCart > itemsLeftOnTheStore) {
                            $('#table${purchase.key.itemID}').addClass('test');
                            $("#alert${purchase.key.itemID}").append("Sorry you order " + itemsInTheCart + " items " +
                                    " but we currently have only " + itemsLeftOnTheStore + " items on our storage " +
                                    ". Please lay out " + i + " items.");
                            $("#alert${purchase.key.itemID}").css("color", "#A94442");

                            $('#itemsOutOfStore').removeClass('hidden');
                        }
                    </script>

                    <!-- out update form -->
                    <div id="update${purchase.value.cartID}" class="modal fade">
                        <div class="modal-dialog" style="padding-top: 165px;">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x
                                    </button>
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
                        // cont total price
                        var pricePerOne = ${purchase.key.price};
                        var amount = ${purchase.value.amount};
                        var totalPrice = pricePerOne * amount;

                        price += totalPrice;

                        //$("#price${purchase.key.itemID}").text("Total price: " + totalPrice);
                    </script>

                </c:forEach>

                </tbody>
            </table>


            <hr>

            <div class="row">
                <div class="col-sm-6 ">

                    <h4 id="totalPrice" style="color: darkgreen"></h4>
                    <script>
                        $("#totalPrice").text("Total price: " + price + "$");
                    </script>

                    <a id="makePurchase" class="btn btn-primary" data-toggle="modal" href="#order">Make purchase</a><br>

                    <script>
                        if (price <= 0) {
                            $('#makePurchase').hide();
                            $('#searchByItemName').hide();
                        }
                    </script>

                </div>
            </div>

            <!-- our buy form -->
            <div id="order" class="modal fade">
                <div class="modal-dialog" style="padding-top: 165px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>

                            <h4 id="modal-label"> Make order
                                <small id="totalPriceHeader"></small>
                            </h4>
                            <script>
                                $("#totalPriceHeader").text("Total price: " + price + "$");
                            </script>
                        </div>
                        <div class="modal-body">

                            <div class="bs-example" id="invalidFormAlert">
                                <div class="alert alert-danger alert-error">
                                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                                    <strong>Error!</strong> Woops !
                                </div>
                            </div>

                            <form:form action="makePurchase" commandName="address" role="form">

                                <div class="form-group error">
                                    <label> City: </label>
                                    <input type="text" name="city" class="form-control"
                                           placeholder="Enter city name">
                                    <span class="help-inline" style="color: red"><form:errors path="city"/></span>
                                </div>

                                <div class="form-group error">
                                    <label> Street: </label>
                                    <input type="text" name="street" class="form-control"
                                           placeholder="Enter street">
                                    <span class="help-inline" style="color: red"><form:errors path="street"/></span>
                                </div>


                                <button type="submit" class="btn btn-primary">Order</button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- out update form -->
        </div>
    </div>
</div>
</body>
</html>
