<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag language="java" pageEncoding="UTF-8" %>

<a href="do/main"><fmt:message bundle="${msg}" key="common.home"/> </a>
<br/>
<br/>
<form action="do/set-language" method="post">
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${locale.language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${locale.language == 'ru' ? 'selected' : ''}>
            &#x420;&#x443;&#x441;&#x441;&#x43a;&#x438;&#x439;</option>
    </select>
</form>
