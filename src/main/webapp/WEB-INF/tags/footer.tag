<%@tag language="java" pageEncoding="UTF-8" %>

<form action="do/set-language" method="post">
    <select id="locale" name="local" onchange="submit()">
        <option value="en" ${locale.language =="en" ? selected : ""}>English</option>
        <option value="ru" ${locale.language =="ru" ? selected : ""}>
            &#x420;&#x443;&#x441;&#x441;&#x43a;&#x438;&#x439;</option>
    </select>
</form>
