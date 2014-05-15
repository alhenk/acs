<%@taglib prefix="ftag" tagdir="/WEB-INF/tags/fields" %>

<form action="do/edit-rfidtag" method="post">
    <fieldset class="edit-rfidtag">
        <p> &nbsp;</p>
        <ftag:string-field field="uid"
                           value="${sessionScope['uid']}"
                           clazz="rfidtag"/>
        <p> &nbsp;</p>
        <ftag:rfidtag-type-field value="${sessionScope['type']}"/>
        <p> &nbsp;</p>
        <ftag:rfidtag-protocol-field value="${sessionScope['protocol']}"/>
        <p> &nbsp;</p>
        <ftag:string-field field="issue-date"
                           value="${sessionScope['issue-date']}"
                           clazz="rfidtag"/>
        <p> &nbsp;</p>
        <ftag:string-field field="expiration-date"
                           value="${sessionScope['issue-date']}"
                           clazz="rfidtag"/>
        <p> &nbsp;</p>

        <p>
            <button type="submit">
                <fmt:message bundle="${msg}" key="form.rfidtag.submit"/>
            </button>
        </p>
    </fieldset>
</form>