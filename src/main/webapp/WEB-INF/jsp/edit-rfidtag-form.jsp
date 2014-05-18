<%@taglib prefix="ftag" tagdir="/WEB-INF/tags/fields" %>

<form action="do/edit-rfidtag" method="post">
    <fieldset class="edit-rfidtag">
        <input type="hidden" name="id" value="${sessionScope['original-rfidtag'].id}">

        <p> &nbsp;</p>
        <ftag:string-field field="uid"
                           value="${sessionScope['original-rfidtag'].uid}"
                           clazz="rfidtag"/>
        <p> &nbsp;</p>
        <ftag:select-field clazz="rfidtag"
                           field="type"
                           optionList="${sessionScope['types']}"
                           value="${sessionScope['original-rfidtag'].type}"/>
        <p> &nbsp;</p>
        <ftag:select-field clazz="rfidtag"
                           field="protocol"
                           optionList="${sessionScope['protocols']}"
                           value="${sessionScope['original-rfidtag'].protocol}"/>
        <p> &nbsp;</p>
        <ftag:string-field field="issue-date"
                           value="${sessionScope['original-rfidtag'].issue.issueDate.date}"
                           clazz="rfidtag"/>
        <p> &nbsp;</p>
        <ftag:string-field field="expiration-date"
                           value="${sessionScope['original-rfidtag'].issue.expirationDate.date}"
                           clazz="rfidtag"/>
        <p> &nbsp;</p>
        <p>
            <button type="submit">
                <fmt:message bundle="${msg}" key="form.rfidtag.submit"/>
            </button>
        </p>
    </fieldset>
</form>