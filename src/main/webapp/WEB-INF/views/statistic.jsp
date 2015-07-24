<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="a" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="td" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<html>
<head>
    <title> Statistic </title>
    <style>
        <%@ include file="../../resources/js/chart/horizBarChart.css" %>
        <%@ include file="../../resources/js/chart/style.css" %>

        <%@ include file="../../resources/css/bootstrap.css" %>
        <%@ include file="../../resources/css/custom.css" %>
        <%@ include file="../../resources/css/panel.css" %>
    </style>

    <script src="<c:url value="/resources/js/bootstrap.3.0.0.min.js" />"></script>
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>

    <script src="<c:url value="/resources/js/cartCount.js" />"></script>

    <script src="<c:url value="/resources/js/chart/horizBarChart.js" />"></script>
    <script src="<c:url value="/resources/js/chart/jquery.horizBarChart.min.js" />"></script>

    <script>
        $(document).ready(function () {
            $('.chart').horizBarChart({
                selector: '.bar',
                speed: 2000
            });
        });
    </script>
</head>
<body>

<div class="container">

    <jsp:include page="static/navbar.jsp"/>

    <div class="panel">
        <div class="panel-body">

            <!-- Page Header -->
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header">Statistic
                        <small></small>
                    </h2>
                </div>
            </div>
            <!-- Page Header -->

            <ul class="chart">
                <li class="title" title=""><h3> Total purchases </h3></li>
                <hr>
                <c:forEach items="${totalStatistic}" var="statistic">
                    <li class="past" title="${statistic.key}"><span class="bar" data-number="${statistic.value}"></span><span
                            class="number">${statistic.value}</span></li>
                </c:forEach>
            </ul>

            <hr>

            <!---
            <ul class="chart">
                <li class="title" title=""><h3> City statistic </h3></li>
                <hr>
                <c:forEach items="${cityStatistic}" var="st">
                    <li class="past" title="${st.key}"><span class="bar" data-number="${st.value}"></span><span
                            class="number">${st.value}</span></li>
                </c:forEach>
            </ul>

            <hr>
            -->
            <c:forEach items="${citiesStatisticMap}" var="statisticList">
                <ul class="chart">
                    <li class="title" title=""><h3> ${statisticList.key} statistic </h3></li>
                    <hr>
                    <c:forEach items="${statisticList.value}" var="statistic">
                        <li class="past" title="${statistic.key}"><span class="bar" data-number="${statistic.value}"></span><span
                                class="number">${statistic.value}</span></li>
                    </c:forEach>
                </ul>

                <hr>

            </c:forEach>

        </div>
    </div>
</div>

<jsp:include page="static/footer.jsp" flush="true"/>

</body>
</html>
















