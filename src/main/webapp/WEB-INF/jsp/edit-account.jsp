<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<mtag:pagetemplate title="edit-account">
    <jsp:body>
        <h2>EDIT ACCOUNT</h2>
        <%@include file="edit-account-form.jsp"%>
        <a href="do/user-list"><fmt:message bundle="${msg}" key="common.user-list"/></a>
    </jsp:body>
</mtag:pagetemplate>