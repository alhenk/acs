<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" isErrorPage="true" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="exception">
    <jsp:body>
        <div class="grid_16">
            <div class="box">
                <h2>
                    <a><fmt:message bundle="${msg}" key="common.exception"/></a>
                </h2>

                <div class="block">
                    <div id="error" style="height:320px;">
                        <div class="grid_6">
                                <%--<div class="box">--%>
                            <div class="block">
                                &nbsp;
                            </div>
                                <%--</div>--%>
                        </div>
                        <div class="grid_8">
                            <div class="box">
                                <h3><fmt:message bundle="${msg}" key="common.details"/></h3>
                                <br/>

                                <c:if test="${not empty sessionScope.error}">
                                    <fmt:message bundle="${msg}" key="${sessionScope.error}"/>
                                    <c:remove var="error" scope="session"/>
                                </c:if>
                                <br/>
                                <ul>
                                    <c:if test="${not empty sessionScope['status-code']}">
                                        <li>
                                            <strong>Status Code</strong>: "${sessionScope['status-code']}"
                                            <c:remove var="status-code" scope="session"/>
                                        </li>
                                    </c:if>
                                    <br/>
                                    <c:if test="${not empty sessionScope['servlet-name']}">
                                        <li>Servlet Name: "${sessionScope['servlet-name']}"</li>
                                        <c:remove var="servlet-name" scope="session"/>
                                    </c:if>
                                    <br/>
                                    <c:if test="${not empty sessionScope['exception-name']}">
                                        <li>Exception Name: "${sessionScope['exception-name']}"</li>
                                        <c:remove var="exception-name" scope="session"/>
                                    </c:if>
                                    <br/>
                                    <c:if test="${not empty sessionScope['request-uri']}">
                                        <li>Requested URI: "${sessionScope['request-uri']}"</li>
                                        <c:remove var="request-uri" scope="session"/>
                                    </c:if>
                                    <br/>
                                    <c:if test="${not empty sessionScope['exception-message']}">
                                        <li>Exception Message: "${sessionScope['exception-message']}"</li>
                                        <c:remove var="exception-message" scope="session"/>
                                    </c:if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</mtag:pagetemplate>