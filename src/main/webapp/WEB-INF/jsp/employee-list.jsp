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
                <td>ID</td>
                <td>FIRST NAME</td>
                <td>LAST NAME</td>
                <td>BIRTH DATE</td>
                <td>POSITION</td>
                <td>TABLE ID</td>
                <td>UID</td>
            </tr>
            <c:forEach var="employee" items="${sessionScope.employees}">
                <tr>
                    <td>${employee.id}</td>
                    <td>${employee.firstName}</td>
                    <td>${employee.lastName}</td>
                    <%--<td>${employee.birthDate}</td>--%>
                    <%--<td>${employee.position}</td>--%>
                    <td>${employee.account1C.tableId}</td>
                    <%--<td>${employee.uid}</td>--%>
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