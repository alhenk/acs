<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<mtag:pagetemplate title="dashboard">
    <jsp:body>
        <c:choose>
            <c:when test="${sessionScope.user.role=='ADMINISTRATOR'}">
                <p><fmt:message bundle="${msg}" key="common.administrator"/></p>
                <a href="do/user-list"><fmt:message bundle="${msg}" key="common.user-list"/></a>
                <br/>
                <a href="do/rfidtag-list"><fmt:message bundle="${msg}" key="common.rfidtag-list"/></a>
            </c:when>
            <c:otherwise>
                <p><fmt:message bundle="${msg}" key="common.user"/></p>
            </c:otherwise>
        </c:choose>
    </jsp:body>
</mtag:pagetemplate>