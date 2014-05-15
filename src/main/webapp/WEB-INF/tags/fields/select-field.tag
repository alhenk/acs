<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="optionList" required="true" %>
<%@attribute name="field" required="true" %>
<%@attribute name="value" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="str" uri="http://trei.kz/acs/util/strings" %>

<c:set var="regex" value="[\\[\\]\\s]"/>
<p>
<label><fmt:message bundle="${msg}" key="form.employee.${field}"/></label>
<select id="${field}" name="${field}">
    <c:forEach var="item" items="${optionList}">
        <option value="${item}" ${value == item ? 'selected' : ''}>
            <fmt:message bundle="${msg}"
                         key='employee.${field}.${str:replaceAll(item,regex,"")}'/>
        </option>
    </c:forEach>
</select>

<p style="color:red;">
    <c:if test="${not empty sessionScope['position-error']}">
        <fmt:message bundle="${msg}" key="${sessionScope['position-error']}"/>
        <c:remove var="position-error" scope="session"/>
    </c:if>
</p>