<h2><fmt:message bundle="${msg}" key="admin.create.user"/> </h2>
<form action="do/signup" method="post">
    <table>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.username"/>:</td>
            <td align="left"><input type="text" name="username"></td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.password"/> :</td>
            <td align="left"><input type="password" name="password"></td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.table-id"/> :</td>
            <td align="left"><input type="password" name="password"></td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.user-role"/> :</td>
            <td align="left"><input type="password" name="password"></td>
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
