<%@taglib prefix="ftag" tagdir="/WEB-INF/tags/fields" %>

<form action="do/create-user" method="post">
    <fieldset class="create-user">
        <ftag:string-field field="username"
                           value="${sessionScope.username}"
                           clazz="user"/>
        <p> &nbsp;</p>
        <ftag:password-field field="password"
                             value="${sessionScope.password}"
                             clazz="user"/>
        <p> &nbsp;</p>
        <ftag:password-field field="confirm-password"
                             value="${sessionScope['confirm-password']}"
                             clazz="user"/>
        <p> &nbsp;</p>
        <ftag:string-field field="email"
                           value="${sessionScope['email']}"
                           clazz="user"/>
        <p> &nbsp;</p>

        <ftag:string-field field="table-id"
                           value="${sessionScope['table-id']}"
                           clazz="user"/>
        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.user.role"/></label>
            <select id="role" name="role">
                <option value="employee" selected><fmt:message bundle="${msg}" key="user.role.EMPLOYEE"/></option>
                <option value="supervisor"><fmt:message bundle="${msg}" key="user.role.SUPERVISOR"/></option>
                <option value="administrator"><fmt:message bundle="${msg}" key="user.role.ADMINISTRATOR"/></option>
            </select>
        </p>
        <p style="color:red;">
            <c:if test="${not empty sessionScope['role-error']}">
                <fmt:message bundle="${msg}" key="${param['role-error']}"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <button type="submit">
                <fmt:message bundle="${msg}" key="form.user.submit"/>
            </button>
        </p>

    </fieldset>
</form>
