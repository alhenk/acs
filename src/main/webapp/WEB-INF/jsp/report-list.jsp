<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<mtag:pagetemplate title="report">
    <jsp:body>
        <div class="grid_16">
            <div class="box">
                <h2>
                    <a href="do/user-list"><fmt:message bundle="${msg}" key="common.late-arrival"/></a>
                </h2>

                <div class="block">
                    <table summary="user list">
                        <thead>
                        <th><fmt:message bundle="${msg}" key="table.report.first-name"/></th>
                        <th><fmt:message bundle="${msg}" key="table.report.last-name"/></th>
                        <th><fmt:message bundle="${msg}" key="table.report.position"/></th>
                        <th><fmt:message bundle="${msg}" key="table.report.department"/></th>
                        <th><fmt:message bundle="${msg}" key="table.report.working-day"/></th>
                        <th><fmt:message bundle="${msg}" key="table.report.arriving"/></th>
                        <th><fmt:message bundle="${msg}" key="table.report.leaving"/></th>
                        <th><fmt:message bundle="${msg}" key="table.report.office-hours"/></th>
                        </tr>
                        </thead>

                        <c:forEach var="officeHour" items="${sessionScope['office-hour-list']}">
                            <tr>
                                <td>${officeHour.employee.firstName}</td>
                                <td>${officeHour.employee.lastName}</td>
                                <td><fmt:message bundle="${msg}"
                                                 key="employee.position.${officeHour.employee.position}"/>
                                </td>
                                <td><fmt:message bundle="${msg}"
                                                 key="employee.department.${officeHour.employee.department}"/>
                                </td>
                                <td>${officeHour.workingDay.date}</td>
                                <td>${officeHour.arriving.time}</td>
                                <td>${officeHour.leaving.time}</td>
                                <td>${officeHour.total.time}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </jsp:body>
</mtag:pagetemplate>
