<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="rfidtag-list">
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
                <td>UID</td>
                <td>TYPE</td>
                <td>PROTOCOL</td>
                <td>ISSUE DATE</td>
                <td>EXPIRATION DATE</td>
            </tr>
            <c:forEach var="rfidtag" items="${sessionScope.rfidtags}">
                <tr>
                    <td>${rfidtag.id}</td>
                    <td>${rfidtag.rfidUid.value}</td>
                    <td>${rfidtag.type}</td>
                    <td>${rfidtag.protocol}</td>
                    <td>${rfidtag.issue.issueDate.date}</td>
                    <td>${rfidtag.issue.expirationDate.date}</td>
                    <td>
                        <a href="do/edit-rfidtag?id=${rfidtag.id}">edit</a>
                    </td>
                    <td>
                        <a href="do/delete-rfidtag?id=${rfidtag.id}">delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <%--<a href="do/create-account"><fmt:message bundle="${msg}" key="create.account"/></a><br/>--%>
    </jsp:body>
</mtag:pagetemplate>