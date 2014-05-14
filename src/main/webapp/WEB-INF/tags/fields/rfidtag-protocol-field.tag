<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="value" required="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<p>
    <label><fmt:message bundle="${msg}" key="form.rfidtag.protocol"/></label>
    <select id="protocol" name="protocol">
        <option value="ISO14443A" ${value == 'ISO14443A' ? 'selected' : ''} >ISO14443A</option>
        <option value="ISO15693" ${value == 'ISO14443A' ? 'selected' : ''} >ISO15693</option>
    </select>
</p>
<p style="color:red;">
    <c:if test="${not empty param['protocol-error']}">
        <fmt:message bundle="${msg}" key="${param['protocol-error']}"/>
    </c:if>
</p>