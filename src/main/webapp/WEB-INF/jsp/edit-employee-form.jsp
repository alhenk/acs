<%@taglib prefix="ftag" tagdir="/WEB-INF/tags/fields" %>

<form action="do/edit-employee" method="post">
    <fieldset class="edit-employee">
        <input type="hidden" name="id" value="${sessionScope['original-employee'].id}">
        <p>&nbsp;</p>
        <ftag:string-field field="first-name"
                           value="${sessionScope['original-employee'].firstName}"
                           clazz="employee"/>
        <p> &nbsp;</p>
        <ftag:string-field field="patronym"
                           value="${sessionScope['original-employee'].patronym}"
                           clazz="employee"/>
        <p> &nbsp;</p>
        <ftag:string-field field="last-name"
                           value="${sessionScope['original-employee'].lastName}"
                           clazz="employee"/>
        <p> &nbsp;</p>
        <ftag:string-field field="birth-date"
                           value="${sessionScope['original-employee'].birthDate.date}"
                           clazz="employee"/>
        <p> &nbsp;</p>
        <ftag:select-field clazz="employee"
                           field="position"
                           optionList="${sessionScope['positions']}"
                           value="${sessionScope['original-employee'].position}"/>
        <p> &nbsp;</p>
        <ftag:select-field clazz="employee"
                           field="department"
                           optionList="${sessionScope['departments']}"
                           value="${sessionScope['original-employee'].department}"/>
        <p> &nbsp;</p>
        <ftag:select-field clazz="employee"
                           field="room"
                           optionList="${sessionScope['rooms']}"
                           value="${sessionScope['original-employee'].room}"/>
        <p> &nbsp;</p>
        <ftag:string-field field="table-id"
                           value="${sessionScope['original-employee'].account1C.tableId}"
                           clazz="employee"/>
        <p> &nbsp;</p>
        <ftag:string-field field="uid"
                           value="${sessionScope['original-employee'].rfidTag.uid}"
                           clazz="employee"/>
        <p> &nbsp;</p>
        <p>
            <button type="submit">
                <fmt:message bundle="${msg}" key="form.employee.submit"/>
            </button>
        </p>
    </fieldset>
</form>
