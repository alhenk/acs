<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@taglib prefix="ftag" tagdir="/WEB-INF/tags/fields" %>

<form action="do/edit-user" method="post">
    <fieldset class="edit">
        <input type="hidden" name="id" value="${sessionScope.account.id}">
        <p>&nbsp;</p>
        <p>
            <label><fmt:message bundle="${msg}" key="form.user.name"/></label>
            <input type="text" name="username" value="${sessionScope.account.username}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['username-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['username-error']}"/>
                <c:remove var="username-error" scope="session"/>
            </c:if>
        </p>
        <p>&nbsp;</p>
        <p>
            <label><fmt:message bundle="${msg}" key="form.user.email"/></label>
            <input type="text" name="email" value="${sessionScope.account.email}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['email-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['email-error']}"/>
                <c:remove var="email-error" scope="session"/>
            </c:if>
        </p>
        <p>&nbsp;</p>
        <ftag:string-field field="table-id"/>
        <%--<p>--%>
            <%--<label><fmt:message bundle="${msg}" key="form.user.table-id"/></label>--%>
            <%--<input type="text" name="table-id" value="${sessionScope.account.account1C.tableId}">--%>
        <%--</p>--%>

        <%--<p style="color:red;">--%>
            <%--<c:if test="${not empty sessionScope['table-id-error']}">--%>
                <%--<fmt:message bundle="${msg}" key="${sessionScope['table-id-error']}"/>--%>
                <%--<c:remove var="table-id-error" scope="session"/>--%>
            <%--</c:if>--%>
        <%--</p>--%>
        <p>&nbsp;</p>
        <p>
            <label><fmt:message bundle="${msg}" key="form.user.role"/></label>
            <select id="role" name="role">
                <option value="employee" ${sessionScope.account.role == 'EMPLOYEE' ? 'selected' : ''} >
                    <fmt:message bundle="${msg}" key="form.sign-in.employee"/>
                </option>
                <option value="supervisor" ${sessionScope.account.role == 'SUPERVISOR' ? 'selected' : ''} >
                    <fmt:message bundle="${msg}" key="form.sign-in.supervisor"/>
                </option>
                <option value="administrator" ${sessionScope.account.role == 'ADMINISTRATOR' ? 'selected' : ''} >
                    <fmt:message bundle="${msg}" key="form.sign-in.administrator"/>
                </option>
            </select>
        </p>
        <p style="color:red;">
            <c:if test="${not empty sessionScope['role-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['role-error']}"/>
                <c:remove var="role-error" scope="session"/>
            </c:if>
        </p>
        <p>&nbsp;</p>
        <p>
            <button type="submit">
                <fmt:message bundle="${msg}" key="form.edit-user.submit"/>
            </button>
        </p>
    </fieldset>
</form>
