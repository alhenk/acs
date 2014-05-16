<%@taglib prefix="ftag" tagdir="/WEB-INF/tags/fields" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form action="do/edit-user" method="post">
    <fieldset class="edit">
        <input type="hidden" name="id" value="${sessionScope['original-user'].id}">

        <p>&nbsp;</p>
        <ftag:string-field field="username"
                           value="${sessionScope['original-user']['username']}"
                           clazz="user"/>
        <p>&nbsp;</p>
        <ftag:string-field field="email"
                           value="${sessionScope['original-user'].email}"
                           clazz="user"/>
        <p>&nbsp;</p>
        <ftag:string-field field="table-id"
                           value="${sessionScope['original-user'].account1C.tableId}"
                           clazz="user"/>
        <p>&nbsp;</p>
        <ftag:select-field clazz="user"
                           field="role"
                           optionList="${sessionScope.roles}"
                           value="${sessionScope['original-user'].role}"/>
        <p>&nbsp;</p>

        <p>
            <button type="submit">
                <fmt:message bundle="${msg}" key="form.edit-user.submit"/>
            </button>
        </p>
        <p style="color:red;">
            <c:if test="${not empty param.status}">
                <fmt:message bundle="${msg}" key="${param.status}"/>
            </c:if>
        </p>
    </fieldset>
</form>
