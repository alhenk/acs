<%@tag language="java" pageEncoding="UTF-8"%>
<%@attribute name="body" fragment="true" %>
<%@attribute name="title" required="true" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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
    <hr>
    <h2>body</h2>
    <jsp:doBody/>
    <hr>
    <h2>footer</h2>
</body>
</html>