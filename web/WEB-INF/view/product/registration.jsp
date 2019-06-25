<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 01.06.2019
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Title</title>
</head>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<body>

<form method="POST" action="shop?command=registration">
    <legend><fmt:message bundle="${i18n}" key="${errorMsg}"/></legend>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"><fmt:message bundle="${i18n}"
                                                            key="registration.name"/></label>
        <div class="col-sm-4">
            <input type="text" class="form-control" name="name" maxlength="25" value="${name}"
                   placeholder="<fmt:message bundle="${i18n}" key="registration.name"/>">
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"><fmt:message bundle="${i18n}"
                                                            key="registration.login"/></label>
        <div class="col-sm-4">
            <input type="text" class="form-control" name="login" maxlength="20"
                   placeholder="<fmt:message bundle="${i18n}" key="registration.login"/>">
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"><fmt:message bundle="${i18n}"
                                                            key="registration.password"/></label>
        <div class="col-sm-4">
            <input type="password" class="form-control" name="password" maxlength="20"
                   placeholder="<fmt:message bundle="${i18n}" key="registration.password"/>">
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"><fmt:message bundle="${i18n}"
                                                            key="registration.repeatPassword"/></label>
        <div class="col-sm-4">
            <input type="password" class="form-control" name="repeatPassword" maxlength="20"
                   placeholder="<fmt:message bundle="${i18n}" key="registration.repeatPassword"/>">
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-10">
            <button type="submit" class="btn btn-primary"><fmt:message bundle="${i18n}"
                                                                       key="registration.signAp"/></button>
        </div>
    </div>
</form>

</body>
</html>
