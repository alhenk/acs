<h2><fmt:message bundle="${msg}" key="edit.account"/> </h2>
<form action="do/edit-account" method="post">
    <input type="hidden" name="id" value="${sessionScope.account.id}">
    <table>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.user.name"/>:</td>
            <td align="left"><input type="text" name="username" value="${sessionScope.account.username}"></td>
            <td align="left" style="color:red;">
                <c:if test="${not empty sessionScope['username-error']}">
                    <fmt:message bundle="${msg}" key="${sessionScope['username-error']}"/>
                    <c:remove var="username-error" scope="session"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.user.email"/> :</td>
            <td align="left"><input type="text" name="email" value="${sessionScope.account.email}" ></td>
            <td align="left" style="color:red;">
                <c:if test="${not empty sessionScope['email-error']}">
                    <fmt:message bundle="${msg}" key="${sessionScope['email-error']}"/>
                    <c:remove var="email-error" scope="session"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.user.table-id"/> :</td>
            <td align="left"><input type="text" name="table-id" value="${sessionScope.account.account1C.tableId}"></td>
            <td align="left" style="color:red;">
                <c:if test="${not empty sessionScope['table-id-error']}">
                    <fmt:message bundle="${msg}" key="${sessionScope['table-id-error']}"/>
                    <c:remove var="table-id-error" scope="session"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.user.role"/> :</td>
            <td align="left">
                <select id="role" name="role">
                    <option value="employee" ${sessionScope.account.role == 'EMPLOYEE' ? 'selected' : ''} > Employee</option>
                    <option value="supervisor" ${sessionScope.account.role == 'SUPERVISOR' ? 'selected' : ''} > Supervisor</option>
                    <option value="administrator" ${sessionScope.account.role == 'ADMINISTRATOR' ? 'selected' : ''} >Administrator</option>
                </select>
                <p>${sessionScope.account.role}</p>
            </td>
            <td align="left" style="color:red;">
                <c:if test="${not empty sessionScope['role-error']}">
                    <fmt:message bundle="${msg}" key="${sessionScope['role-error']}"/>
                    <c:remove var="role-error" scope="session"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td align="right">
                <button type="submit">
                    <fmt:message bundle="${msg}" key="form.edit-account.submit"/>
                </button>
            </td>
        </tr>
    </table>
</form>
