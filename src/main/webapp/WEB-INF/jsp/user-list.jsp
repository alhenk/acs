<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="user-list">
    <jsp:body>
        <div class="grid_12">
            <div class="box">
                <h2>
                    <a href="do/user-list"><fmt:message bundle="${msg}" key="common.user-list"/></a>
                </h2>

                <p>
                    <c:if test="${not empty sessionScope.status}">
                        <fmt:message bundle="${msg}" key="${sessionScope.status}"/>
                        <c:remove var="status" scope="session"/>
                    </c:if>
                </p>

                <div class="block">
                    <div id="user-list-table" style="height:320px;">
                        <table summary="user list">
                            <%--<colgroup>--%>
                                <%--<col class="colA"/>--%>
                                <%--<col class="colB"/>--%>
                                <%--<col class="colC"/>--%>
                                <%--<col class="colD"/>--%>
                                <%--<col class="colE"/>--%>
                                <%--<col class="colF"/>--%>
                            <%--</colgroup>--%>
                            <thead>
                            <tr></tr>
                            <th>USERNAME</th>
                            <th>EMAIL</th>
                            <th>TABLE ID</th>
                            <th>ROLE</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            </tr>
                            </thead>
                            <c:forEach var="user" items="${sessionScope.users}">
                                <tr>
                                    <td>${user.username}</td>
                                    <td>${user.email}</td>
                                    <td>${user.account1C.tableId}</td>
                                    <td>${user.role}</td>
                                    <td>
                                        <a href="do/edit-account?id=${user.id}"><img src="img/edit.png"></a>
                                    </td>
                                    <td>
                                        <c:if test="${user.id !=1}">
                                            <a href="do/delete-account?id=${user.id}"><img src="img/delete.png"></a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="grid_4">
            <div class="box">
                <h2>
                    INFO
                </h2>
                <br/>

                <div class="block">
                    <div id="user-list-control" style="height:304px;">
                        <form action="do/create-account" method="get">
                            <input type="submit" value="<fmt:message bundle='${msg}' key='create.account'/>">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</mtag:pagetemplate>