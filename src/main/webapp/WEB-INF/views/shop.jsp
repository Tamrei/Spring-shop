<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true" %>
<%@taglib prefix="sec"
          uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title> Shop </title>
    <jsp:include page="static/staticFiles.jsp"/>
    <style>
        .list-group-item {
            border-radius: 0px;
            border: none;
        }
        #searchBar h4 {
            margin-bottom: 3px;
            margin-top: 3px;
            font-size: 15px;
        }

    </style>
    <script src="<c:url value="/resources/js/view/shop.js" />"></script>
    <script>
        $(document).ready(function () {
            $('#cartNotification').hide();
            sortPanel();
            //getHomePageImages();
        });
    </script>
</head>
<body>

<div class="container">

<jsp:include page="static/navbar.jsp"/>

<div class="bs-example" id="cartNotification" style="display:none;">
    <div class="alert alert-success">
        <a href="#" class="close" data-dismiss="alert">&times;</a>
        <strong>Success!</strong>
        <div id="cartNotificationMessage"> Success!</div>
    </div>
</div>

<!-- Carousel -->
<div style="padding-bottom: 1%;">

    <header id="myCarousel" class="carousel slide">
        <div class="carousel-inner">

            <div class="item active">
                <img class="slide-image"
                     src="http://healthybliss.net/bliss/wp-content/uploads/2015/01/nooderslide1500-1500x430.jpg">
            </div>

            <c:forEach items="${homePageImages}" var="image">
                <div class="item">
                    <img class="slide-image"
                         src="homePageImage/img/${image.id}">
                </div>
            </c:forEach>
        </div>
        <!-- Carousel controls -->
        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
            <span class="icon-prev"></span>
        </a>
        <a class="right carousel-control" href="#myCarousel" data-slide="next">
            <span class="icon-next"></span>
        </a>
        <!-- Carousel controls -->
    </header>
</div>
<!-- Carousel -->

<div class="panel">
    <div class="panel-body">

        <!-- Page Header -->
        <div class="row">
            <div class="col-lg-12">
                <h2 class="page-header"> Shop </h2>
            </div>
        </div>
        <!-- Page Header -->

        <div class="col-md-2 sidebar" style="padding-right: 0px; padding-left: 0px;">
            <div class="list-group" id="searchBar">
                <a href="#" class="list-group-item active" id="All"><h4>All</h4></a>
                <a href="#" class="list-group-item" id="Fruit"><h4 id>Fruit</h4></a>
                <a href="#" class="list-group-item" id="Drink"><h4>Drink</h4></a>
                <a href="#" class="list-group-item" id="Vegetables"><h4>Vegetables</h4></a>
                <a href="#" class="list-group-item" id="Diary-products"><h4>Dairy-products</h4></a>
                <a href="#" class="list-group-item" id="Chocolate"><h4>Chocolate</h4></a>
                <a href="#" class="list-group-item" id="Chips"><h4>Chips</h4></a>
            </div>
        </div>

        <div class="col-md-10 content" style="padding-right: 0px;">

            <div class="row">
                <c:forEach items="${items}" var="item">
                    <div class="type" id="${item.type}">
                        <div class="col-sm-6 col-md-3" id="${item.type}">
                            <div class="thumbnail" style="height: 222px; padding: 1px;">
                                <img src="/shop/img/${item.itemID}">

                                <div class="caption">
                                    <h4 class="pull-right">$${item.price}</h4>
                                    <h4 class="itemName" id="itemName">${item.itemName}</h4>

                                    <p>
                                        <c:choose>
                                            <c:when test="${empty pageContext.request.userPrincipal}">
                                                <a data-toggle="modal" role="button" class="btn btn-default btn-block"
                                                   href="#login"> Put item in the cart </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a data-toggle="modal" role="button" class="btn btn-default btn-block"
                                                   href="#buy${item.itemID}"> Put item in the cart </a>
                                            </c:otherwise>
                                        </c:choose>

                                        <!--
                                        <sec:authorize access="hasRole('ADMIN')"><a data-toggle="modal"
                                           class="btn btn-default" href="#update${item.itemID}">
                                           Edit form</a></sec:authorize>
                                        -->
                                    </p>
                                </div>
                            </div>


                            <!-- Out update form (remove to store) -->
                            <div id="update${item.itemID}" class="modal fade">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal"
                                                    aria-hidden="true">x
                                            </button>
                                            <h4 id="modal-label">Update ${item.itemName}</h4>
                                        </div>
                                        <div class="modal-body">
                                            <form:form method="post" action="/shop/update/${item.itemID}"
                                                       commandName="item" enctype="multipart/form-data">
                                                <div class="form-group">
                                                    <label>Old Item Name: ${item.itemName} </label>
                                                    <input type="text" name="itemName" class="form-control"
                                                           placeholder="Enter Item Name"
                                                           value="${item.itemName}">
                                                </div>

                                                <div class="form-group">
                                                    <label>Old Type: ${item.type} </label>
                                                    <input type="text" name="type" class="form-control"
                                                           placeholder="Enter Item Name" value="${item.type}">
                                                </div>

                                                <div class="form-group">
                                                    <label>Old Price: ${item.price} </label>
                                                    <input type="number" name="price"
                                                           class="form-control bfh-number" placeholder="Price"
                                                           value="${item.price}">
                                                </div>

                                                <div class="form-group">
                                                    <label>Item Image:</label>
                                                    <input type="file" name="file" class="form-control"
                                                           value="Test">
                                                </div>

                                                <button type="submit" class="btn btn-default" id="sub">Submit</button>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--  -->


                            <!-- Put in the cart modal -->
                            <div id="buy${item.itemID}" class="modal fade">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal"
                                                    aria-hidden="true">x
                                            </button>
                                            <h4 id="modal-label2">Buy ${item.itemName}</h4>
                                        </div>
                                        <div class="modal-body" style="padding-bottom: 35px;">
                                            <img src="/shop/img/${item.itemID}"
                                                 class="img-rounded" width="180" height="120" id="left"/>

                                            <div style="width: 65%; float: right;">

                                                <label>Item Name: ${item.itemName} </label>
                                                <input type="number" id="amount${item.itemID}" name="amount"
                                                       class="form-control bfh-number"
                                                       min="1" max="999" value="1"
                                                       step="1" data-bind="value:replyNumber"
                                                       placeholder="Amount">

                                                <div id="priceInformation">
                                                    <h4>Price for one ${item.price}</h4>
                                                    <h4 id="result${item.itemID}"></h4>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button class="btn btn-primary btn-md" id="putButton"
                                                    onclick="putItemInCart(${item.itemID}, '${item.itemName}')"> Put Item in the cart
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <!--  -->

                                <!-- price information script -->
                                <script>
                                    var itemID = "#${item.itemID}";
                                    $('#amount${item.itemID}').on("input", function () {
                                        var price = ${item.price};
                                        var amount = ($("#amount${item.itemID}").val());
                                        var total = price * amount;
                                        var outputStr = "Price for " + amount + " is $" + total;

                                        $("#result${item.itemID}").text(outputStr);
                                    });
                                    $("#sub${item.itemID}").on("click", function () {
                                        $("#buy${item.itemID}").modal('hide');
                                    });
                                </script>
                                <!-- price information script -->

                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
    </div>
</div>

<jsp:include page="static/footer.jsp" flush="true"/>

</body>
</html>
