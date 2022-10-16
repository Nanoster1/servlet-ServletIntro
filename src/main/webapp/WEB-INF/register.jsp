<%--
  Created by IntelliJ IDEA.
  User: Daniil
  Date: 16.10.2022
  Time: 23:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>helo2</title>
</head>
<body>
<form method="post">
  <p><input name="email" style="width: 300px" required type="email" placeholder="Email"></p>
  <p><input name="login" style="width: 300px" required type="text" placeholder="Login"></p>
  <p><input name="password" style="width: 300px" required type="password" placeholder="Password" minlength="4"></p>
  <button style="width: 100px" type="submit">
    Создать
  </button>
</form>
</body>
</html>
