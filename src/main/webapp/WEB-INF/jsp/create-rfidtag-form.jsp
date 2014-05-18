<%@taglib prefix="ftag" tagdir="/WEB-INF/tags/fields" %>

<form action="do/create-rfidtag" method="post">
    <fieldset class="create-rfidtag">
        <p> &nbsp;</p>
        <ftag:string-field field="uid"
                           value="${sessionScope['uid']}"
                           clazz="rfidtag"/>
        <p> &nbsp;</p>
        <ftag:select-field clazz="rfidtag"
                           field="type"
                           optionList="${sessionScope['types']}"
                           value="${sessionScope['type']}"/>
        <p> &nbsp;</p>
        <ftag:select-field clazz="rfidtag"
                           field="protocol"
                           optionList="${sessionScope['protocols']}"
                           value="${sessionScope['protocol']}"/>
        <p> &nbsp;</p>
        <ftag:string-field field="issue-date"
                           value="${sessionScope['issue-date']}"
                           clazz="rfidtag"/>
        <p> &nbsp;</p>
        <ftag:string-field field="expiration-date"
                           value="${sessionScope['expiration-date']}"
                           clazz="rfidtag"/>
        <p> &nbsp;</p>

        <p>
            <button type="submit">
                <fmt:message bundle="${msg}" key="form.rfidtag.submit"/>
            </button>
        </p>
    </fieldset>
</form>