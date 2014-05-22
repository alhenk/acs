<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="entity" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="page" value="${sessionScope.page}"/>
<c:set var="numPages" value="${sessionScope['num-pages']}"/>
<c:set var="nextPage" value="${page + 1 <= numPages ? page + 1: page}"/>
<c:set var="previousPage" value="${page - 1 <= 0 ? 1 : page-1}"/>
<div class="grid_16">
    <div class="box">
        <div class="grid_1">
            <form action="do/${entity}-list" method="get">
                <c:if test="${not empty param.sort}">
                    <input type="hidden" name="sort" value="${param.sort}">
                </c:if>
                <input type="hidden" name="page" value="${previousPage}">
                <input type="submit" value="<"  ${page <= 1 ? 'disabled':''}/>

            </form>
        </div>
        <div class="grid_1"> ${page}/${numPages} </div>
        <div class="grid_1">
            <form action="do/${entity}-list" method="get">
                <c:if test="${not empty param.sort}">
                    <input type="hidden" name="sort" value="${param.sort}">
                </c:if>
                <input type="hidden" name="page" value="${nextPage}">
                <input type="submit" value=">" ${page >= numPages ? 'disabled':''} />
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
</div>
