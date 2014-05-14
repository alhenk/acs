<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="height" value="480"/>
<mtag:pagetemplate title="edit-rfidtag">
    <jsp:body>
        <mtag:edit entity="rfidtag" height="${height}">
            <jsp:body>
                <%@include file="edit-rfidtag-form.jsp" %>
            </jsp:body>
        </mtag:edit>
        <mtag:info height="${height}">
            <jsp:body>
                <fmt:message bundle="${msg}" key="lorem.ipsum"/>
            </jsp:body>
        </mtag:info>
    </jsp:body>
</mtag:pagetemplate>