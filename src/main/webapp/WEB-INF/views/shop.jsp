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

        .thumbnail {
            /*width: 125px;*/
            height: 185px;
            /*overflow: auto;*/
        }

        #img-rounded {
            border-radius: 4px;
        }

        #center {
            /*margin-left: auto;
            margin-right: auto;*/
            text-align: center;
        }

        #right {
            float: right;
        }


    </style>
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
    <script src="<c:url value="/resources/js/search.js" />"></script>
    <script>
        $(document).ready(function () {
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

<body style="padding: 1rem;">

<div class="container">

    <jsp:include page="static/navbar.jsp" flush="true"/>

    <div class="carousel slide" data-ride="carousel" style="padding-top: 5%">
        <ol class="carousel-indicators">
            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="item active">
                <img class="slide-image"
                     src="http://healthybliss.net/bliss/wp-content/uploads/2015/01/nooderslide1500-1500x430.jpg"
                     id="img-rounded" alt="">
            </div>
        </div>

        <div class="carousel-inner">
            <div class="item">
                <img class="slide-image"
                     src="http://healthybliss.net/bliss/wp-content/uploads/2015/01/nooderslide1500-1500x430.jpg"
                     id="img-rounded" alt="">
            </div>
        </div>

        <div class="carousel-inner">
            <div class="item">
                <img class="slide-image"
                     src="http://www.greentribune.com/wp-content/uploads/2013/05/fruits-and-vegetables-supermarket-grocery-store-642x336.png"
                     id="img-rounded" alt="">
            </div>
        </div>

    </div>

    <dl class="dl-horizontal" style="padding-top: 2%">
        <dt>
        <div class="list-group" id="searchBar">
            <a href="#" class="list-group-item" id="All">All</a>
            <a href="#" class="list-group-item active" id="Fruit">Fruit</a>
            <a href="#" class="list-group-item" id="Drink">Drink</a>
            <a href="#" class="list-group-item" id="Vegetables">Vegetables</a>
            <a href="#" class="list-group-item" id="Diary-products">Dairy-products</a>
            <a href="#" class="list-group-item" id="Chocolate">Chocolate</a>
            <a href="#" class="list-group-item" id="Chips">Chips</a>

        </div>
        </dt>
        <dd>

            <input type="search" id="searchByItemName" class="form-control" style="margin-bottom:15px;"
                   placeholder="Search by item name" onkeyup="searchValue('#itemName', this.id)">

            <div class="row">
                <c:forEach items="${items}" var="item">
                    <div class="type" id="${item.type}">
                        <div class="col-md-3" id="${item.type}">
                            <!--<div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">-->
                            <div class="thumbnail">
                                <img src="/shop/img/${item.itemID}"
                                     class="img-rounded" width="75" height="55">
                                <!--<img src="http://placehold.it/240x150" alt="ALT NAME" class="img-responsive" />-->

                                <div class="caption">
                                    <h4 class="pull-right">$${item.price}</h4>
                                    <h4 id="itemName">${item.itemName}</h4>

                                    <p>
                                        <a data-toggle="modal" class="btn btn-primary" href="#buy${item.itemID}">Buy</a>
                                        <sec:authorize access="hasRole('ADMIN')"><a data-toggle="modal"
                                                                                    class="btn btn-default"
                                                                                    href="#update${item.itemID}">Edit
                                            form</a></sec:authorize>
                                    </p>
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
                                                     class="img-rounded" width="140" height="95" id="left"/>

                                                <div id="right" style="padding-right: 40%;">
                                                    <form:form action="/shop/${item.itemID}">
                                                    <label >Item Name: ${item.itemName} </label>
                                                    <input type="number" id="amount${item.itemID}" name="amount"
                                                           class="form-control bfh-number"
                                                           min="1" max="99999" value="1"
                                                           placeholder="Amount">

                                                </div>

                                            </div>
                                            <div class="modal-footer">
                                                <button class="btn btn-primary">Buy</button>
                                                </form:form>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                                <!-- our buy form -->





                                <script>
                                    var itemID = "#${item.itemID}";
                                    $("#amount${item.itemID}").on("input", function () {
                                        var price = ${item.price};
                                        var amount = ($("#amount${item.itemID}").val());
                                        $("#price${item.itemID}").text(price * amount);
                                    });
                                    $("#sub${item.itemID}").on("click", function () {
                                        $("#buy${item.itemID}").modal('hide');
                                    });
                                </script>
                                <!-- </p>-->
                                <!--</div>-->
                            </div>
                        </div>
                    </div>





                </c:forEach>
            </div>
        </dd>
    </dl>

</div>

<div  role="navigation">

    <div class="container">
        <h5> Test (c) </h5>
    </div>

</div>

</body>
</html>
