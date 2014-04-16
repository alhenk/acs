<h2><fmt:message bundle="${msg}" key="login.please-login"/> </h2>
<form action="do/signin" method="post">
    <table>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="login.username"/>:</td>
            <td align="left"><input type="text" name="username"></td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="login.password"/> :</td>
            <td align="left"><input type="password" name="password"></td>
        </tr>
        <tr>
            <td align="right">
                <button type="submit">
                    <fmt:message bundle="${msg}" key="login.submit"/>
                </button>
            </td>
        </tr>
    </table>
</form>