<h3><fmt:message bundle="${msg}" key="fill-in.form"/></h3>

<p> &nbsp;</p>

<form action="do/sign-up" method="post">
    <fieldset class="sign-up">
        <p>
            <label><fmt:message bundle="${msg}" key="form.user.name"/></label>
            <input type="text" name="username" value="${sessionScope["username"]}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['username-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['username-error']}"/>
                <c:remove var="username-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.user.password"/></label>
            <input type="password" name="password" value="${sessionScope["password"]}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['password-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['password-error']}"/>
                <c:remove var="password-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.user.confirm-password"/></label>
            <input type="password" name="confirm-password" value="${sessionScope["confirm-password"]}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['confirm-password-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['confirm-password-error']}"/>
                <c:remove var="confirm-password-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.user.email"/></label>
            <input type="text" name="email" value="${sessionScope['email']}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['email-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['email-error']}"/>
                <c:remove var="email-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.user.table-id"/></label>
            <input type="text" name="table-id" value="${sessionScope["table-id"]}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['table-id-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['table-id-error']}"/>
                <c:remove var="table-id-error" scope="session"/>
            </c:if>
        </p>

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
                <fmt:message bundle="${msg}" key="${sessionScope['role-error']}"/>
                <c:remove var="role-error" scope="session"/>
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
