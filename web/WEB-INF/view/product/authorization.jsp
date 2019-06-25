<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 01.06.2019
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>
<head>
    <title><fmt:message bundle="${i18n}" key="authorization.signIn"/></title>
</head>


<body>
<form method="POST" action="shop?command=authorization">
    <legend><fmt:message bundle="${i18n}" key="${errorMsg}"/></legend>
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
        <div class="col-sm-10">
            <button type="submit" class="btn btn-primary"><fmt:message bundle="${i18n}"
                                                                       key="authorization.signIn"/></button>
        </div>
    </div>
</form>
</body>
</html>
