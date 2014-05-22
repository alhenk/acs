<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<mtag:pagetemplate title="report">
<jsp:body>
<div class="grid_16">
<div class="box">

<h2>
    <a><fmt:message bundle="${msg}" key="common.report"/></a>
</h2>

<div class="block">
<mtag:report-paging action="${sessionScope['report-action']}"/>
<div class="clear"></div>
<table summary="report list">
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

<tr>
<td>
    <form method="GET" action="do/${sessionScope['report-action']}">
        <c:choose>
            <c:when test="${sessionScope['report-action'] == 'group-monthly-report'
        or sessionScope['report-action'] == 'individual-monthly-report'}">
                <input type="hidden" name="year" value="${sessionScope.year}">
                <input type="hidden" name="month" value="${sessionScope.month}">
            </c:when>
            <c:when test="${sessionScope['report-action'] == 'group-daily-report'
        or sessionScope['report-action'] == 'individual-daily-report'}">
                <input type="hidden" name="report-date" value="${sessionScope['report-date']}">
            </c:when>
        </c:choose>
        <c:if test="${not empty param.sortLN}">
            <input type="hidden" name="sortLN" value="${param.sortLN}">
        </c:if>
        <c:if test="${not empty param.sortJP}">
            <input type="hidden" name="sortJP" value="${param.sortJP}">
        </c:if>
        <c:if test="${not empty param.sortDP}">
            <input type="hidden" name="sortDP" value="${param.sortDP}">
        </c:if>
        <c:if test="${not empty param.sortWD}">
            <input type="hidden" name="sortWD" value="${param.sortWD}">
        </c:if>
        <c:if test="${not empty param.sortAR}">
            <input type="hidden" name="sortAR" value="${param.sortAR}">
        </c:if>
        <c:if test="${not empty param.sortLV}">
            <input type="hidden" name="sortLV" value="${param.sortLV}">
        </c:if>
        <c:if test="${not empty param.sortOH}">
            <input type="hidden" name="sortOH" value="${param.sortOH}">
        </c:if>
        <input type="checkbox" name="sortFN"
               value="1"  ${param.sortFN == '1' ? 'checked':''}
               onchange="submit()"/>
    </form>
</td>
<td>
    <form method="GET" action="do/${sessionScope['report-action']}">
        <c:choose>
            <c:when test="${sessionScope['report-action'] == 'group-monthly-report'
        or sessionScope['report-action'] == 'individual-monthly-report'}">
                <input type="hidden" name="year" value="${sessionScope.year}">
                <input type="hidden" name="month" value="${sessionScope.month}">
            </c:when>
            <c:when test="${sessionScope['report-action'] == 'group-daily-report'
        or sessionScope['report-action'] == 'individual-daily-report'}">
                <input type="hidden" name="report-date" value="${sessionScope['report-date']}">
            </c:when>
        </c:choose>
        <c:if test="${not empty param.sortFN}">
            <input type="hidden" name="sortFN" value="${param.sortFN}">
        </c:if>
        <c:if test="${not empty param.sortJP}">
            <input type="hidden" name="sortJP" value="${param.sortJP}">
        </c:if>
        <c:if test="${not empty param.sortDP}">
            <input type="hidden" name="sortDP" value="${param.sortDP}">
        </c:if>
        <c:if test="${not empty param.sortWD}">
            <input type="hidden" name="sortWD" value="${param.sortWD}">
        </c:if>
        <c:if test="${not empty param.sortAR}">
            <input type="hidden" name="sortAR" value="${param.sortAR}">
        </c:if>
        <c:if test="${not empty param.sortLV}">
            <input type="hidden" name="sortLV" value="${param.sortLV}">
        </c:if>
        <c:if test="${not empty param.sortOH}">
            <input type="hidden" name="sortOH" value="${param.sortOH}">
        </c:if>
        <input type="checkbox" name="sortLN"
               value="1"  ${param.sortLN == '1' ? 'checked':''}
               onchange="submit()"/>
    </form>
</td>
<td>
    <form method="GET" action="do/${sessionScope['report-action']}">
        <c:choose>
            <c:when test="${sessionScope['report-action'] == 'group-monthly-report'
        or sessionScope['report-action'] == 'individual-monthly-report'}">
                <input type="hidden" name="year" value="${sessionScope.year}">
                <input type="hidden" name="month" value="${sessionScope.month}">
            </c:when>
            <c:when test="${sessionScope['report-action'] == 'group-daily-report'
        or sessionScope['report-action'] == 'individual-daily-report'}">
                <input type="hidden" name="report-date" value="${sessionScope['report-date']}">
            </c:when>
        </c:choose>
        <c:if test="${not empty param.sortFN}">
            <input type="hidden" name="sortFN" value="${param.sortFN}">
        </c:if>
        <c:if test="${not empty param.sortLN}">
            <input type="hidden" name="sortLN" value="${param.sortLN}">
        </c:if>
        <c:if test="${not empty param.sortDP}">
            <input type="hidden" name="sortDP" value="${param.sortDP}">
        </c:if>
        <c:if test="${not empty param.sortWD}">
            <input type="hidden" name="sortWD" value="${param.sortWD}">
        </c:if>
        <c:if test="${not empty param.sortAR}">
            <input type="hidden" name="sortAR" value="${param.sortAR}">
        </c:if>
        <c:if test="${not empty param.sortLV}">
            <input type="hidden" name="sortLV" value="${param.sortLV}">
        </c:if>
        <c:if test="${not empty param.sortOH}">
            <input type="hidden" name="sortOH" value="${param.sortOH}">
        </c:if>
        <input type="checkbox" name="sortJP"
               value="1"  ${param.sortJP == '1' ? 'checked':''}
               onchange="submit()"/>
    </form>
</td>
<td>
    <form method="GET" action="do/${sessionScope['report-action']}">
        <c:choose>
            <c:when test="${sessionScope['report-action'] == 'group-monthly-report'
        or sessionScope['report-action'] == 'individual-monthly-report'}">
                <input type="hidden" name="year" value="${sessionScope.year}">
                <input type="hidden" name="month" value="${sessionScope.month}">
            </c:when>
            <c:when test="${sessionScope['report-action'] == 'group-daily-report'
        or sessionScope['report-action'] == 'individual-daily-report'}">
                <input type="hidden" name="report-date" value="${sessionScope['report-date']}">
            </c:when>
        </c:choose>
        <c:if test="${not empty param.sortFN}">
            <input type="hidden" name="sortFN" value="${param.sortFN}">
        </c:if>
        <c:if test="${not empty param.sortLN}">
            <input type="hidden" name="sortLN" value="${param.sortLN}">
        </c:if>
        <c:if test="${not empty param.sortJP}">
            <input type="hidden" name="sortJP" value="${param.sortJP}">
        </c:if>
        <c:if test="${not empty param.sortWD}">
            <input type="hidden" name="sortWD" value="${param.sortWD}">
        </c:if>
        <c:if test="${not empty param.sortAR}">
            <input type="hidden" name="sortAR" value="${param.sortAR}">
        </c:if>
        <c:if test="${not empty param.sortLV}">
            <input type="hidden" name="sortLV" value="${param.sortLV}">
        </c:if>
        <c:if test="${not empty param.sortOH}">
            <input type="hidden" name="sortOH" value="${param.sortOH}">
        </c:if>
        <input type="checkbox" name="sortDP"
               value="1"  ${param.sortDP == '1' ? 'checked':''}
               onchange="submit()"/>
    </form>
</td>
<td>
    <form method="GET" action="do/${sessionScope['report-action']}">
        <c:choose>
            <c:when test="${sessionScope['report-action'] == 'group-monthly-report'
        or sessionScope['report-action'] == 'individual-monthly-report'}">
                <input type="hidden" name="year" value="${sessionScope.year}">
                <input type="hidden" name="month" value="${sessionScope.month}">
            </c:when>
            <c:when test="${sessionScope['report-action'] == 'group-daily-report'
        or sessionScope['report-action'] == 'individual-daily-report'}">
                <input type="hidden" name="report-date" value="${sessionScope['report-date']}">
            </c:when>
        </c:choose>
        <c:if test="${not empty param.sortFN}">
            <input type="hidden" name="sortFN" value="${param.sortFN}">
        </c:if>
        <c:if test="${not empty param.sortLN}">
            <input type="hidden" name="sortLN" value="${param.sortLN}">
        </c:if>
        <c:if test="${not empty param.sortJP}">
            <input type="hidden" name="sortJP" value="${param.sortJP}">
        </c:if>
        <c:if test="${not empty param.sortDP}">
            <input type="hidden" name="sortDP" value="${param.sortDP}">
        </c:if>
        <c:if test="${not empty param.sortAR}">
            <input type="hidden" name="sortAR" value="${param.sortAR}">
        </c:if>
        <c:if test="${not empty param.sortLV}">
            <input type="hidden" name="sortLV" value="${param.sortLV}">
        </c:if>
        <c:if test="${not empty param.sortOH}">
            <input type="hidden" name="sortOH" value="${param.sortOH}">
        </c:if>
        <input type="checkbox" name="sortWD"
               value="1"  ${param.sortWD == '1' ? 'checked':''}
               onchange="submit()"/>
    </form>
</td>
<td>
    <form method="GET" action="do/${sessionScope['report-action']}">
        <c:choose>
            <c:when test="${sessionScope['report-action'] == 'group-monthly-report'
        or sessionScope['report-action'] == 'individual-monthly-report'}">
                <input type="hidden" name="year" value="${sessionScope.year}">
                <input type="hidden" name="month" value="${sessionScope.month}">
            </c:when>
            <c:when test="${sessionScope['report-action'] == 'group-daily-report'
        or sessionScope['report-action'] == 'individual-daily-report'}">
                <input type="hidden" name="report-date" value="${sessionScope['report-date']}">
            </c:when>
        </c:choose>
        <c:if test="${not empty param.sortFN}">
            <input type="hidden" name="sortFN" value="${param.sortFN}">
        </c:if>
        <c:if test="${not empty param.sortLN}">
            <input type="hidden" name="sortLN" value="${param.sortLN}">
        </c:if>
        <c:if test="${not empty param.sortJP}">
            <input type="hidden" name="sortJP" value="${param.sortJP}">
        </c:if>
        <c:if test="${not empty param.sortDP}">
            <input type="hidden" name="sortDP" value="${param.sortDP}">
        </c:if>
        <c:if test="${not empty param.sortWD}">
            <input type="hidden" name="sortWD" value="${param.sortWD}">
        </c:if>
        <c:if test="${not empty param.sortLV}">
            <input type="hidden" name="sortLV" value="${param.sortLV}">
        </c:if>
        <c:if test="${not empty param.sortOH}">
            <input type="hidden" name="sortOH" value="${param.sortOH}">
        </c:if>
        <input type="checkbox" name="sortAR"
               value="1"  ${param.sortAR == '1' ? 'checked':''}
               onchange="submit()"/>
    </form>
</td>
<td>
    <form method="GET" action="do/${sessionScope['report-action']}">
        <c:choose>
            <c:when test="${sessionScope['report-action'] == 'group-monthly-report'
        or sessionScope['report-action'] == 'individual-monthly-report'}">
                <input type="hidden" name="year" value="${sessionScope.year}">
                <input type="hidden" name="month" value="${sessionScope.month}">
            </c:when>
            <c:when test="${sessionScope['report-action'] == 'group-daily-report'
        or sessionScope['report-action'] == 'individual-daily-report'}">
                <input type="hidden" name="report-date" value="${sessionScope['report-date']}">
            </c:when>
        </c:choose>
        <c:if test="${not empty param.sortFN}">
            <input type="hidden" name="sortFN" value="${param.sortFN}">
        </c:if>
        <c:if test="${not empty param.sortLN}">
            <input type="hidden" name="sortLN" value="${param.sortLN}">
        </c:if>
        <c:if test="${not empty param.sortJP}">
            <input type="hidden" name="sortJP" value="${param.sortJP}">
        </c:if>
        <c:if test="${not empty param.sortDP}">
            <input type="hidden" name="sortDP" value="${param.sortDP}">
        </c:if>
        <c:if test="${not empty param.sortWD}">
            <input type="hidden" name="sortWD" value="${param.sortWD}">
        </c:if>
        <c:if test="${not empty param.sortAR}">
            <input type="hidden" name="sortAR" value="${param.sortAR}">
        </c:if>
        <c:if test="${not empty param.sortOH}">
            <input type="hidden" name="sortOH" value="${param.sortOH}">
        </c:if>
        <input type="checkbox" name="sortLV"
               value="1"  ${param.sortLV == '1' ? 'checked':''}
               onchange="submit()"/>
    </form>
</td>
<td>
    <form method="GET" action="do/${sessionScope['report-action']}">
        <c:choose>
            <c:when test="${sessionScope['report-action'] == 'group-monthly-report'
        or sessionScope['report-action'] == 'individual-monthly-report'}">
                <input type="hidden" name="year" value="${sessionScope.year}">
                <input type="hidden" name="month" value="${sessionScope.month}">
            </c:when>
            <c:when test="${sessionScope['report-action'] == 'group-daily-report'
        or sessionScope['report-action'] == 'individual-daily-report'}">
                <input type="hidden" name="report-date" value="${sessionScope['report-date']}">
            </c:when>
        </c:choose>
        <c:if test="${not empty param.sortFN}">
            <input type="hidden" name="sortFN" value="${param.sortFN}">
        </c:if>
        <c:if test="${not empty param.sortLN}">
            <input type="hidden" name="sortLN" value="${param.sortLN}">
        </c:if>
        <c:if test="${not empty param.sortJP}">
            <input type="hidden" name="sortJP" value="${param.sortJP}">
        </c:if>
        <c:if test="${not empty param.sortDP}">
            <input type="hidden" name="sortDP" value="${param.sortDP}">
        </c:if>
        <c:if test="${not empty param.sortWD}">
            <input type="hidden" name="sortWD" value="${param.sortWD}">
        </c:if>
        <c:if test="${not empty param.sortAR}">
            <input type="hidden" name="sortAR" value="${param.sortAR}">
        </c:if>
        <c:if test="${not empty param.sortLV}">
            <input type="hidden" name="sortLV" value="${param.sortLV}">
        </c:if>
        <input type="checkbox" name="sortOH"
               value="1"  ${param.sortOH == '1' ? 'checked':''}
               onchange="submit()"/>
    </form>
</td>
</tr>

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
