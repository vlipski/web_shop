<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 01.06.2019
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:fmt="http://java.sun.com/jsp/jstl/fmt">
<head>
    <meta charset="UTF-8">
    <title>shop</title>
</head>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-secondary">

    <a class="navbar-brand" href="#"><fmt:message bundle="${i18n}" key="navigation.nameProgect"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/shop?command=shop"><b><fmt:message
                        bundle="${i18n}" key="navigation.homepage"/></b><span class="sr-only">(current)</span></a>
            </li>
            <c:if test="${user == null}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/shop?command=registration&first_request=true"><b><fmt:message
                            bundle="${i18n}" key="navigation.signAp"/></b><span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/shop?command=authorization"><b><fmt:message
                            bundle="${i18n}" key="navigation.signIn"/></b><span class="sr-only">(current)</span></a>
                </li>
            </c:if>
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/shop?command=logout"><b><fmt:message
                            bundle="${i18n}" key="logout.logout"/></b><span class="sr-only">(current)</span></a>
                </li>
            </c:if>
            <c:url var="path" value="/shop?command=${sessionScope.pageName}"></c:url>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/shop?command=basketDto"><b><fmt:message
                        bundle="${i18n}" key="navigation.basket"/></b></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/shop?command=orders"><b><fmt:message
                        bundle="${i18n}" key="navigation.orders"/></b></a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown1" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false"><b><fmt:message bundle="${i18n}" key="navigation.language"/></b></a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown1">
                    <a class="dropdown-item" href="${path}&amp;locale=en"><b><fmt:message bundle="${i18n}"
                                                                                          key="navigation.en"/></b></a>
                    <a class="dropdown-item" href="${path}&amp;locale=ru"><b><fmt:message bundle="${i18n}"
                                                                                          key="navigation.ru"/></b></a>
                </div>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0" method="GET" action="shop">
            <input class="form-control mr-sm-2" type="search" name="fabricator"
                   placeholder="<fmt:message bundle="${i18n}" key="sort.byFabricator"/>">
            <button class="btn btn-outline-primary my-2 my-sm-0" name="command" value="shop" type="submit"><b><fmt:message
                    bundle="${i18n}" key="sort.search"/></b></button>
        </form>
    </div>
</nav>
</body>
</html>
