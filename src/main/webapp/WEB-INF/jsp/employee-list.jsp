<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="offset" value="${sessionScope.offset}"/>
<c:set var="length" value="${sessionScope.length}"/>
<c:set var="totalNumber" value="${sessionScope['total-number']}"/>
<c:set var="nextPage" value="${offset+length > totalNumber ? offset: offset+length}"/>
<c:set var="previousPage" value="${offset - length <= 0 ? 0: offset-length}"/>
<c:set var="currentPage" value="${length == 0 ? 0:offset/length+1}"/>
<c:set var="totalPages" value="${length == 0 ? 0:totalNumber/length + 1}"/>
<mtag:pagetemplate title="employee-list">
    <jsp:body>
        <div class="grid_16">
            <div class="box">
                <h2>
                    <a href="do/employee-list"><fmt:message bundle="${msg}" key="common.employee-list"/></a>
                </h2>

                <div class="block">
                    <div id="employee-list-control" style="height:30px;">
                        <div class="grid_1">
                            <form action="do/employee-list" method="get">
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
                            <form action="do/employee-list" method="get">
                                <input type="hidden" name="sort" value="${param.sort}">
                                <input type="hidden" name="offset" value="${nextPage}">
                                <input type="submit" value=">">
                            </form>
                        </div>
                        <div class="grid_9">
                            <p>&nbsp;</p>
                        </div>

                        <div class="grid_4">
                            <form action="do/create-employee" method="get">
                                <input type="submit"
                                       value="<fmt:message bundle='${msg}' key='create.employee'/>">
                            </form>
                        </div>
                    </div>
                    <div class="clear"></div>

                    <table summary="employee list">
                        <thead>
                        <th><fmt:message bundle='${msg}' key='table.employee.first-name'/></th>
                        <th><fmt:message bundle='${msg}' key='table.employee.patronym'/></th>
                        <th><fmt:message bundle='${msg}' key='table.employee.last-name'/></th>
                        <th><fmt:message bundle='${msg}' key='table.employee.birth-date'/></th>
                        <th><fmt:message bundle='${msg}' key='table.employee.position'/></th>
                        <th><fmt:message bundle='${msg}' key='table.employee.department'/></th>
                        <th><fmt:message bundle='${msg}' key='table.employee.room'/></th>
                        <th><fmt:message bundle='${msg}' key='table.employee.table-id'/></th>
                        <th>UID</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        </tr>
                        </thead>
                        <tr>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="offset" value="${sessionScope.offset}">
                                    <input type="checkbox" name="sort"
                                           value="FIRST_NAME"  ${param.sort == 'FIRST_NAME' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>&nbsp;</td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="offset" value="${sessionScope.offset}">
                                    <input type="checkbox" name="sort"
                                           value="LAST_NAME"  ${param.sort == 'LAST_NAME' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="offset" value="${sessionScope.offset}">
                                    <input type="checkbox" name="sort"
                                           value="BIRTH_DATE"  ${param.sort == 'BIRTH_DATE' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="offset" value="${sessionScope.offset}">
                                    <input type="checkbox" name="sort"
                                           value="POSITION"  ${param.sort == 'POSITION' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="offset" value="${sessionScope.offset}">
                                    <input type="checkbox" name="sort"
                                           value="DEPARTMENT"  ${param.sort == 'DEPARTMENT' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="offset" value="${sessionScope.offset}">
                                    <input type="checkbox" name="sort"
                                           value="ROOM"  ${param.sort == 'ROOM' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="offset" value="${sessionScope.offset}">
                                    <input type="checkbox" name="sort"
                                           value="TABLE_ID"  ${param.sort == 'TABLE_ID' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="offset" value="${sessionScope.offset}">
                                    <input type="checkbox" name="sort"
                                           value="UID"  ${param.sort == 'UID' ? 'checked':''} onchange="submit()"/>
                                </form>
                            </td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <c:forEach var="employee" items="${sessionScope.employees}">
                            <tr>
                                <td>${employee.firstName}</td>
                                <td>${employee.patronym}</td>
                                <td>${employee.lastName}</td>
                                <td>${employee.birthDate.date}</td>
                                <td><fmt:message bundle="${msg}"
                                                 key="structure.position.${employee.position}"/></td>
                                <td><fmt:message bundle="${msg}"
                                                 key="structure.department.${employee.department}"/></td>
                                <td>${employee.room.roomNumber}</td>
                                <td>${employee.account1C.tableId}</td>
                                <td>${employee.rfidTag.uid}</td>
                                <td>
                                    <a href="do/edit-employee?id=${employee.id}"><img src="img/edit.png"></a>
                                </td>
                                <td>
                                    <a href="do/delete-employee?id=${employee.id}"><img src="img/delete.png"></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </jsp:body>
</mtag:pagetemplate>