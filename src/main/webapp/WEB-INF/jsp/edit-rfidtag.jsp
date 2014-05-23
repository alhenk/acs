<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="height" value="520"/>
<mtag:pagetemplate title="edit-rfidtag">
    <jsp:body>
        <mtag:edit entity="rfidtag" height="${height}">
            <jsp:body>
                <%@include file="edit-rfidtag-form.jsp" %>
            </jsp:body>
        </mtag:edit>
        <mtag:info height="${height}">
            <jsp:body>
                <c:choose>
                    <c:when test="${locale.language == 'en'}">
                        <mtag:rfidtag_rules_en/>
                    </c:when>
                    <c:when test="${locale.language == 'ru'}">
                        <mtag:rfidtag_rules_ru/>
                    </c:when>
                </c:choose>
            </jsp:body>
        </mtag:info>
    </jsp:body>
</mtag:pagetemplate>