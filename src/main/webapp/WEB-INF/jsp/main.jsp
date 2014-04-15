<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="errorPage.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<mtag:pagetemplate title="main">
    <jsp:body>
        <form action="do/register" method="post">
            <table>
                <tr>
                    <td align="right"><fmt:message bundle="${msg}" key="login.username"/>:</td>
                    <td align="left"><input type="text" name="username"></td>
                </tr>
                <tr>
                    <td align="right"><fmt:message bundle="${msg}" key="login.password"/> :</td>
                    <td align="left"><input type="password" name="password"></td>
                </tr>
                <tr>
                    <td align="right">
                        <button type="submit">
                            <fmt:message bundle="${msg}" key="login.submit"/>
                        </button>
                    </td>
                </tr>
            </table>
        </form>
    </jsp:body>
</mtag:pagetemplate>
