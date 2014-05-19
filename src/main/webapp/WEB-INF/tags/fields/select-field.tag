<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="clazz" required="true" %>
<%@attribute name="field" required="true" %>
<%@attribute name="value" required="true" %>
<%@attribute name="optionList" type="java.util.List" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<p>
    <label><fmt:message bundle="${msg}" key="form.${clazz}.${field}"/></label>
    <select id="${field}" name="${field}">
        <c:forEach var="item" items="${optionList}">
            <option value="${item}"
                ${value == item ? 'selected' : ''}>
                <fmt:message bundle="${msg}" key="${clazz}.${field}.${item}"/>
            </option>
        </c:forEach>
    </select>

<p style="color:red;">
    <c:if test="${not empty sessionScope['position-error']}">
        <fmt:message bundle="${msg}" key="${sessionScope['position-error']}"/>
        <c:remove var="position-error" scope="session"/>
    </c:if>
</p>