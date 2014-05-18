<%@tag language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty param.error}">
    <p style="color:red;">
        <fmt:message bundle="${msg}" key="${param.error}"/>
    </p>
</c:if>
<c:if test="${not empty param.status}">
    <p>
        <fmt:message bundle="${msg}" key="${param.status}"/>
    </p>
</c:if>