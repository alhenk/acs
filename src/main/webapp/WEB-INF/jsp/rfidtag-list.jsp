<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<mtag:pagetemplate title="rfidtag-list">
    <jsp:body>
        <div class="grid_16">
        <div class="box">
            <h2>
                <a href="do/rfidtag-list"><fmt:message bundle="${msg}" key="common.rfidtag-list"/></a>
            </h2>

            <div class="block">
                <div id="rfidtag-list-control" style="height:30px;">
                    <mtag:paging entity="rfidtag"/>
                    <div class="clear"></div>
                    <table summary="RFID tag list">
                        <thead>
                        <th>UID</th>
                        <th><fmt:message bundle="${msg}" key="table.rfidtag.type"/></th>
                        <th><fmt:message bundle="${msg}" key="table.rfidtag.protocol"/></th>
                        <th><fmt:message bundle="${msg}" key="table.rfidtag.issue"/></th>
                        <th><fmt:message bundle="${msg}" key="table.rfidtag.expiration"/></th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        </thead>
                        <tr>
                            <td>
                                <form method="GET" action="do/rfidtag-list">
                                    <input type="hidden" name="offset" value="${sessionScope.offset}">
                                    <input type="checkbox" name="sort"
                                           value="UID"  ${param.sort == 'UID' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/rfidtag-list">
                                    <input type="hidden" name="offset" value="${sessionScope.offset}">
                                    <input type="checkbox" name="sort"
                                           value="TYPE"  ${param.sort == 'TYPE' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/rfidtag-list">
                                    <input type="hidden" name="offset" value="${sessionScope.offset}">
                                    <input type="checkbox" name="sort"
                                           value="PROTOCOL"  ${param.sort == 'PROTOCOL' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/rfidtag-list">
                                    <input type="hidden" name="offset" value="${sessionScope.offset}">
                                    <input type="checkbox" name="sort"
                                           value="ISSUE_DATE"  ${param.sort == 'ISSUE_DATE' ? 'checked':''}
                                           onchange="submit()"/>
                                </form>
                            </td>
                            <td>
                                <form method="GET" action="do/rfidtag-list">
                                    <input type="hidden" name="offset" value="${sessionScope.offset}">
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
                                <td>${rfidtag.uid}</td>
                                <td>${rfidtag.type}</td>
                                <td>${rfidtag.protocol}</td>
                                <td>${rfidtag.issue.issueDate.date}</td>
                                <td>${rfidtag.issue.expirationDate.date}</td>
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
    </jsp:body>
</mtag:pagetemplate>