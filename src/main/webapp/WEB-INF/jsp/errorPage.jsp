<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" isErrorPage="true" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<mtag:pagetemplate title="error">
    <jsp:body>
        <%--<p>E&nbsp;R&nbsp;R&nbsp;O&nbsp;R</p>--%>
        <fmt:message bundle="${msg}" key="common.error"/>
    </jsp:body>
</mtag:pagetemplate>