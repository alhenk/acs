<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="dashboard">
    <jsp:body>
        <div class="grid_8">
            <div class="box">
                <h2>
                    <a href="#"><fmt:message bundle="${msg}" key="create.account"/></a>
                </h2>
                <%@ include file="signup-form.jsp" %>
                <c:if test="${not empty sessionScope.error}">
                    <p style="color:red;">
                        <fmt:message bundle="${msg}" key="${sessionScope.error}"/>
                        <c:remove var="error" scope="session"/>
                    </p>
                </c:if>
                <c:if test="${not empty sessionScope.status}">
                    <p>
                        <fmt:message bundle="${msg}" key="${sessionScope.status}"/>
                        <c:remove var="status" scope="session"/>
                    </p>
                </c:if>
                <br/>
                <a href="do/user-list"><fmt:message bundle="${msg}" key="common.user-list"/></a>
            </div>
        </div>
    </jsp:body>
</mtag:pagetemplate>
