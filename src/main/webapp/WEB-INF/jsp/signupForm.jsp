<h2><fmt:message bundle="${msg}" key="admin.create.account"/> </h2>
<form action="do/sign-up" method="post">
    <table>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.username"/>:</td>
            <td align="left"><input type="text" name="username" value="${sessionScope["username"]}"></td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.password"/> :</td>
            <td align="left"><input type="password" name="password" ></td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.table-id"/> :</td>
            <td align="left"><input type="text" name="table-id" value="${sessionScope["table-id"]}"></td>
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
