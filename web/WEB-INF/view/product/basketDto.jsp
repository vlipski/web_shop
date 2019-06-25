<%@ page import="java.math.BigDecimal" %>
<%@ page import="static java.math.BigDecimal.ROUND_HALF_UP" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 02.06.2019
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<style>
    tr:not(:nth-child(1)):hover {
        background: lightblue;
        cursor: pointer;
        color: red;
    }
</style>

<script>
    jQuery(function ($) {
        $('tbody tr[data-href]').addClass('clickable').click(function () {
            window.location = $(this).attr('data-href');
        });
    });
</script>

<div>
    <c:if test="${serviceMsg != null}">
        <div><fmt:message bundle="${i18n}" key="${serviceMsg}"/></div>
    </c:if>
    <c:set var="salary" value="${0}"/>

    <table class="table">
        <tr>
            <th scope="col"><fmt:message bundle="${i18n}" key="product.fabricator"/></th>
            <th scope="col"><fmt:message bundle="${i18n}" key="product.model"/></th>
            <th scope="col"><fmt:message bundle="${i18n}" key="product.diagonal"/></th>
            <th scope="col"><fmt:message bundle="${i18n}" key="product.price"/></th>
            <th scope="col"><fmt:message bundle="${i18n}" key="basket.quantity"/></th>
        </tr>

        <c:forEach var="basket" items="${basketDto}" varStatus="status">

            <c:set var="salary" value="${salary + basket.price * basket.quantity}"/>
            <tr class="even"
                data-href="${pageContext.request.contextPath}/shop?command=deleteFromBasket&idBasket=${basket.idBasket}"
                title="<fmt:message bundle="${i18n}" key="basket.deleteFromBasket"/>">
                <td>${basket.fabricator}</td>
                <td>${basket.model}</td>
                <td>${basket.diagonal}</td>
                <td>${basket.price}</td>
                <td>${basket.quantity}</td>
            </tr>
        </c:forEach>
    </table>
    <div class="row">
        <div class="col-8">
            <fmt:message bundle="${i18n}" key="basket.total"/>:
            <fmt:setLocale value="en_US"/>
            <fmt:formatNumber value="${salary}" pattern="#.##" groupingUsed="false"/>
        </div>
        <div class="col-4">
            <a href="${pageContext.request.contextPath}/shop?command=createOrders" class="btn btn-primary"
               role="button"><fmt:message bundle="${i18n}" key="basket.pay"/></a>
        </div>
    </div>
</div>

