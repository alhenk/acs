<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="height" required="true" %>
<%@attribute name="body" fragment="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="grid_8">
    <div class="box">
        <h2><a><fmt:message bundle="${msg}" key="common.info"/></a></h2>

        <div class="block">
            <div id="info" style="height:${height}px;">
                <jsp:doBody/>
            </div>
        </div>
    </div>
</div>

