<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<mtag:pagetemplate title="main">
    <jsp:body>
        <div class="grid_8">
            <div class="box">
                <h2>
                    <a href="#">Login Form</a>
                </h2>

                <div class="block">
                    <div id="sign-form" style="height:280px;">
                        <p> &nbsp;</p>

                        <p> &nbsp;</p>

                        <div class="grid_8">
                            <c:choose>
                                <c:when test="${empty sessionScope['user']}">
                                    <%@include file="signin-form.jsp" %>
                                    <c:if test="${not empty sessionScope.error}">
                                        <p style="color:red;">
                                            <fmt:message bundle="${msg}" key="${sessionScope.error}"/>
                                            <c:remove var="error" scope="session"/>
                                        </p>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <h3><fmt:message bundle="${msg}" key="common.welcome"/>
                                            ${sessionScope.user.username}
                                    </h3>
                                    <a href="do/dashboard"><fmt:message bundle="${msg}" key="common.dashboard"/></a>
                                    <br/>
                                    <a href="do/sign-out"><fmt:message bundle="${msg}" key="common.sign-out"/> </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <%--<div class="grid_8">--%>
                            <%--<p>1&nbsp;</p>--%>
                            <%--<p>2&nbsp;</p>--%>
                            <%--<p>3 asdfasdfasdf</p>--%>
                            <%--<p>4&nbsp;</p>--%>
                            <%--<p>5&nbsp;</p>--%>
                        <%--</div>--%>
                    </div>
                </div>
            </div>
        </div>
        <div class="grid_8">
            <div class="box">
                <h2><a href="#" id="specifications">ACS</a></h2>

                <div class="block">
                    <div id="info" style="height:280px;">

                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</mtag:pagetemplate>
