<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<mtag:pagetemplate title="employee-list">
    <jsp:body>
        <div class="grid_16">
            <div class="box">
                <h2>
                    <a href="do/employee-list"><fmt:message bundle="${msg}" key="common.employee-list"/></a>
                </h2>
                <div class="block">
                    <mtag:paging entity="employee"/>
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
                                    <input type="hidden" name="page" value="${sessionScope.page}">
                                    <input type="checkbox" name="sort"
                                           value="FIRST_NAME"  ${param.sort == 'FIRST_NAME' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>&nbsp;</td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="page" value="${sessionScope.page}">
                                    <input type="checkbox" name="sort"
                                           value="LAST_NAME"  ${param.sort == 'LAST_NAME' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="page" value="${sessionScope.page}">
                                    <input type="checkbox" name="sort"
                                           value="BIRTH_DATE"  ${param.sort == 'BIRTH_DATE' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="page" value="${sessionScope.page}">
                                    <input type="checkbox" name="sort"
                                           value="POSITION"  ${param.sort == 'POSITION' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="page" value="${sessionScope.page}">
                                    <input type="checkbox" name="sort"
                                           value="DEPARTMENT"  ${param.sort == 'DEPARTMENT' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="page" value="${sessionScope.page}">
                                    <input type="checkbox" name="sort"
                                           value="ROOM"  ${param.sort == 'ROOM' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="page" value="${sessionScope.page}">
                                    <input type="checkbox" name="sort"
                                           value="TABLE_ID"  ${param.sort == 'TABLE_ID' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/employee-list">
                                    <input type="hidden" name="page" value="${sessionScope.page}">
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
                                                 key="employee.position.${employee.position}"/></td>
                                <td><fmt:message bundle="${msg}"
                                                 key="employee.department.${employee.department}"/></td>
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