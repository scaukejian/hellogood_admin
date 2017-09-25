<%--
  Created by IntelliJ IDEA.
  User: KJ
  Date: 2017/9/19
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<style rel="stylesheet" type="text/css">
    body {
        text-align: center;
        margin: 100px auto;
    }
    img {
        margin-top: 50px;
        width: 400px;
    }
</style>
</head>
<body>
<h2>对不起，您没有权限访问该资源路径，请联系管理员授权。</h2>
<img src="../images/noAuth.png" onclick="location.href='<%=request.getContextPath()%>/pages/login.jsp';"/>
</body>
</html>
