<%@taglib prefix="ftag" tagdir="/WEB-INF/tags/fields" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

        <p>
            <label><fmt:message bundle="${msg}" key="form.employee.position"/></label>
            <select id="position" name="position">
                <option value="GENERAL_DIRECTOR" selected><fmt:message bundle="${msg}"
                                                                       key="structure.position.GENERAL_DIRECTOR"/></option>
                <option value="COMMERCIAL_DIRECTOR"><fmt:message bundle="${msg}"
                                                                 key="structure.position.COMMERCIAL_DIRECTOR"/></option>
                <option value="DEPARTMENT_HEAD"><fmt:message bundle="${msg}"
                                                             key="structure.position.DEPARTMENT_HEAD"/></option>
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
                <option value="RESEARCH_AND_DEVELOPMENT" selected><fmt:message bundle="${msg}"
                                                                               key="structure.department.RESEARCH_AND_DEVELOPMENT"/></option>
                <option value="ACCOUNTANCY"><fmt:message bundle="${msg}"
                                                         key="structure.department.ACCOUNTANCY"/></option>
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