<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="entity" required="true" %>
<%@attribute name="height" required="true" %>
<%@attribute name="body" fragment="true" %>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="grid_8">
    <div class="box">
        <h2>
            <a><fmt:message bundle="${msg}" key="fill-in.form"/></a>
        </h2>

        <div class="block">
            <div id="${entity}-form" style="height:${height}px;">
                <div class="grid_4">
                    <div class="box">
                        <div class="block">
                            <p>&nbsp;</p>
                        </div>
                    </div>
                </div>
                <div class="grid_8">
                    <div class="box">
                        <div class="block">
                            <jsp:doBody/>
                            <mtag:status/>
                            <p>&nbsp;</p>
                            <mtag:cancel form="create-${entity}"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


