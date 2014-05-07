<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" isErrorPage="true" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="error">
    <jsp:body>
        <fmt:message bundle="${msg}" key="common.error"/>
        <h3><fmt:message bundle="${msg}" key="common.details"/></h3>
        <br>
        <c:if test="${not empty sessionScope.error}">
            <fmt:message bundle="${msg}" key="${sessionScope.error}"/>
            <c:remove var="error" scope="session"/>
        </c:if>
        <br/>
        <strong>Status Code</strong>: "${sessionScope['status-code']}"
        <c:choose>
            <c:when test="${sessionScope['status-code']=='401'}">
                <h2><fmt:message bundle="${msg}" key="common.unauthorized"/></h2>
            </c:when>
            <c:when test="${sessionScope['status-code']=='403'}">
                <h2><fmt:message bundle="${msg}" key="common.forbidden"/></h2>
            </c:when>
            <c:when test="${sessionScope['status-code']=='404'}">
                <h2><fmt:message bundle="${msg}" key="common.not-found"/></h2>
            </c:when>
            <c:otherwise>
                <h2><fmt:message bundle="${msg}" key="common.error"/></h2>
            </c:otherwise>
        </c:choose>
        <br>
        <strong>Requested URI</strong>: "${sessionScope['request-uri']}"
        <c:remove var="request-uri" scope="session"/>
        <br>
    </jsp:body>
</mtag:pagetemplate>