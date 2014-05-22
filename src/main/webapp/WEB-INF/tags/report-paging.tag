<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="action" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="page" value="${sessionScope.page}"/>
<c:set var="numPages" value="${sessionScope['num-pages']}"/>
<c:set var="nextPage" value="${page + 1 <= numPages ? page + 1: page}"/>
<c:set var="previousPage" value="${page - 1 <= 0 ? 1 : page-1}"/>

<div class="grid_1">
    <form action="do/${action}" method="get">
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
        <c:if test="${not empty param.sortOH}">
            <input type="hidden" name="sortOH" value="${param.sortOH}">
        </c:if>
        <input type="hidden" name="page" value="${previousPage}">
        <input type="submit" value="<"  ${page <= 1 ? 'disabled':''}/>

    </form>
</div>
<div class="grid_1"> ${page}/${numPages} </div>
<div class="grid_1">
    <form action="do/${action}" method="get">
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
        <c:if test="${not empty param.sortOH}">
            <input type="hidden" name="sortOH" value="${param.sortOH}">
        </c:if>
        <input type="hidden" name="page" value="${nextPage}">
        <input type="submit" value=">" ${page >= numPages ? 'disabled':''} />
    </form>
</div>
