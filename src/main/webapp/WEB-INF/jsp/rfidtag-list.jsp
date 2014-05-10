<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="rfidtag-list">
    <jsp:body>
        <div class="grid_12">
            <div class="box">
                <h2>
                    <a href="do/employee-list"><fmt:message bundle="${msg}" key="common.rfidtag-list"/></a>
                </h2>

                <p>
                    <c:if test="${not empty sessionScope.status}">
                        <fmt:message bundle="${msg}" key="${sessionScope.status}"/>
                        <c:remove var="status" scope="session"/>
                    </c:if>
                </p>

                <div class="block">
                    <div id="employee-list-table" style="height:1200px;">
                        <table summary="RFID tag list">
                            <thead>
                                <%--<th>ID</th>--%>
                            <th>UID</th>
                            <th>TYPE</th>
                            <th>PROTOCOL</th>
                            <th>ISSUE DATE</th>
                            <th>EXPIRATION DATE</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            </thead>
                            <tr>
                                <td>
                                    <form method="GET" action="do/rfidtag-list">
                                        <input type="checkbox" name="sort"
                                               value="UID"  ${param.sort == 'UID' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <td>
                                    <form method="GET" action="do/rfidtag-list">
                                        <input type="checkbox" name="sort"
                                               value="TYPE"  ${param.sort == 'TYPE' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <td>
                                    <form method="GET" action="do/rfidtag-list">
                                        <input type="checkbox" name="sort"
                                               value="PROTOCOL"  ${param.sort == 'PROTOCOL' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <td>
                                    <form method="GET" action="do/rfidtag-list">
                                        <input type="checkbox" name="sort"
                                               value="ISSUE_DATE"  ${param.sort == 'ISSUE_DATE' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <td>
                                    <form method="GET" action="do/rfidtag-list">
                                        <input type="checkbox" name="sort"
                                               value="EXPIRATION_DATE"  ${param.sort == 'EXPIRATION_DATE' ? 'checked':''}
                                               onchange="submit()"/>
                                    </form>
                                </td>
                                <th>&nbsp;</th>
                                <th>&nbsp;</th>
                            </tr>
                            <c:forEach var="rfidtag" items="${sessionScope.rfidtags}">
                                <tr>
                                        <%--<td>${rfidtag.id}</td>--%>
                                    <td>${rfidtag.uid}</td>
                                    <td>${rfidtag.type}</td>
                                    <td>${rfidtag.protocol}</td>
                                    <td>
                                            ${rfidtag.issue.issueDate.date}
                                    </td>
                                    <td>
                                            ${rfidtag.issue.expirationDate.date}
                                    </td>
                                    <td>
                                        <a href="do/edit-rfidtag?id=${rfidtag.id}"><img src="img/edit.png"></a>
                                    </td>
                                    <td>
                                        <a href="do/delete-rfidtag?id=${rfidtag.id}"><img src="img/delete.png"></a>
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
                        <form action="do/create-rfidtag" method="get">
                            <input type="submit" value="<fmt:message bundle='${msg}' key='create.rfidtag'/>"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</mtag:pagetemplate>