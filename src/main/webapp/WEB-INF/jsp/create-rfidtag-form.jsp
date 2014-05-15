<%@taglib prefix="ftag" tagdir="/WEB-INF/tags/fields" %>

<form action="do/create-rfidtag" method="post">
    <fieldset class="create-rfidtag">
        <p> &nbsp;</p>
        <ftag:string-field field="uid"
                           value="RFID UID"
                           clazz="rfidtag"/>
        <p> &nbsp;</p>
        <ftag:rfidtag-type-field value="CARD"/>
        <p> &nbsp;</p>
        <ftag:rfidtag-protocol-field value="ISO15693"/>
        <p> &nbsp;</p>
        <ftag:string-field field="issue-date"
                           value="YYYY-MM-DD"
                           clazz="rfidtag"/>
        <p> &nbsp;</p>
        <ftag:string-field field="expiration-date"
                           value="YYYY-MM-DD"
                           clazz="rfidtag"/>
        <p> &nbsp;</p>

        <p>
            <button type="submit">
                <fmt:message bundle="${msg}" key="form.rfidtag.submit"/>
            </button>
        </p>
    </fieldset>
</form>