<%@tag language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="title" required="true" %>

<div class="grid_16">
    <h1 id="branding">
        <a><fmt:message bundle="${msg}" key="common.acs"/></a>
    </h1>
</div>

<div class="grid_16">
    <ul class="nav main">
        <li>
            <a href="do/main"><fmt:message bundle="${msg}" key="common.main"/></a>
        </li>
        <c:if test="${not empty sessionScope['user']}">
            <li>
                <a href="do/dashboard"><fmt:message bundle="${msg}" key="common.dashboard"/></a>
                <c:if test="${sessionScope['user'].role == 'ADMINISTRATOR'}">
                    <ul>
                        <li>
                            <a href="do/user-list"><fmt:message bundle="${msg}" key="common.user-list"/></a>
                        </li>
                        <li>
                            <a href="do/rfidtag-list"><fmt:message bundle="${msg}" key="common.rfidtag-list"/></a>
                        </li>
                        <li>
                            <a href="do/employee-list"><fmt:message bundle="${msg}" key="common.employee-list"/></a>
                        </li>
                    </ul>
                </c:if>
            </li>
        </c:if>

        <li class="secondary">
            <form action="do/set-language" method="post">
                <select id="language" name="language" onchange="submit()">
                    <option value="en" ${locale.language == 'en' ? 'selected' : ''}>English</option>
                    <option value="ru" ${locale.language == 'ru' ? 'selected' : ''}>
                        &#x420;&#x443;&#x441;&#x441;&#x43a;&#x438;&#x439;</option>
                </select>
            </form>
        </li>
        <c:if test="${not empty sessionScope.user}">
            <li class="secondary">
                <a href="do/sign-out"><fmt:message bundle="${msg}" key="common.sign-out"/></a>
            </li>
        </c:if>
        <li class="secondary">
            <c:choose>
                <c:when test="${empty sessionScope.user}">
                    <a><fmt:message bundle='${msg}' key='common.unauthorized'/></a>
                </c:when>
                <c:otherwise>
                    <a>
                        <fmt:message bundle='${msg}' key='user.role.${sessionScope.user.role}'/>
                            ${sessionScope.user.username}
                    </a>
                </c:otherwise>
            </c:choose>
        </li>
    </ul>
</div>

<div class="clear"></div>
<div class="grid_16">&nbsp;</div>
<div class="grid_6">
    <h3 id="page-heading"><fmt:message bundle='${msg}' key='common.${title}'/><a href=""></a></h3>
</div>
<div class="grid_8">
    <c:if test="${not empty param.status}">
    <h2 id="status"><fmt:message bundle='${msg}' key='${param.status}'/><a href=""></a></h2>
    </c:if>
</div>
<div class="clear"></div>
<div class="grid_16">&nbsp;</div>
<div class="clear"></div>

