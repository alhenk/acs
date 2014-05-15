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
        <ftag:select-field clazz="user"
                           field="role"
                           optionList="${sessionScope['roles']}"
                           value="${sessionScope['role']}"/>
        <p> &nbsp;</p>

        <p>
            <button type="submit">
                <fmt:message bundle="${msg}" key="form.user.submit"/>
            </button>
        </p>

    </fieldset>
</form>
