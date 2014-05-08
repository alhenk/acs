<h3><fmt:message bundle="${msg}" key="form.sign-in.please-login"/></h3>

<p> &nbsp;</p>

<form action="do/sign-in" method="post">
    <fieldset class="login">
        <p>
            <label><fmt:message bundle="${msg}"
                                key="form.sign-in.name"/></label>
            <input type="text" name="username">
        </p>

        <p>
            <label><fmt:message bundle="${msg}"
                                key="form.sign-in.password"/></label>
            <input type="password" name="password">
        </p>
        <input class="login button" type="submit"
               value="<fmt:message bundle="${msg}" key="form.sign-in.submit"/>">
        </input>
    </fieldset>
</form>
