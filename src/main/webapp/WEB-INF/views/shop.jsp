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
    <style>
        <%@ include file="../../resources/css/bootstrap.css" %>
        <%@ include file="../../resources/css/custom.css" %>
        <%@ include file="../../resources/css/box.css" %>

        .thumbnail {
            height: 185px;
        }

        #img-rounded {
            border-radius: 3px;
        }

        #right {
            float: right;
        }

        #priceInformation {
            color: darkgreen;
            padding-left: 5px;
        }

        .pagination > li > a, .pagination > li > span {
            border-radius: 50% !important;
            margin: 0 5px;
        }

    </style>

    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script src="<c:url value="/resources/js/search.js" />"></script>
    <script src="<c:url value="/resources/js/ajax.js" />"></script>
    <script>
        $(document).ready(function () {
            $('#cartNotification').hide();
            var rows = $('div.type');

            $("#searchBar").children().click(function () {
                var col = $(this).text();
                var ww = rows.filter("#" + col).show();
                rows.not(ww).hide();
                $("#searchBar").children().each(function () {
                    if ($(this).text() != col) {
                        $(this).removeClass("list-group-item active").addClass("list-group-item");
                    }
                    else {
                        $(this).removeClass("list-group-item").addClass("list-group-item active");
                    }
                });
            });

            $('#All').click(function () {
                rows.show();
            });
        });
    </script>
    <title> Shop </title>
</head>

<body>

<script>
    // get carousel
    $.ajax({
        type: "Get",
        url: 'carouselController.html',
        async: false,
        success: function (data) {
            //alert("+");
            //$(document).ajaxStop(function() { location.reload(true); });
        },
        error: function (e) {
            alert("-");
        }
    });
</script>

<div class="container">

<jsp:include page="static/navbar.jsp" flush="true"/>


<div class="bs-example" id="cartNotification" style="display:none;">
    <div class="alert alert-success">
        <a href="#" class="close" data-dismiss="alert">&times;</a>
        <strong>Success!</strong>

        <div id="cartNotificationMessage"> Success!</div>
    </div>
</div>


<div style="padding-bottom: 1%;">
    <!-- Header Carousel -->
    <header id="myCarousel" class="carousel slide">
        <!-- Wrapper for slides -->
        <div class="carousel-inner">

            <div class="item active">
                <img class="slide-image"
                     src="http://healthybliss.net/bliss/wp-content/uploads/2015/01/nooderslide1500-1500x430.jpg"
                     id="img-rounded" alt="">
            </div>

            <c:forEach items="${homePageImages}" var="image">
                <div class="item">
                    <img class="slide-image"
                         src="homePageImage/img/${image.id}"
                         id="img-rounded" alt="">
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
    </header>

</div>

<div class="panel panel-login" style="margin-top: 20px; padding-top: 1%;">
<div class="panel-body">

<!-- Page Header -->
<div class="row">
    <div class="col-lg-12">
        <h2 class="page-header"> Shop </h2>
    </div>
</div>


<div class="col-md-2 sidebar" style="
    padding-right: 15px;
    padding-left: 0px;">
    <div class="list-group" id="searchBar">
        <a href="#" class="list-group-item active" id="All">All</a>
        <a href="#" class="list-group-item" id="Fruit">Fruit</a>
        <a href="#" class="list-group-item" id="Drink">Drink</a>
        <a href="#" class="list-group-item" id="Vegetables">Vegetables</a>
        <a href="#" class="list-group-item" id="Diary-products">Dairy-products</a>
        <a href="#" class="list-group-item" id="Chocolate">Chocolate</a>
        <a href="#" class="list-group-item" id="Chips">Chips</a>
    </div>
</div>

<div class="col-md-10 content" style="
    padding-right: 0px;
    padding-left: 0px;">


    <input type="search" id="searchByItemName" class="form-control" style="margin-bottom:15px;"
           placeholder="Search by item name" onkeyup="searchValue('#itemName', this.id)">

    <div class="row">
        <c:forEach items="${items}" var="item">
            <div class="type" id="${item.type}">
                <div class="col-sm-6 col-md-3" id="${item.type}">
                    <div class="thumbnail" style="height: 222px; padding: 1px;">
                        <img src="/shop/img/${item.itemID}"
                             id="img-rounded">

                        <div class="caption">
                            <h4 class="pull-right">$${item.price}</h4>
                            <h4 id="itemName">${item.itemName}</h4>

                            <p>
                                <a data-toggle="modal" role="button" class="btn btn-default"
                                   href="#buy${item.itemID}">Buy</a>
                                <sec:authorize access="hasRole('ADMIN')"><a data-toggle="modal"
                                                                            role="button"
                                                                            class="btn btn-default"
                                                                            href="#update${item.itemID}">
                                    Edit form</a></sec:authorize>
                            </p>
                        </div>
                    </div>

                    <!-- out update form -->
                    <div id="update${item.itemID}" class="modal fade">
                        <div class="modal-dialog" style="padding-top: 165px;">
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

                                        <button type="submit" id="sub">Submit</button>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- out update form -->


                    <!-- our buy form -->
                    <!-- Modal -->
                    <div id="buy${item.itemID}" class="modal fade">
                        <div class="modal-dialog" style="padding-top: 165px;">
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

                                    <div id="right" style="width: 65%;">


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
                                    <button class="btn btn-primary" id="putButton"
                                            onclick="putItemInCart(${item.itemID})"> Buy
                                    </button>
                                </div>

                            </div>

                            <script>
                                function putItemInCart(itemID) {
                                    var amount = $('#amount' + itemID).val();

                                    $.ajax({
                                        type: "Post",
                                        url: 'putItemInTheCart.html',
                                        data: "itemID=" + itemID + "&amount=" + amount,
                                        success: function (data) {

                                            showCartCount('cartCount'); // update cart count
                                            $('.modal.in').modal('hide')
                                            $('#cartNotification').show();
                                            $('#cartNotificationMessage').text('${item.itemID} successfully added to your cart!');
                                        },
                                        error: function (e) {
                                            alert('error!');
                                        }
                                    });
                                }
                            </script>
                        </div>

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
                        <!-- -->

                    </div>
                    <!-- our buy form -->

                </div>
            </div>

        </c:forEach>
    </div>

    <ul class="pagination">
        <li class="active"><a href="#1">1 <span class="sr-only">(current)</span></a></li>
        <li style=""><a href="#2">2</a></li>
        <li><a href="#3">3</a></li>
        <li><a href="#4">4</a></li>
        <li><a href="#5">5</a></li>
    </ul>

</div>

</div>
</div>

</div>

<div role="navigation" style="background-color: #222">
    <div class="container">
        <a href="https://github.com/Tamrei/Spring-shop"> Source Code (GitHub) </a>
    </div>
</div>

</body>
</html>
