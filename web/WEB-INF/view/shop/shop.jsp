<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 01.06.2019
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<?xml version="1.0" encoding="UTF-8"?>
<HTML xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:fmt="http://java.sun.com/jsp/jstl/fmt">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>

    <title>shop</title>
</head>
<body>
<jsp:directive.include file="/WEB-INF/tags/navigation.jsp"/>
<div class="jumbotron">
    <div class="container">
        <div class="row">
            <div class="col-9">
                <c:url var="bodyUrl" value="/WEB-INF/view/product/product.jsp"></c:url>
                <c:if test="${not empty sessionScope.pagePath}">
                    <c:url var="bodyUrl" value='../${sessionScope.pagePath}'></c:url>
                </c:if>
                <jsp:include page="${bodyUrl}"/>
            </div>
            <div class="col-3">
                <c:if test="${sessionScope.pageName == 'shop' || sessionScope.pageName == 'addProduct'}">
                    <jsp:directive.include file="/WEB-INF/view/product/sort.jsp"/>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
