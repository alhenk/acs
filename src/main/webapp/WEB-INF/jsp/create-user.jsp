<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="create-user">
    <jsp:body>
        <div class="grid_8">
            <div class="box">
                <h2>
                    <a><fmt:message bundle="${msg}" key="fill-in.form"/></a>
                </h2>

                <div class="block">
                    <div id="sign-form" style="height:540px;">
                        <div class="grid_4">
                            <div class="box">
                                <div class="block">
                                    <p>&nbsp;</p>
                                </div>
                            </div>
                        </div>
                        <div class="grid_8">
                            <div class="box">
                                <div class="block">
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
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="grid_8">
            <div class="box">
                <h2><a><fmt:message bundle="${msg}" key="common.info"/></a></h2>

                <div class="block">
                    <div id="info" style="height:540px;">
                        <p>&nbsp;</p>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</mtag:pagetemplate>
