<%@tag language="java" pageEncoding="UTF-8"%>
<%@attribute name="body" fragment="true" %>
<%@attribute name="title" required="true" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="mtag" tagdir="/WEB-INF/tags" %>

<c:set var="locale" value="${not empty param.locale ? param.locale :
                             not empty locale ? locale : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages" var="msg" scope="session"/>


<html>
<head>
    <title>${title}</title>
    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <c:set var="uri">${pageContext.request.requestURI}</c:set>
    <c:set var="ctx">${pageContext.request.contextPath}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${ctx}/"/>
</head>
<body>
    <h1>${fn:toUpperCase(title)}</h1>
    <h2>header</h2>
    <p>Locale = ${locale}</p>
    <hr>
    <h2>body</h2>
    <jsp:doBody/>
    <hr>
    <h2>footer</h2>
    <mtag:footer/>
</body>
</html>