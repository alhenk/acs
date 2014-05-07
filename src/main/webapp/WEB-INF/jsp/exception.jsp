<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" isErrorPage="true" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="exception">
    <jsp:body>
        <fmt:message bundle="${msg}" key="common.exception"/>
        <h3><fmt:message bundle="${msg}" key="common.details"/></h3>

        <%--<h2>--%>
            <%--<c:if test="${not null sessionScope.error}">--%>
                <%--${sessionScope.error}--%>
            <%--</c:if>--%>
        <%--</h2>--%>
        <ul>
            <li>
                <strong>Status Code</strong>: "${sessionScope['status-code']}"
                <c:remove var="status-code" scope="session"/>
            </li>

            <li>Servlet Name: "${sessionScope['servlet-name']}"</li>
            <c:remove var="servlet-name" scope="session"/>
            <li>Exception Name: "${sessionScope['exception-name']}"</li>
            <c:remove var="exception-name" scope="session"/>
            <li>Requested URI: "${sessionScope['request-uri']}"</li>
            <c:remove var="request-uri" scope="session"/>
            <li>Exception Message: "${sessionScope['exception-message']}"</li>
            <c:remove var="exception-message" scope="session"/>
        </ul>
    </jsp:body>
</mtag:pagetemplate>