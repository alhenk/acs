<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="employee-list">
    <jsp:body>
        <p>
            <c:if test="${not empty sessionScope.status}">
                <fmt:message bundle="${msg}" key="${sessionScope.status}"/>
                <c:remove var="status" scope="session"/>
            </c:if>
        </p>


        <table>
            <tr>
                <td style='width: 20px'>
                    <form method="GET" action="do/employee-list">
                        <input type="checkbox" name="sort" value="ID"  ${param.sort == 'ID' ? 'checked':''} onchange="submit()"/>
                    </form >
                    ID
                </td>
                <td>
                    <form method="GET" action="do/employee-list">
                        <input type="checkbox" name="sort" value="FIRST_NAME"  ${param.sort == 'FIRST_NAME' ? 'checked':''} onchange="submit()"/>
                    </form >
                    FIRST NAME
                </td>
                <td>
                    <form method="GET" action="do/employee-list">
                        <input type="checkbox" name="sort"
                               value="LAST_NAME"  ${param.sort == 'LAST_NAME' ? 'checked':''} onchange="submit()"/>
                    </form>
                    LAST NAME
                </td>
                <td>
                    <form method="GET" action="do/employee-list">
                        <input type="checkbox" name="sort"
                               value="BIRTH_DATE"  ${param.sort == 'BIRTH_DATE' ? 'checked':''} onchange="submit()"/>
                    </form>
                    BIRTH DATE
                </td>
                <td>POSITION</td>
                <td>DEPARTMENT</td>
                <td>
                    <form method="GET" action="do/employee-list">
                        <input type="checkbox" name="sort"
                               value="TABLE_ID"  ${param.sort == 'TABLE_ID' ? 'checked':''} onchange="submit()"/>
                    </form>
                    TABLE ID
                </td>
                <td>
                    <form method="GET" action="do/employee-list">
                        <input type="checkbox" name="sort"
                               value="UID"  ${param.sort == 'UID' ? 'checked':''} onchange="submit()"/>
                    </form>
                    UID
                </td>
            </tr>
            <c:forEach var="employee" items="${sessionScope.employees}">
                <tr>
                    <td>${employee.id}</td>
                    <td>${employee.firstName}</td>
                    <td>${employee.lastName}</td>
                    <td>${employee.birthDate.date}</td>
                    <td>${employee.position}</td>
                    <td>${employee.department}</td>
                    <td>${employee.account1C.tableId}</td>
                    <td>${employee.rfidTag.uid}</td>
                    <td>
                        <a href="do/edit-employee?id=${employee.id}">edit</a>
                    </td>
                    <td>
                        <a href="do/delete-employee?id=${employee.id}">delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <a href="do/create-employee"><fmt:message bundle="${msg}" key="create.employee"/></a><br/>
    </jsp:body>
</mtag:pagetemplate>