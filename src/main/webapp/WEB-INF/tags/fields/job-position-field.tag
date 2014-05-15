<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="positons" required="true" %>
<%@attribute name="value" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<p>
<label><fmt:message bundle="${msg}" key="form.employee.position"/></label>
<%--<select id="position1" name="position">--%>
<%--<option value="GENERAL_DIRECTOR" selected><fmt:message bundle="${msg}"--%>
<%--key="structure.position.GENERAL_DIRECTOR"/></option>--%>
<%--<option value="COMMERCIAL_DIRECTOR"><fmt:message bundle="${msg}"--%>
<%--key="structure.position.COMMERCIAL_DIRECTOR"/></option>--%>
<%--<option value="DEPARTMENT_HEAD"><fmt:message bundle="${msg}"--%>
<%--key="structure.position.DEPARTMENT_HEAD"/></option>--%>
<%--</select>--%>
<select id="position" name="position">
    <c:forEach var="jobPosition" items="${positons}">
        <option value="${jobPosition}" ${value == '${jobPosition}' ? 'selected' : ''}>
            <fmt:message bundle="${msg}" key="employee.position.${jobPosition}"/></option>
    </c:forEach>
</select>

<c:forEach var="jobPosition" items="${positons}">
    <p>${jobPosition}"</p>
</c:forEach>

<p style="color:red;">
    <c:if test="${not empty sessionScope['position-error']}">
        <fmt:message bundle="${msg}" key="${sessionScope['position-error']}"/>
        <c:remove var="position-error" scope="session"/>
    </c:if>
</p>