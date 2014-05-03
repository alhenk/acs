<h2><fmt:message bundle="${msg}" key="form.sign-in.please-login"/> </h2>
<form action="do/sign-in" method="post">
    <table>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.sign-in.name"/>:</td>
            <td align="left"><input type="text" name="username"></td>
        </tr>
        <tr>
            <td align="right"><fmt:message bundle="${msg}" key="form.sign-in.password"/> :</td>
            <td align="left"><input type="password" name="password"></td>
        </tr>
        <tr>
            <td align="right">
                <button type="submit">
                    <fmt:message bundle="${msg}" key="form.sign-in.submit"/>
                </button>
            </td>
        </tr>
    </table>
</form>