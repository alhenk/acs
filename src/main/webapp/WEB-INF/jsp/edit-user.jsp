<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<mtag:pagetemplate title="edit-user">
    <jsp:body>
        <div class="grid_8">
            <div class="box">
                <h2>
                    <a><fmt:message bundle="${msg}" key="edit.user"/></a>
                </h2>

                <div class="block">
                    <div id="edit-user" style="height:420px;">
                        <div class="grid_4">
                            <p>&nbsp;</p>
                        </div>
                        <div class="grid_8">
                            <div class="box">
                                <div class="block">
                                    <%@include file="edit-user-form.jsp" %>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="grid_8">
            <div class="box">
                <h2><a><fmt:message bundle="${msg}" key="common.info"/> </a></h2>
                <div class="block">
                    <div id="info-edit-user" style="height:420px;">
                        <p>&nbsp;</p>
                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
</mtag:pagetemplate>