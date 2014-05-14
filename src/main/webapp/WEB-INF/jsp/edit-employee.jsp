<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<mtag:pagetemplate title="edit-employee">
    <jsp:body>
        <mtag:edit entity="employee">
            <jsp:body>
                <%@include file="edit-employee-form.jsp" %>
            </jsp:body>
        </mtag:edit>
    </jsp:body>
</mtag:pagetemplate>