<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="field" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<c:set var="error" value="${field}-error" />--%>

<p>
    <label><fmt:message bundle="${msg}" key="form.table-id"/></label>
    <input type="text" name="table-id" value="${sessionScope.account.account1C.tableId}">
</p>

<p style="color:red;">
    <c:if test="${not empty param['table-id-error']}">
        <fmt:message bundle="${msg}" key="${param['table-id-error']}"/>
    </c:if>
</p>