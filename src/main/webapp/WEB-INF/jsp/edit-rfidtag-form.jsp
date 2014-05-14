<form action="do/edit-rfidtag" method="post">
    <fieldset class="edit-rfidtag">
        <p>
            <label>UID</label>
            <input type="text" name="uid" value="${sessionScope["uid"]}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['uid-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['uid-error']}"/>
                <c:remove var="uid-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.rfidtag.type"/></label>
            <input type="text" name="type" value="${sessionScope["type"]}">
        </p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.rfidtag.type"/></label>
            <select id="type" name="type">
                <option value="CARD" selected>CARD</option>
                <option value="KEYFOB">KEYFOB</option>
                <option value="STICKER">STICKER</option>
            </select>
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['type-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['type-error']}"/>
                <c:remove var="type-error" scope="session"/>
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
            <c:if test="${not empty sessionScope['protocol-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['protocol-error']}"/>
                <c:remove var="protocol-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.rfidtag.issue-date"/></label>
            <input type="text" name="issue-date" value="${sessionScope['issue-date']}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['issue-date-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['issue-date-error']}"/>
                <c:remove var="issue-date-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.rfidtag.expiration-date"/></label>
            <input type="text" name="expiration-date" value="${sessionScope["expiration-date"]}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['expiration-date-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['expiration-date-error']}"/>
                <c:remove var="expiration-date-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>


        <p> &nbsp;</p>

        <p>
            <button type="submit">
                <fmt:message bundle="${msg}" key="form.rfidtag.submit"/>
            </button>
        </p>

    </fieldset>
</form>