<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="user-list">
    <jsp:body>
        <p>
            <c:if test="${not empty sessionScope.status}">
                <fmt:message bundle="${msg}" key="${sessionScope.status}"/>
                <c:remove var="status" scope="session"/>
            </c:if>
        </p>
        <table>
            <c:forEach var="user" items="${sessionScope.users}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                        <%--for debug purpose--%>
                    <td>${user.password}</td>
                    <td>${user.account1C.tableId}</td>
                    <td>${user.role}</td>
                    <td>
                        <a href="do/edit-account?id=${user.id}">edit</a>
                    </td>
                    <td>
                        <c:if test="${user.id !=1}">
                            <a href="do/delete-account?id=${user.id}">delete</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </jsp:body>
</mtag:pagetemplate>