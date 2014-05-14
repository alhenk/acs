<%@taglib prefix="ftag" tagdir="/WEB-INF/tags/fields" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form action="do/edit-user" method="post">
    <fieldset class="edit">
        <input type="hidden" name="id" value="${sessionScope.account.id}">

        <p>&nbsp;</p>
        <ftag:string-field field="username"
                           value="${sessionScope.account.username}"
                           clazz="user"/>
        <p>&nbsp;</p>
        <ftag:string-field field="email"
                           value="${sessionScope.account.email}"
                           clazz="user"/>
        <p>&nbsp;</p>
        <ftag:string-field field="table-id"
                           value="${sessionScope.account.account1C.tableId}"
                           clazz="user"/>
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
        <p>&nbsp;</p>

        <p>
            <button type="submit">
                <fmt:message bundle="${msg}" key="form.edit-user.submit"/>
            </button>
        </p>
        <p style="color:red;">
            <c:if test="${not empty param.status}">
                <fmt:message bundle="${msg}" key="${param.status}"/>
            </c:if>
        </p>
    </fieldset>
</form>
