<%@taglib prefix="ftag" tagdir="/WEB-INF/tags/fields" %>

<form action="do/create-employee" method="post">
    <fieldset class="create-employee">
        <ftag:string-field field="first-name"
                           value="${sessionScope['first-name']}"
                           clazz="employee"/>
        <p> &nbsp;</p>
        <ftag:string-field field="patronym"
                           value="${sessionScope['patronym']}"
                           clazz="employee"/>
        <p> &nbsp;</p>
        <ftag:string-field field="last-name"
                           value="${sessionScope['last-name']}"
                           clazz="employee"/>
        <p> &nbsp;</p>
        <ftag:string-field field="birth-date"
                           value="${sessionScope['birth-date']}"
                           clazz="employee"/>
        <p> &nbsp;</p>
        <ftag:select-field field="position"
                                 optionList="${sessionScope['positions']}"
                                 value="${sessionScope['position']}"/>
        <p> &nbsp;</p>
        <ftag:select-field field="department"
                           optionList="${sessionScope['departments']}"
                           value="${sessionScope['department']}"/>
        <p> &nbsp;</p>
        <ftag:select-field field="room"
                           optionList="${sessionScope['rooms']}"
                           value="${sessionScope['room']}"/>
        <p> &nbsp;</p>
        <ftag:string-field field="table-id"
                           value="${sessionScope['table-id']}"
                           clazz="employee"/>
        <p> &nbsp;</p>
        <ftag:string-field field="uid"
                           value="${sessionScope['uid']}"
                           clazz="employee"/>
        <p> &nbsp;</p>
        <p>
            <button type="submit">
                <fmt:message bundle="${msg}" key="form.employee.submit"/>
            </button>
        </p>
    </fieldset>
</form>