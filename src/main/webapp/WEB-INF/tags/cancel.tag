<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="form" required="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form action="do/${form}-cancel" method="get">
    <p>
        <button type="submit">
            <fmt:message bundle="${msg}" key="form.${form}.cancel"/>
        </button>
    </p>
</form>