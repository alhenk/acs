<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="errorPage.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="dashboard">
    <jsp:body>
                <p>CREATE ACCOUNT</p>
                <%@ include file="signupForm.jsp"%>
                <c:if test="${not empty sessionScope.error}">
                    <p  style="color:red;">
                        <fmt:message bundle="${msg}" key="${sessionScope.error}"/>
                    </p>
                </c:if>
    </jsp:body>
</mtag:pagetemplate>
