<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 28.05.2019
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"/>
<link rel="stylesheet" href="http://bootstraptema.ru/plugins/2018/irs/ion.rangeSlider.css"/>
<link rel="stylesheet" href="http://bootstraptema.ru/plugins/2018/irs/ion.rangeSlider.skin.css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="http://bootstraptema.ru/plugins/2018/irs/ion.rangeSlider.min.js"></script>
<%@ page import="java.util.List" %>
<%@ page import="by.it.services.ProductService" %>
<%@ page import="by.it.services.impl.ProductServiceImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>
<script>
    $(document).ready(function () {
        $("#range_03").ionRangeSlider({
            type: "double",
            grid: true,
            min: 500,
            max: 2500,
            prefix: "P"
        });
    });

    $(document).ready(function () {
        $("#range_04").ionRangeSlider({
            type: "int",
            grid: true,
            min: 17,
            max: 70,
            prefix: "/"
        });
    });
</script>
<%
    ProductService productService = ProductServiceImpl.getInstance();
    List<String> listFabricator = productService.selectNameFabricator();
    pageContext.setAttribute("listFabricator", listFabricator);
%>
<div>
    <form method="GET" action="shop?command=shop">
        <fieldset>
            <h6><fmt:message bundle="${i18n}" key="sort.byPrice"/></h6>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="minmax" id="inlineRadio1" value="ASC">
                <label class="form-check-label" for="inlineRadio1"><fmt:message bundle="${i18n}"
                                                                                key="sort.firstCheap"/></label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="minmax" id="inlineRadio2" value="DESC">
                <label class="form-check-label" for="inlineRadio2"><fmt:message bundle="${i18n}"
                                                                                key="sort.firstExpensive"/></label>
            </div>
            <h6><fmt:message bundle="${i18n}" key="sort.byFabricator"/></h6>
            <c:forEach var="fabricator" items="${listFabricator}" varStatus="status">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="fabricator" value="${fabricator}"
                           id="defaultCheck1">
                    <label class="form-check-label" for="defaultCheck1">
                            ${fabricator}
                    </label>
                </div>
            </c:forEach>
            <div class="form-group row">
                <label for="range_03" class="col-sm-2 control-label"><fmt:message bundle="${i18n}"
                                                                                  key="product.price"/></label>
                <div class="col-sm-10">
                    <input type="text" name="price" id="range_03">
                </div>
            </div>
            <br><br>
            <div class="form-group row">
                <label for="range_04" class="col-sm-2 control-label"><fmt:message bundle="${i18n}"
                                                                                  key="product.diagonal"/></label>
                <div class="col-sm-10">
                    <input type="text" name="diagonal" id="range_04">
                </div>
            </div>
            <button class="btn btn-primary my-2 my-sm-0" name="command" value="shop" type="submit"><b><fmt:message
                    bundle="${i18n}" key="sort.sort"/></b></button>
        </fieldset>
    </form>
</div>
