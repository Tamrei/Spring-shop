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
    <jsp:include page="static/staticFiles.jsp"/>
    <script src="<c:url value="/resources/js/view/user.js" />"></script>
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
<!-- Page Header -->

<!-- Table -->
<c:forEach items="${items}" var="item">
    <div class="panel panel-default" id="pnl${item.itemID}">
        <div class="panel-heading" style="padding: 0;">

            <div id="flip${item.itemID}">

                <table class="table borderless" style="padding: 0">
                    <tr>

                        <td style="width: 270px"><img src="/shop/img/${item.itemID}" width="120" height="75"></td>
                        <td style="width: 170px"><strong> ID :</strong> ${item.itemID} </td>
                        <td style="width: 300px" id="itemName"><strong> Item Name :</strong> ${item.itemName} </td>
                        <td style="width: 300px"><strong> Left on Stock :</strong> ${item.leftOnStore} </td>

                        <td style="width: 200px" id="stst${item.itemID}"></td>

                        <script>
                            var status2 = $("#stst${item.itemID}");
                            var panel = $("#pnl${item.itemID}");

                            if (${item.available}) {
                                status2.css("color", "darkgreen").text("enabled");
                            } else {
                                status2.css("color", "darkred").text("disabled");
                            }
                        </script>

                        <!-- Options dropdown -->
                        <td id="btnDropdwn${item.itemID}">
                            <div class="dropdown">
                                <button class="btn btn-default dropdown-toggle" type="button"
                                        data-toggle="dropdown"> Options
                                    <span class="caret"></span></button>
                                <ul class="dropdown-menu">
                                    <li><a href="/addItem">Add new Item</a>
                                    </li>
                                    <li><a data-toggle="modal" role="button"
                                           href="#addDelivery${item.itemID}"> Add
                                        delivery </a>
                                    </li>
                                    <li><a data-toggle="modal" role="button"
                                           href="#Modal${item.itemID}"> Change item status </a>
                                    </li>
                                </ul>
                            </div>
                        </td>
                        <!-- Options dropdown -->
                    </tr>
                </table>
            </div>
        </div>

        <div id="panel${item.itemID}" class="panel-body" style="display: block;padding-bottom: 0px;">
            <table class="table">
                <th> Delivery ID</th>
                <th> Amount</th>
                <th> Date of Delivery</th>
                <c:forEach items="${item.itemDeliveries}" var="deliveries">
                    <tr>
                        <td>${deliveries.itemDeliveryID}</td>
                        <td>${deliveries.itemQuantity}</td>
                        <td>${deliveries.dateOfDelivery}</td>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </div>
    <!-- Table -->


    <!-- Disable/Enable Modal -->
    <div class="modal fade" id="Modal${item.itemID}" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Modal Header</h4>
                </div>

                <div class="modal-body">
                    <p><strong>If you enable item</strong> that mean that this item will be available for
                        purchase. </p>

                    <p><strong>If you disable item</strong> that mean that this item will be not available for
                        purchase. </p>
                </div>
                <div class="modal-footer">

                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            onclick="enableDisableItem(${item.itemID})">Enable
                    </button>

                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        Close
                    </button>

                </div>

            </div>
        </div>
    </div>
    <!-- Disable/Enable Modal -->


    <!-- Add new delivery modal -->
    <div id="addDelivery${item.itemID}" class="modal fade">
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
                            <input type="number" name="itemQuantity"
                                   class="form-control"
                                   placeholder="Quntity">
                        </div>

                        <button type="submit" class="btn btn-default" id="sub">Submit
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <!-- Add new delivery modal -->


    <script>
        var isThisAAddNewDeliveryButton = false;

        $('#btnDropdwn${item.itemID}').on('click', function () {
            isThisAAddNewDeliveryButton = true;
        });

        $("#flip${item.itemID}").on('click', function () {
            if (!isThisAAddNewDeliveryButton) {
                $("#panel${item.itemID}").slideToggle("slow");
            }
            isThisAAddNewDeliveryButton = false;
        });

        //isThisAAddNewDeliveryButton = false;
    </script>

</c:forEach>


<script>
    function setStatus(isEnabled, itemID) {
        var status = $("#stst" + itemID);
        //var panel2 = $("#pnl" + itemID);

        if (isEnabled) {
            status.css("color", "darkgreen").text("enabled");
        } else {
            status.css("color", "darkred").text("disabled");
        }

    }

    function enableDisableItem(itemID) {
        $.ajax({
            type: "Post",
            url: "enableDisableItem.html",
            data: "itemID=" + itemID,
            success: function (data) {
                if (data == "true") {
                    setStatus(true, itemID);
                } else {
                    setStatus(false, itemID);
                }
            },
            error: function (e) {
                alert("error!");
            }
        });
    }
</script>

</div>
</div>
</div>


<jsp:include page="static/footer.jsp" flush="true"/>

</body>
</html>