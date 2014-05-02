<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="title" required="true" %>

<h1 style="letter-spacing: 10pt;">
    <fmt:message bundle='${msg}' key='common.attendance'/>
</h1>

<h2><fmt:message bundle='${msg}' key='common.${title}'/></h2>
<c:if test="${not empty sessionScope.user}">
    <fmt:message bundle='${msg}' key='common.user'/> &nbsp; ${sessionScope.user.username}
</c:if>
