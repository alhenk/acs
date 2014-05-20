<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="ftag" tagdir="/WEB-INF/tags/fields" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="height" value="320"/>
<mtag:pagetemplate title="dashboard">
    <jsp:body>
        <div class="grid_8">
            <div class="box">
                <h2>
                    <a><fmt:message bundle="${msg}" key="common.dashboard"/></a>
                </h2>

                <div class="block">
                    <div id="sign-form" style="height:${height}px;">
                        <c:choose>
                            <c:when test="${sessionScope.user.role=='ADMINISTRATOR'}">
                                <h3><fmt:message bundle="${msg}" key="common.administrator"/></h3>

                                <p>&nbsp;</p>

                                <form action="do/user-list" method="get">
                                    <input type="submit" value="<fmt:message bundle="${msg}" key="common.user-list"/>"/>
                                </form>
                                <p>&nbsp;</p>

                                <form action="do/rfidtag-list" method="get">
                                    <input type="submit"
                                           value="<fmt:message bundle="${msg}" key="common.rfidtag-list"/>"/>
                                </form>
                                <p>&nbsp;</p>

                                <form action="do/employee-list" method="get">
                                    <input type="submit"
                                           value="<fmt:message bundle="${msg}" key="common.employee-list"/>"/>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <p><fmt:message bundle="${msg}" key="common.user"/></p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        <mtag:report height="${height}">
            <jsp:body>
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
                            <p>&nbsp;</p>

                            <form action="do/late-arrival-report" method="get">
                                <fieldset class="create-employee">
                                    <p> &nbsp;</p>
                                    <ftag:string-field field="year"
                                                       value="${sessionScope['year']}"
                                                       clazz="date"/>
                                    <p> &nbsp;</p>
                                    <ftag:select-field clazz="date"
                                                       field="month"
                                                       optionList="${sessionScope['months']}"
                                                       value="${sessionScope['month']}"/>
                                    <p> &nbsp;</p>

                                    <p> &nbsp;</p>
                                    <input type="submit"
                                           value="<fmt:message bundle="${msg}" key="common.late-arrival.report.monthly"/>"/>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </jsp:body>
        </mtag:report>
    </jsp:body>
</mtag:pagetemplate>