<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="errorPage.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="user-list">
<jsp:body>
    <table>
    <c:forEach var="user" items="${sessionScope.users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <%--for debug purpose--%>
                <td>${user.password}</td>
                <td>${user.tableId.tableId}</td>
                <td>${user.role}</td>
            </tr>
    </c:forEach>
    </table>
</jsp:body>
</mtag:pagetemplate>