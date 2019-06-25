<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
        window.setTimeout(function () {
            $('#my-alert').alert('close');
        }, 1000);
    });

    jQuery(function ($) {
        $('tbody tr[data-href]').addClass('clickable').click(function () {
            window.location = $(this).attr('data-href');
        });
    });
</script>

<div>
    <c:if test="${serviceMsg != null}">
        <div id="my-alert" class="alert alert-primary alert-dismissible fade show" role="alert">
            <fmt:message bundle="${i18n}" key="${serviceMsg}"/>
        </div>
    </c:if>

    <div><fmt:message bundle="${i18n}" key="product.title"/></div>
    <table class="table">
        <tr>
            <th scope="col"><fmt:message bundle="${i18n}" key="product.fabricator"/></th>
            <th scope="col"><fmt:message bundle="${i18n}" key="product.model"/></th>
            <th scope="col"><fmt:message bundle="${i18n}" key="product.diagonal"/></th>
            <th scope="col"><fmt:message bundle="${i18n}" key="product.balans"/></th>
            <th scope="col"><fmt:message bundle="${i18n}" key="product.price"/></th>
        </tr>

        <c:forEach var="product" items="${products}" varStatus="status">
            <tr class="even" data-href="${pageContext.request.contextPath}
            /shop?command=addProduct&idProduct=${product.idProduct}"
                title="<fmt:message bundle="${i18n}" key="product.addToBasket"/>">
                <td>${product.fabricator}</td>
                <td>${product.model}</td>
                <td>${product.diagonal}</td>
                <td>${product.balans}</td>
                <td>${product.price}</td>
            </tr>
        </c:forEach>
    </table>
</div>




