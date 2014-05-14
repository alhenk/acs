<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" isErrorPage="true" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<mtag:pagetemplate title="error">
    <jsp:body>
        <div class="grid_16">
            <div class="box">
                <h2>
                    <a><fmt:message bundle="${msg}" key="common.error"/></a>
                </h2>

                <div class="block">
                    <div id="error" style="height:320px;">
                        <div class="grid_6">
                            <div class="block">
                                &nbsp;
                            </div>
                        </div>
                        <div class="grid_8">
                            <div class="box">
                                <h3><fmt:message bundle="${msg}" key="common.details"/></h3>
                                <br>
                                <c:if test="${not empty param.error}">
                                    <fmt:message bundle="${msg}" key="${param.error}"/>
                                </c:if>
                                <br/>
                                <c:if test="${not empty sessionScope['status-code']}">
                                    <strong>Status Code</strong>: "${sessionScope['status-code']}"
                                </c:if>
                                <c:choose>
                                    <c:when test="${sessionScope['status-code']=='401'}">
                                        <h3><fmt:message bundle="${msg}" key="common.unauthorized"/></h3>
                                    </c:when>
                                    <c:when test="${sessionScope['status-code']=='403'}">
                                        <h3><fmt:message bundle="${msg}" key="common.forbidden"/></h3>
                                    </c:when>
                                    <c:when test="${sessionScope['status-code']=='404'}">
                                        <h3><fmt:message bundle="${msg}" key="common.not-found"/></h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3><fmt:message bundle="${msg}" key="common.error"/></h3>
                                    </c:otherwise>
                                </c:choose>
                                <c:remove var="status-code" scope="session"/>
                                <br>
                                <c:if test="${not empty sessionScope['request-uri'] && sessionScope.user.role == 'ADMINISTRATOR'}">
                                    <strong>Requested URI</strong>: "${sessionScope['request-uri']}"
                                    <c:remove var="request-uri" scope="session"/>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</mtag:pagetemplate>