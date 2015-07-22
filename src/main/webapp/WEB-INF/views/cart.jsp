<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true" %>
<%@taglib prefix="sec"
          uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="td" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title> My Cart </title>
    <jsp:include page="static/staticFiles.jsp"/>
    <script src="<c:url value="/resources/js/validation/purchaseFormValidator.js" />"></script>
    <script src="<c:url value="/resources/js/validation/validationMarkup.js" />"></script>
    <script src="<c:url value="/resources/js/view/cart.js" />"></script>
    <script>
        $(document).ready(function () {
            var url = window.location.href; // get current url
            if (url.endsWith('#error')) {
                $('#order').modal('show');
                $('#invalidFormAlert').show();
            }
            else $('#invalidFormAlert').hide();
            //validatePurchaseForm();
        });
    </script>
</head>

<body>
<div class="container">

<jsp:include page="static/navbar.jsp"/>

<c:if test="${not empty errorE}">
    <div class="bs-example">
        <div class="alert alert-danger alert-error">
            <a href="#" class="close" data-dismiss="alert">&times;</a>
            <strong>Error!</strong> <c:out value="${errorE}"/>
        </div>
    </div>
</c:if>

<c:if test="${not empty successfulPurchase}">
    <div class="bs-example">
        <div class="alert alert-success">
            <a href="#" class="close" data-dismiss="alert">&times;</a>
            <strong>Successful Purchase!</strong> You can view all your purchases <a href="/myPurchases"> here.</a>
        </div>
    </div>
</c:if>

<div class="bs-example hidden" id="itemsOutOfStore">
    <div class="alert alert-danger alert-error">
        <a href="#" class="close" data-dismiss="alert">&times;</a>
        <strong>Error!</strong> Sorry but you have more items in your cart than we have on our store ;(
    </div>
</div>

<div class="bs-example hidden" id="itemsNotAvailable">
    <div class="alert alert-danger alert-warning">
        <a href="#" class="close" data-dismiss="alert">&times;</a>
        <strong>Warning!</strong> Sorry you have items in your cart that currently disable.
        In order to make purchase pls lay out them.
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
<!-- Page Header -->

<div id="emptyCart" class="hidden">
    <h2 style="text-align: center"> You cart is empty. You can it up <a href="/shop">here.</a></h2>
</div>

<table class="table table-curved table-hover" id="cartTable">

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
        var isCartReadyForOrder = true;
    </script>

    <c:forEach items="${carts}" var="purchase">

        <tr id="table${purchase.key.itemID}">
            <td><img src="/shop/img/${purchase.key.itemID}" width="120" height="75"></td>
            <td id="itemName"><h4> ${purchase.key.itemName} </h4>
                <h4 id="st${purchase.key.itemID}" class="text-success"> Available </h4>
            </td>
            <td> ${purchase.value.amount} </td>
            <td>
                <p> Price for ${purchase.value.amount}
                    is $${purchase.key.price * purchase.value.amount} </p>
                <p class="text-success"> Price per one: $${purchase.key.price} </p>
            </td>

            <td>
                <h4 id="alert${purchase.key.itemID}"></h4>
            </td>

            <td>
                <form:form method="delete" action="cart/layOut/${purchase.value.cartID}">
                    <button type="submit" class="btn btn-default btn-danger"> Lay out</button>
                </form:form>

                <a data-toggle="modal" class="btn btn-default"
                   href="#update${purchase.value.cartID}"> Edit form </a>
            </td>
        </tr>

        <!-- Check if items in the cart available for purchase -->
        <script>
            var itemsInTheCart = ${purchase.value.amount};
            var itemsLeftOnTheStore = ${purchase.key.leftOnStore};
            var isItemAvailable = ${purchase.key.available};

            var st = $("#st${purchase.key.itemID}");

            if (!isItemAvailable) {
                $("#alert${purchase.key.itemID}").append("This item currently not available for purchase.");
                setErrorMarkup();
                isCartReadyForOrder = false;
            }

            else if (itemsInTheCart > itemsLeftOnTheStore) {
                $("#alert${purchase.key.itemID}").append("Sorry but currently only have " + itemsLeftOnTheStore + " of this in our stock.");
                setErrorMarkup();
                isCartReadyForOrder = false;
            }

            function setErrorMarkup() {
                $("#alert${purchase.key.itemID}").css("color", "#A94442");
                $('#itemsOutOfStore').removeClass('hidden');
                st.css("color", "rgb(169, 68, 66)").text("disabled");
            }
        </script>
        <!-- Check if items in the cart available for purchase -->

        <!-- Update item amount in cart modal -->
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

                            <button type="submit" id="sub" class="btn btn-primary">Submit</button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <!-- Update item amount in cart modal -->

        <script>
            // cont total price
            var pricePerOne = ${purchase.key.price};
            var amount = ${purchase.value.amount};
            var totalPrice = pricePerOne * amount;
            price += totalPrice;
        </script>

    </c:forEach>

    </tbody>
</table>

<hr>

<div class="row">
    <div class="col-sm-6 ">
        <h3 id="totalPrice"></h3>
    </div>
</div>

<hr>

<div class="row">
    <div class="col-sm-6 ">
        <a id="makePurchase" class="btn btn-primary" data-toggle="modal" href="#order"> Make purchase </a><br>
    </div>

    <script>
        // show total price
        $("#totalPrice").text("Total price: " + price + "$");

        if (price <= 0) {
            $('#makePurchase').hide();
            $('#searchByItemName').hide();
            $('#totalPrice').hide();
            $('#cartTable').hide();
            $('#emptyCart').removeClass('hidden');
        }
    </script>
</div>

<script>
    $('#makePurchase').prop('disabled', !isCartReadyForOrder);
</script>

<!-- Make purchase modal -->
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

                <form:form action="makePurchase" commandName="address" role="form" id="purchase-form">

                    <div class="form-group has-feedback" id="city-form">
                        <label class="control-label" for="city" id="city-label"> City: </label>


                        <select name="city" class="form-control">
                            <c:forEach items="${availableCities}" var="city">
                                <option  id="city" name="city" value="${city}"> ${city} </option>
                            </c:forEach>
                        </select>
                        <br>
                        <!--
                        <div class="controls">
                            <input type="text" name="city" class="form-control"
                                   placeholder="Enter city name" id="city">
                        </div>
                        -->
                    </div>

                    <div class="form-group has-feedback" id="street-form">
                        <label class="control-label" for="street" id="street-label"> Street: </label>

                        <div class="controls">
                            <input type="text" name="street" class="form-control"
                                   placeholder="Enter street" id="street">
                        </div>
                    </div>

                    <hr>

                    <button type="submit" class="btn btn-primary" name="submit" value="submit"> Order</button>
                </form:form>
            </div>
        </div>
    </div>
</div>
<!-- Make purchase modal -->

</div>
</div>
</div>

<jsp:include page="static/footer.jsp" flush="true"/>

</body>
</html>
