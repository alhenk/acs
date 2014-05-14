<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="height" value="640"/>
<mtag:pagetemplate title="create-employee">
    <jsp:body>
        <mtag:create entity="employee" height="${height}">
            <jsp:body>
                <%@ include file="create-employee-form.jsp" %>
            </jsp:body>
        </mtag:create>
        <mtag:info height="${height}">
            <jsp:body>
                <fmt:message bundle="${msg}" key="lorem.ipsum"/>
            </jsp:body>
        </mtag:info>
    </jsp:body>
</mtag:pagetemplate>


<%--<jsp:body>--%>
<%--<div class="grid_8">--%>
<%--<div class="box">--%>
<%--<h2>--%>
<%--<a><fmt:message bundle="${msg}" key="fill-in.form"/></a>--%>
<%--</h2>--%>

<%--<div class="block">--%>
<%--<div id="sign-form" style="height:640px;">--%>
<%--<div class="grid_4">--%>
<%--<div class="box">--%>
<%--<div class="block">--%>
<%--<p>&nbsp;</p>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="grid_8">--%>
<%--<div class="box">--%>
<%--<div class="block">--%>
<%--<%@ include file="create-employee-form.jsp" %>--%>
<%--<c:if test="${not empty param.error}">--%>
<%--<p style="color:red;">--%>
<%--<fmt:message bundle="${msg}" key="${param.error}"/>--%>
<%--</p>--%>
<%--</c:if>--%>
<%--<c:if test="${not empty param.status}">--%>
<%--<p>--%>
<%--<fmt:message bundle="${msg}" key="${param.status}"/>--%>
<%--</p>--%>
<%--</c:if>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="grid_8">--%>
<%--<div class="box">--%>
<%--<h2><a><fmt:message bundle="${msg}" key="common.info"/></a></h2>--%>

<%--<div class="block">--%>
<%--<div id="info" style="height:640px;">--%>
<%--<p>&nbsp;</p>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</jsp:body>--%>
<%--</mtag:pagetemplate>--%>