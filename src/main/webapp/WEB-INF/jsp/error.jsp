<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" isErrorPage="true" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="error">
    <jsp:body>
        <fmt:message bundle="${msg}" key="common.error"/>
        <h3>Error Details</h3>
        <br>
        <strong>Status Code</strong>: "${sessionScope['status-code']}"
        <c:remove var="status-code" scope="session"/>
        <br>
        <strong>Requested URI</strong>: "${sessionScope['request-uri']}"
        <c:remove var="request-uri" scope="session"/>
        <br>
    </jsp:body>
</mtag:pagetemplate>