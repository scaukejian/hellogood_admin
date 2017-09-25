<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/21
  Time: 12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>基础数据</title>
</head>
<body>
<h2>新增基础信息</h2>
<form action="/hellogood_admin/baseData/add.do" method="post">
    type : <input type="text" name="type"></br>
    code : <input type="text" name="code"></br>
    name : <input type="text" name="name">
    <input type="submit" value="提交">
</form>
</body>
</html>
