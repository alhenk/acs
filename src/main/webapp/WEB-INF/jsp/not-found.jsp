<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" isErrorPage="true"%>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<mtag:pagetemplate title="error">
    <jsp:body>
        <fmt:message bundle="${msg}" key="common.not-found"/>
        <p>"${pageContext.exception}"</p>
    </jsp:body>
</mtag:pagetemplate>