<%@ page import="com.hellogood.utils.RSAUtil" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<!doctype html>
<%
	String path = request.getContextPath();
	String account = null;
	String password = null;
	boolean rmbUser = false;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if ("emp".equals(cookie.getName())) {
				String empStr = RSAUtil.decrypt(cookie.getValue());
				String[] empArr = empStr.split(",");
				if (empArr != null && empArr.length == 3) {
				   account = empArr[0];
				   password = empArr[1];
				   rmbUser = true;
				}
			}
		}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台登陆</title>
<script type="text/javascript" src="../js/common/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="../js/login/login.js?${ts}"></script>
<script type="text/javascript" src="../js/common/jquery.prompt.js"></script>
<script type="text/javascript" src="../js/login/jquery.cookie.js"></script>
<link href="../css/Series.css" rel="stylesheet" type="text/css">
<style>
body, input {
	padding: 0;
	margin: 0;
	font-family: "微软雅黑";
}

img {
	display: block;
	margin: 0;
	padding: 0;
}

header {
	height: 190px;
	text-align: center;
}

header a {
	color: #2b2b2b;
	font-size: 60px;
	font-family: '微软雅黑';
	font-weight: bold;

	line-height: 190px;
	letter-spacing: 10px;
}

.sign_in_box {
	background-image: url(../images/login/banner.jpg);
	height: 526px;
	width: 100%;
	position: relative;
}

.box {
	position: absolute;
	right: 20%;
}

.text_box {
	float: left;
	margin: 100px 150px;
}

.text_box img {
	width: 110%;
}

.sign_in {
	width: 520px;
	height: 472px;
	background-image: url(../images/login/sign_bg.png);
	float: right;
	position: relative;
	margin-top: 26px;
}

.sign_in div {
	width: 400px;
	margin: auto;
}

.sign_in .sign_in_content {
	width: 400px;
	position: absolute;
	left: 09%;
}

.sign_in_content * {
	float: left;
}

input {
	color: #a5a5a5;
	height: 60px;
	background-color: transparent;
	border-radius: 8px;
	border: 1px #2b2b2b solid;
	width: 400px;
	font-size: 26px;
	outline: none;
	text-align: center;
}

.little_ipt {
	width: 160px;
}

.remove_btn {
	cursor: pointer;
	background-image: url(../images/login/remove_btn.png);
	width: 20px;
	height: 20px;
	outline: none;
	-webkit-appearance: none;
	border-radius: 0;
	border: none;
}

.sign_in_content div {
	margin: 13px;
}

.sign_in_content input[type="password"] {
	letter-spacing: 10px;
	text-align: center;
}

@media ( max-width :1440px) {
	.sign_in {
		margin-right: 60px;
	}
	.text_box {
		margin: 100px 80px;
	}
	header a {
		margin-left: 330px;
	}
	.box {
		position: absolute;
		right: 16%;
	}
}

@media ( max-width :1366px) {
	.sign_in {
		margin-right: 30px;
	}
}

@media ( max-width :1280px) {
	.box {
		position: absolute;
		right: 6%;
	}
	header a {
		margin-left: 200px;
	}
}

@media ( max-width :1024px) {
	.box {
		position: absolute;
		right: 6%;
	}
	header a {
		margin-left: 100px;
	}
	.text_box {
		margin: 160px 60px;
	}
	.text_box img {
		width: 70%;
	}
}
</style>
</head>

<body style="background-color: #ededed;">
	<header><a>Hello Good后台</a></header>
	<div class="sign_in_box">
		<div class="text_box">
			<img src="../images/login/text.png">
		</div>
		<div class="box">
			<div class="sign_in">
				<div class="sign_in_content">
					<form id="admin_login_form" method="post" >
					<div>
						<input style="margin-top: 40px;" type="text" value="<%=account != null ? account : "账号"%>" name="account" id="account"
							onfocus='input_focus(this)' onblur='input_blur(this)' />
					</div>
					<div>
						<input class="mima_ipt" type="<%=password != null ? "password" : "text"%>" value="<%=password != null ? password : "密码"%>" name="password" id="password" >
					</div>
					<div class="yzm_box">
						<input class="little_ipt" type="text" value="验证码" name="code" id="code"
							onfocus='yzm_input_focus(this)' onblur='yzm_input_blur(this)'>
						<img style="float: right; cursor: pointer; border-radius: 8px; height: 60px;" id="changeImgObj" 
							src="${pageContext.request.contextPath}/verifyCode/get.do">
					</div>
					<div>
						<input class="remove_btn" type="checkbox" name="rmbUser" id="rmbUser" style="margin-right: 10px;<%= rmbUser ? "background-image:url(../images/login/remove_btn_in.png)" : "background-image:url(../images/login/remove_btn.png)" %>" value="true" <%= rmbUser ? "checked" : "" %>  >记住密码
					</div>
					<div class="sign_in_btn"
						id="doSubmit"
						style="cursor: pointer; background-image: url(../images/login/sign_btn.png); margin-top:45px;width: 400px; height: 60px; font-size: 28px; line-height: 60px; text-align: center; font-family: '微软雅黑'; color: #e5e5e5;">登录
					</div>
					</form>
				</div>

			</div>
		</div>
	</div>

</body>
</html>
