<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="entity" required="true" %>
<%@attribute name="body" fragment="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="grid_8">
    <div class="box">
        <h2>
            <a><fmt:message bundle="${msg}" key="fill-in.form"/></a>
        </h2>

        <div class="block">
            <div id="${entity}" style="height:420px;">
                <div class="grid_4">
                    <p>&nbsp;</p>
                </div>
                <div class="grid_8">
                    <div class="box">
                        <div class="block">
                            <jsp:doBody/>
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
            <div id="info-edit-${entity}" style="height:420px;">
                <p>&nbsp;</p>
            </div>
        </div>
    </div>
</div>