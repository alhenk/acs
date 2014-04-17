<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="errorPage.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<mtag:pagetemplate title="main">
    <jsp:body>
        <c:choose>
            <c:when test="${empty sessionScope['user']}">
                <%@include file="loginForm.jsp"%>
            </c:when>
            <c:otherwise>
                <p> <fmt:message bundle="${msg}" key="main.welcome"/>
                    ${sessionScope.user.username}
                </p>
                <a href="do/signout"><fmt:message bundle="${msg}" key="common.signout"/> </a>
            </c:otherwise>
        </c:choose>
    </jsp:body>
</mtag:pagetemplate>
