<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="errorPage.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags"%>

<mtag:pagetemplate title="main">
    <jsp:body>
        <p>B&nbsp;O&nbsp;D&nbsp;Y</p>
        <%--error page test--%>
        <p>${5%0}</p>
    </jsp:body>
</mtag:pagetemplate>
