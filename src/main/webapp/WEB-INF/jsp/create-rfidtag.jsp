<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="height" value="480"/>

<mtag:pagetemplate title="create-rfidtag">
    <jsp:body>
        <mtag:create entity="rfidtag" height="${height}">
            <jsp:body>
                <%@ include file="create-rfidtag-form.jsp" %>
            </jsp:body>
        </mtag:create>
        <mtag:info height="${height}">
            <jsp:body>
                <fmt:message bundle="${msg}" key="lorem.ipsum"/>
            </jsp:body>
        </mtag:info>
    </jsp:body>
</mtag:pagetemplate>
