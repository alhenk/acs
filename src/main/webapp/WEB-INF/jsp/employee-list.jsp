<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="employee-list">
    <jsp:body>
        <div class="grid_13">
            <div class="box">
                <h2>
                    <a href="do/employee-list"><fmt:message bundle="${msg}" key="common.employee-list"/></a>
                </h2>

                <p>
                    <c:if test="${not empty sessionScope.status}">
                        <fmt:message bundle="${msg}" key="${sessionScope.status}"/>
                        <c:remove var="status" scope="session"/>
                    </c:if>
                </p>

                <div class="block">
                    <div id="employee-list-table" style="height:1200px;">
                        <table summary="employee list">
                            <thead>
                            <th>ID</th>
                            <th>FIRST NAME</th>
                            <th>LAST NAME</th>
                            <th>BIRTH DATE</th>
                            <th>POSITION</th>
                            <th>DEPARTMENT</th>
                            <th>TABLE ID</th>
                            <th>UID</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            </tr>
                            </thead>
                            <tr>
                                <td style='width: 20px'>
                                    <form method="GET" action="do/employee-list">
                                        <input type="checkbox" name="sort"
                                               value="ID"  ${param.sort == 'ID' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <td>
                                    <form method="GET" action="do/employee-list">
                                        <input type="checkbox" name="sort"
                                               value="FIRST_NAME"  ${param.sort == 'FIRST_NAME' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <td>
                                    <form method="GET" action="do/employee-list">
                                        <input type="checkbox" name="sort"
                                               value="LAST_NAME"  ${param.sort == 'LAST_NAME' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <td>
                                    <form method="GET" action="do/employee-list">
                                        <input type="checkbox" name="sort"
                                               value="BIRTH_DATE"  ${param.sort == 'BIRTH_DATE' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>
                                    <form method="GET" action="do/employee-list">
                                        <input type="checkbox" name="sort"
                                               value="TABLE_ID"  ${param.sort == 'TABLE_ID' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <td>
                                    <form method="GET" action="do/employee-list">
                                        <input type="checkbox" name="sort"
                                               value="UID"  ${param.sort == 'UID' ? 'checked':''} onchange="submit()"/>
                                    </form>
                                </td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                            <c:forEach var="employee" items="${sessionScope.employees}">
                                <tr>
                                    <td>${employee.id}</td>
                                    <td>${employee.firstName}</td>
                                    <td>${employee.lastName}</td>
                                    <td>${employee.birthDate.date}</td>
                                    <td><fmt:message bundle="${msg}" key="structure.position.${employee.position}"/></td>
                                    <td><fmt:message bundle="${msg}" key="structure.department.${employee.department}"/></td>
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
        </div>
        <div class="grid_3">
            <div class="box">
                <h2>
                    INFO
                </h2>
                <br/>

                <div class="block">
                    <div id="user-list-control" style="height:304px;">
                        <form action="do/create-employee" method="get">
                            <input type="submit" value="<fmt:message bundle='${msg}' key='create.employee'/>">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</mtag:pagetemplate>