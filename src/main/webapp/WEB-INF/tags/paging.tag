<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="entity" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="offset" value="${sessionScope.offset}"/>
<c:set var="length" value="${sessionScope.length}"/>
<c:set var="totalNumber" value="${sessionScope['total-number']}"/>
<c:set var="nextPage" value="${offset+length > totalNumber ? offset: offset+length}"/>
<c:set var="previousPage" value="${offset - length <= 0 ? 0: offset-length}"/>
<c:set var="currentPage" value="${length == 0 ? 0:offset/length+1}"/>
<c:set var="totalPages" value="${length == 0 ? 0:totalNumber/length + 1}"/>

<div class="grid_1">
    <form action="do/${entity}-list" method="get">
        <input type="hidden" name="sort" value="${param.sort}">
        <input type="hidden" name="offset" value="${previousPage}">
        <input type="submit" value="<">
    </form>
</div>
<div class="grid_1">
    <p><fmt:formatNumber type="number" value="${currentPage}" maxFractionDigits="0"/>
        /<fmt:formatNumber type="number" value="${totalPages}" maxFractionDigits="0"/>
    </p>
</div>
<div class="grid_1">
    <form action="do/${entity}-list" method="get">
        <input type="hidden" name="sort" value="${param.sort}">
        <input type="hidden" name="offset" value="${nextPage}">
        <input type="submit" value=">">
    </form>
</div>
<div class="grid_3">
    <p>&nbsp;</p>
</div>

<div class="grid_4">
    <form action="do/create-${entity}" method="get">
        <input type="submit"
               value="<fmt:message bundle='${msg}' key='create.${entity}'/>">
    </form>
</div>
</div>