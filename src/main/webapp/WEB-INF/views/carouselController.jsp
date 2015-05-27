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

        <%@ include file="../../resources/css/box.css" %>
    </style>
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
    <script src="<c:url value="/resources/js/search.js" />"></script>
    <script>
        $(document).ready(function () {


        });
    </script>

</head>
<body>
<!-- Page Content -->

<script>
    /*$.ajax({
        type: "Get",
        url: 'homePageImages.html',
        success: function (data) {

        },
        error : function (e) {
            alert("-");
        }
    });*/
</script>

<div class="container">

    <jsp:include page="static/navbar.jsp" flush="true"/>

    <c:if test="${not empty notAnImage}">
        <div class="bs-example">
            <div class="alert alert-danger alert-error">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>Error!</strong> <c:out value="${notAnImage}"/>
            </div>
        </div>
    </c:if>

    <div class="panel panel-login">
        <div class="panel-body">

            <!-- Page Header -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Page Heading
                        <small>Secondary Text</small>
                    </h1>
                </div>
            </div>
            <!-- /.row -->



            <!-- Projects Row -->
            <div class="row">
                <c:forEach items="${homePageImages}" var="image">

                    <div style="padding-bottom: 300px;">
                        <div class="col-md-8">
                            <a href="#">
                                <img src="homePageImage/img/${image.id}" width="700" height="300"
                                     class="img-rounded"/>

                            </a>
                        </div>
                        <div class="col-md-3">
                            <form:form method="delete" action="carouselController/delete/${image.id}">
                                <button type="submit" class="btn btn-primary"> <- Delete this image </button>
                            </form:form>

                        </div>
                    </div>
                    <hr>

                </c:forEach>

                <script>
                    function deleteImage(id) {
                        $.ajax({
                            type: "Post",
                            url: 'carouselController/delete.html',
                            data: "id=" + id,
                            success: function (data) {

                            },
                            error : function (e) {
                                alert("-");
                            }
                        });
                    }

                    //carouselController/delete
                </script>

                <div class="col-md-8">
                    <a href="#">
                        <img src="http://placehold.it/700x300" class="img-rounded"/>
                    </a>
                </div>

                <div class="col-md-3">
                    <form:form method="post" action="carouselController/addNewImage" commandName="homePageImage"
                               enctype="multipart/form-data">
                        <div class="form-group">
                            <label>Add new Image</label>
                            <input type="file" name="file" class="form-control" accept="image/*">
                            <p>All images will be resized to 1500x430 resolution!</p>
                        </div>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form:form>
                </div>
            </div>

            <!-- /.row -->


            <!-- Footer -->

        </div>
    </div>

</div>
</body>
</html>
