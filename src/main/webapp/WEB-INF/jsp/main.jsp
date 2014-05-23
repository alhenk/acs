<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="height" value="400"/>
<mtag:pagetemplate title="main">
    <jsp:body>
        <div class="grid_8">
            <div class="box">
                <h2>
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <a href="#"><fmt:message bundle="${msg}" key="common.sing-in"/></a>
                        </c:when>
                        <c:otherwise>
                            <a href="#"><fmt:message bundle="${msg}" key="common.welcome"/></a>
                        </c:otherwise>
                    </c:choose>
                </h2>

                <div class="block">
                    <div id="sign-form" style="height:${height}px;">
                        <p> &nbsp;</p>

                        <p> &nbsp;</p>

                        <div class="grid_8">
                            <c:choose>
                                <c:when test="${empty sessionScope['user']}">
                                    <%@include file="signin-form.jsp" %>
                                    <c:if test="${not empty param.error}">
                                        <p style="color:red;">
                                            <fmt:message bundle="${msg}" key="${param.error}"/>
                                            <c:remove var="error" scope="session"/>
                                        </p>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <h3><fmt:message bundle="${msg}" key="common.welcome"/>
                                            ${sessionScope.user.username}
                                    </h3>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <mtag:info height="${height}">
            <jsp:body>
                <c:choose>
                    <c:when test="${locale.language == 'en'}">
                        <mtag:policy_en/>
                    </c:when>
                    <c:when test="${locale.language == 'ru'}">
                        <mtag:policy_ru/>
                    </c:when>
                </c:choose>
            </jsp:body>
        </mtag:info>
    </jsp:body>
</mtag:pagetemplate>
