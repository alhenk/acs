<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="param" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty  param.param }">
    <input type="hidden" name="${param}" value="${param.param}">
</c:if>
