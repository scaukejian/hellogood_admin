<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="../js/common/jquery-1.11.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

</script>
</head>
<body>
 	 <h1>xss测试</h1>
 	 <%
        String co = request.getParameter("co");
        String type = request.getParameter("type");
     %>
    <h2>cookie :<%=co%></h2>
     <h2>type : <%=type%></h2>
</body>
</html>