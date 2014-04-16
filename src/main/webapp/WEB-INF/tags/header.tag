<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="title" required="true" %>
<h1 style="letter-spacing: 10pt;">
    <fmt:message bundle='${msg}' key='main.attendance'/>
</h1>

<h2><fmt:message bundle='${msg}' key='common.${title}'/></h2>