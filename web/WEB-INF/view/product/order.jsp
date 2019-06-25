<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 04.06.2019
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<div>
    <c:if test="${serviceMsg != null}">
        <div><fmt:message bundle="${i18n}" key="${serviceMsg}"/></div>
    </c:if>
    <div>
        <a href="${pageContext.request.contextPath}/shop?command=orders&download=true" class="btn btn-primary"
           role="button"><fmt:message bundle="${i18n}" key="order.downloadHistory"/></a>
    </div>
    <table class="table">
        <tr>
            <th scope="col"><fmt:message bundle="${i18n}" key="order.orderDate"/></th>
            <th scope="col"><fmt:message bundle="${i18n}" key="order.listGoods"/></th>
            <th scope="col"><fmt:message bundle="${i18n}" key="basket.total"/></th>
        </tr>
        <c:forEach var="order" items="${listOrders}" varStatus="status">
            <tr class="info">
                <td>${order.data}</td>
                <td>
                    <table class="table">
                        <tr>
                            <td scope="col"><fmt:message bundle="${i18n}" key="product.fabricator"/></td>
                            <td scope="col"><fmt:message bundle="${i18n}" key="product.model"/></td>
                            <td scope="col"><fmt:message bundle="${i18n}" key="product.price"/></td>
                            <td scope="col"><fmt:message bundle="${i18n}" key="basket.quantity"/></td>
                        </tr>
                        <c:forEach var="item" items="${order.orderItemsDto}" varStatus="status">
                            <tr class="info">
                                <td>${item.fabricaeor}</td>
                                <td>${item.model}</td>
                                <td>${item.price}</td>
                                <td>${item.quantity}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
                <td>${order.total}</td>
            </tr>
        </c:forEach>
    </table>
</div>
