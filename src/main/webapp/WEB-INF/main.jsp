<%--@elvariable id="rootFile" type="java.io.File"--%>
<%--@elvariable id="parentPath" type="java.lang.String"--%>
<%--@elvariable id="files" type="java.io.File[]"--%>
<%--@elvariable id="attrs" type="java.nio.file.attribute.BasicFileAttributes"--%>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <title>File Explorer</title>
</head>
<header>
    <%=new Date()%>
    <a href="/login?logout=true">Logout</a>
</header>
<body>
<h1>${rootFile.absolutePath}</h1>
<hr/>
<div>
    <a href="${parentPath}">Вверх</a>
    <table>
        <tr><th>Файл</th><th>Размер</th><th>Дата</th></tr>
        <c:forEach var="file" items="${files}">
            <tr>
                <td><a href="?path=${file.getAbsolutePath()}">${file.name}</a></td>
                <td>${file.length()}</td>
                <td>${attrs.creationTime()}</td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
