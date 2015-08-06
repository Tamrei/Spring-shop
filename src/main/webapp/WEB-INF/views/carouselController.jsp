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
    <title>Carousel Controller</title>
    <jsp:include page="static/staticFiles.jsp"/>
</head>
<body>


<div class="container">

    <jsp:include page="static/navbar.jsp" flush="true"/>

    <c:if test="${not empty error}">
        <div class="bs-example">
            <div class="alert alert-danger alert-error">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>Error!</strong> <c:out value="${error}"/>
            </div>
        </div>
    </c:if>

    <div class="panel panel-login">
        <div class="panel-body">

            <!-- Page Header -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Home page controller
                        <small></small>
                    </h1>
                </div>
            </div>
            <!-- Page Header -->


            <!-- Home page image Row -->
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
                                <button type="submit" class="btn btn-primary"> <- Delete this image</button>
                            </form:form>

                        </div>
                    </div>

                    <hr>
                </c:forEach>

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
        </div>
    </div>
</div>

<jsp:include page="static/footer.jsp" flush="true"/>

</body>
</html>
