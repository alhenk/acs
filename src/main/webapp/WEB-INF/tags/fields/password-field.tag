<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="field" required="true" %>
<%@attribute name="value" required="true" %>
<%@attribute name="clazz" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="error" value="${field}-error"/>

<p>
    <label><fmt:message bundle="${msg}" key="form.${clazz}.${field}"/></label>
    <input type="password" name="${field}" value="${value}">
</p>

<p style="color:red;">
    <c:if test="${not empty param[error]}">
        <fmt:message bundle="${msg}" key="${param[error]}"/>
    </c:if>
</p>