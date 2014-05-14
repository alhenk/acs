<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="value" required="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<p>
    <label><fmt:message bundle="${msg}" key="form.rfidtag.type"/></label>
    <select id="type" name="type">
        <option value="CARD" ${value == 'CARD' ? 'selected' : ''} >
            <fmt:message bundle="${msg}" key="form.rfidtag.type.card"/></option>
        <option value="KEYFOB" ${value == 'KEYFOB' ? 'selected' : ''} >
            <fmt:message bundle="${msg}" key="form.rfidtag.type.keyfob"/></option>
        <option value="STICKER" ${value == 'STICKER' ? 'selected' : ''} >
            <fmt:message bundle="${msg}" key="form.rfidtag.type.sticker"/></option>
    </select>
</p>
<p style="color:red;">
    <c:if test="${not empty param['type-error']}">
        <fmt:message bundle="${msg}" key="${param['type-error']}"/>
    </c:if>
</p>