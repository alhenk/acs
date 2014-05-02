<h2><fmt:message bundle="${msg}" key="admin.create.account"/> </h2>
<form action="do/sign-up" method="post">
    <table>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.username"/>:</td>
            <td align="left"><input type="text" name="username" value="${sessionScope["username"]}"></td>
            <td align="left" style="color:red;">
                <c:if test="${not empty sessionScope['username-error']}">
                    <fmt:message bundle="${msg}" key="${sessionScope['username-error']}"/>
                    <c:remove var="username-error" scope="session"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.password"/> :</td>
            <td align="left"><input type="password" name="password" value="${sessionScope["password"]}" ></td>
            <td align="left" style="color:red;">
                <c:if test="${not empty sessionScope['password-error']}">
                    <fmt:message bundle="${msg}" key="${sessionScope['password-error']}"/>
                    <c:remove var="password-error" scope="session"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.confirm-password"/> :</td>
            <td align="left"><input type="password" name="confirm-password" value="${sessionScope["confirm-password"]}" ></td>
            <td align="left" style="color:red;">
                <c:if test="${not empty sessionScope['confirm-password-error']}">
                    <fmt:message bundle="${msg}" key="${sessionScope['confirm-password-error']}"/>
                    <c:remove var="confirm-password-error" scope="session"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.table-id"/> :</td>
            <td align="left"><input type="text" name="table-id" value="${sessionScope["table-id"]}"></td>
            <td align="left" style="color:red;">
                <c:if test="${not empty sessionScope['table-id-error']}">
                    <fmt:message bundle="${msg}" key="${sessionScope['table-id-error']}"/>
                    <c:remove var="table-id-error" scope="session"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.user-role"/> :</td>
            <td align="left">
            <select id="user-role" name="user-role">
                <option value="employee" selected>Employee</option>
                <option value="supervisor" >Supervisor</option>
                <option value="administrator" >Administrator</option>
             </select>
            </td>
            <td align="left" style="color:red;">
                <c:if test="${not empty sessionScope['user-role-error']}">
                    <fmt:message bundle="${msg}" key="${sessionScope['user-role-error']}"/>
                    <c:remove var="user-role-error" scope="session"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td align="right">
                <button type="submit">
                    <fmt:message bundle="${msg}" key="form.submit"/>
                </button>
            </td>
        </tr>
    </table>
</form>
