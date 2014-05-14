<%@taglib prefix="ftag" tagdir="/WEB-INF/tags/fields" %>
<form action="do/edit-rfidtag" method="post">
    <fieldset class="edit-rfidtag">
        <p> &nbsp;</p>
        <ftag:string-field field="uid"
                           value="${sessionScope['uid']}"
                           clazz="rfidtag"/>
        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.rfidtag.type"/></label>
            <select id="type" name="type">
                <option value="CARD" selected>CARD</option>
                <option value="KEYFOB">KEYFOB</option>
                <option value="STICKER">STICKER</option>
            </select>
        </p>
        <p style="color:red;">
            <c:if test="${not empty param['type-error']}">
                <fmt:message bundle="${msg}" key="${param['type-error']}"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.rfidtag.protocol"/></label>
            <select id="protocol" name="protocol">
                <option value="ISO14443A" selected>ISO14443A</option>
                <option value="ISO15693">ISO15693</option>
            </select>
        </p>
        <p style="color:red;">
            <c:if test="${not empty param['protocol-error']}">
                <fmt:message bundle="${msg}" key="${param['protocol-error']}"/>
            </c:if>
        </p>

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