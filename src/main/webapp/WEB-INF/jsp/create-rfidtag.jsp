<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="height" value="600"/>
<mtag:pagetemplate title="create-rfidtag">
    <jsp:body>
        <mtag:create entity="rfidtag" height="${height}">
            <jsp:body>
                <%@ include file="create-rfidtag-form.jsp" %>
            </jsp:body>
        </mtag:create>
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
