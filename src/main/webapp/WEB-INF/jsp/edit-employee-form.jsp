<form action="do/edit-employee" method="post">
    <fieldset class="edit-employee">
        <p>
            <label><fmt:message bundle="${msg}" key="form.employee.first-name"/></label>
            <input type="text" name="first-name" value="${sessionScope["first-name"]}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['first-name-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['first-name-error']}"/>
                <c:remove var="first-name-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.employee.patronym"/></label>
            <input type="text" name="patronym" value="${sessionScope["patronym"]}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['patronym-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['patronym-error']}"/>
                <c:remove var="patronym-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.employee.last-name"/></label>
            <input type="text" name="last-name" value="${sessionScope["last-name"]}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['last-name-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['last-name-error']}"/>
                <c:remove var="last-name-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.employee.birth-date"/></label>
            <input type="text" name="birth-date" value="${sessionScope["birth-date"]}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['birth-date-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['birth-date-error']}"/>
                <c:remove var="birth-date-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.employee.position"/></label>
            <select id="position" name="position">
                <option value="GENERAL_DIRECTOR" selected><fmt:message bundle="${msg}" key="structure.position.GENERAL_DIRECTOR"/></option>
                <option value="COMMERCIAL_DIRECTOR"><fmt:message bundle="${msg}" key="structure.position.COMMERCIAL_DIRECTOR"/></option>
                <option value="DEPARTMENT_HEAD"><fmt:message bundle="${msg}" key="structure.position.DEPARTMENT_HEAD"/></option>
            </select>
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['position-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['position-error']}"/>
                <c:remove var="position-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.employee.department"/></label>
            <select id="department" name="department">
                <option value="RESEARCH_AND_DEVELOPMENT" selected><fmt:message bundle="${msg}" key="structure.department.RESEARCH_AND_DEVELOPMENT"/></option>
                <option value="ACCOUNTANCY"><fmt:message bundle="${msg}" key="structure.department.ACCOUNTANCY"/></option>
                <option value="COMMERCIAL"><fmt:message bundle="${msg}" key="structure.department.COMMERCIAL"/></option>
                <option value="DEFAULT"><fmt:message bundle="${msg}" key="structure.department.DEFAULT"/></option>
            </select>
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['department-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['department-error']}"/>
                <c:remove var="department-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.employee.room"/></label>
            <select id="room" name="room">
                <option value="101" selected>101</option>
                <option value="102">102</option>
                <option value="103">103</option>
            </select>
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['room-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['room-error']}"/>
                <c:remove var="room-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

        <p>
            <label><fmt:message bundle="${msg}" key="form.employee.table-id"/></label>
            <input type="text" name="table-id" value="${sessionScope["table-id"]}">
        </p>

        <p style="color:red;">
            <c:if test="${not empty sessionScope['table-id-error']}">
                <fmt:message bundle="${msg}" key="${sessionScope['table-id-error']}"/>
                <c:remove var="table-id-error" scope="session"/>
            </c:if>
        </p>

        <p> &nbsp;</p>

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
            <button type="submit">
                <fmt:message bundle="${msg}" key="form.employee.submit"/>
            </button>
        </p>

    </fieldset>
</form>
