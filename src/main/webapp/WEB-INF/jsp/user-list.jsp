<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="user-list">
    <jsp:body>
        <div class="grid_16">
            <div class="box">
                <h2>
                    <a href="do/user-list"><fmt:message bundle="${msg}" key="common.user-list"/></a>
                </h2>
                <div class="block">
                    <div id="user-list-control" style="height:30px;">
                        <mtag:paging entity="user"/>
                    <div class="clear"></div>
                        <table summary="user list">
                            <thead>
                            <th><fmt:message bundle="${msg}" key="table.user-list.username"/></th>
                            <th>E-MAIL</th>
                            <th><fmt:message bundle="${msg}" key="table.user-list.table-id"/></th>
                            <th><fmt:message bundle="${msg}" key="table.user-list.role"/></th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            </tr>
                            </thead>
                            <tr>
                                <td>
                                    <form method="GET" action="do/user-list">
                                        <input type="hidden" name="offset" value="${sessionScope.offset}">
                                        <input type="checkbox" name="sort"
                                               value="USER_NAME"  ${param.sort == 'USER_NAME' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <td>
                                    <form method="GET" action="do/user-list">
                                        <input type="hidden" name="offset" value="${sessionScope.offset}">
                                        <input type="checkbox" name="sort"
                                               value="EMAIL"  ${param.sort == 'EMAIL' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <td>
                                    <form method="GET" action="do/user-list">
                                        <input type="hidden" name="offset" value="${sessionScope.offset}">
                                        <input type="checkbox" name="sort"
                                               value="TABLE_ID"  ${param.sort == 'TABLE_ID' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <td>
                                    <form method="GET" action="do/user-list">
                                        <input type="hidden" name="offset" value="${sessionScope.offset}">
                                        <input type="checkbox" name="sort"
                                               value="ROLE"  ${param.sort == 'ROLE' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                            <c:forEach var="user" items="${sessionScope.users}">
                                <tr>
                                    <td>${user.username}</td>
                                    <td>${user.email}</td>
                                    <td>${user.account1C.tableId}</td>
                                    <td>
                                    <fmt:message bundle="${msg}" key="user.role.${user.role}"/>
                                    <td>
                                        <a href="do/edit-user?id=${user.id}"><img src="img/edit.png"></a>
                                    </td>
                                    <td>
                                        <c:if test="${user.id !=1}">
                                            <a href="do/delete-user?id=${user.id}"><img src="img/delete.png"></a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
    </jsp:body>
</mtag:pagetemplate>