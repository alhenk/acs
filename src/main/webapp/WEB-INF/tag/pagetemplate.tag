<%@tag language="java" pageEncoding="UTF-8"%>
<%@attribute name="body" fragment="true" %>
<%@attribute name="title" required="true" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
    <title>${title}</title>
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