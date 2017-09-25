<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
<link rel="shortcut icon" href="../../images/register/favicon.ico" type="image/x-icon">
<link rel="Bookmark" href="../../images/register/favicon.ico" type="image/x-icon">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
<title>易约，您的专属约会管家</title>
<style>
body {
	margin: 0;
	padding: 0;
}

img {
	display: block;
	margin: 0;
	padding: 0;
}

body {
	background-image: url(../../images/ruici/bg.jpg);
	background-color: #2b2b2b;
}

.logo {
	width: 75%;
	margin: auto;
	margin-top: 7%;
	margin-bottom: 10%;
}

.logo img {
	width: 100%;
}

.ip_box {
	width: 82%;
	margin: auto;
	margin-bottom: 2%;
}

input {
	background-color: #FFF;
	opacity: 0.6;
	border-radius: 5px;
	outline: none;
	border: 0;
	font-size: 14px;
	color: #2b2b2b;
}

.baliu {
	width: 26%;
	background-image: url(../../images/ruici/baliu.png);
	background-repeat: no-repeat;
	float: left;
}

.phone {
	padding: 0 5%;
	width: 62%;
	float: right;
}

.mima {
	width: 90%;
	padding: 0 5%;
}

.yzm_box input {
	float: right;
	width: 62%;
	padding: 0 5%;
}

.yzmhq {
	background-image: url(../../images/ruici/yzm.png);
	background-repeat: no-repeat;
	width: 26%;
	font-size: 12px;
	color: #ededed;
	float: left;
	text-align: center;
}

.zc_box {
	letter-spacing: 4px;
	margin: auto;
	width: 82%;
	border: #767575 1px solid;
	border-radius: 5px;
	background-color: transparent;
	color: #a9a9a9;
	font-size: 16px;
	text-align: center;
	margin-top: 8%;
}

.time_text {
	width: 82%;
	margin: auto;
	margin-top: 3%;
}

.time_text img {
	width: 100%;
}

.bottom_logo {
	width: 32%;
	margin: auto;
	margin-top: 16%;
}

.bottom_logo img {
	width: 100%;
}

.sex{
	width: 26%;
	background-color:#FFF;	
	background-repeat: no-repeat;
	float: left;
}

</style>
</head>

<body>
	<div class="logo">
		<img src="../../images/register/logo.png">
	</div>

	<div class="ip_box">
		<div class="baliu"></div>
		<input class="phone" id='phone' maxlength="11" value=""
			placeholder="手机号码">
	</div>

	<div class="ip_box">
		<input class="mima" value="" type="password" id="password"
			placeholder="密码">
	</div>
	<div class="ip_box">
			<select id="sex" name="sex" style="height:90%; width:100% ; background-color: #FFF;opacity: 0.6;border-radius: 5px;outline: none; border: 0; font-size: 14px; color: #2b2b2b;">
				<option value="">性别</option>
				<option value="男">男</option>
				<option value="女">女</option>
			</select>
	</div>
	<div class="ip_box yzm_box">
		<div class="yzmhq" id="getCode">获取验证码</div>
		<input class="yzm" id="registerCode" value="" placeholder="验证码">
	</div>

	<div class="zc_box" id="doSubmit">注册(活动特开通道)</div>

	<div class="time_text">
		<img src="../../images/register/text.png">
	</div>

	<script type="text/javascript"
		src="../../js/common/jquery-1.11.2.min.js"></script>
	<script>
		$(function() {
			var inputgao = $(window).height() * 0.072;
			$("input").height(inputgao + "px");
			$("input").css("lineHeight", inputgao + "px");
			$(".yzmhq").css("lineHeight", inputgao + "px");
			$(".yzm_box .yzmhq").height(inputgao + "px");
			$(".ip_box .baliu").css("backgroundSize", "auto 100%");
			$(".yzmhq").css("backgroundSize", "auto 100%");
			$(".yzm_box").height(inputgao + "px");
			$(".ip_box .baliu").height(inputgao + "px");
			$(".ip_box").height(inputgao + "px");
			$(".zc_box").height(inputgao + "px");
			$(".zc_box").css("lineHeight", inputgao + "px");

			var wait = 60;
			var time =function (o) {
				if (wait == 0) {
					$(o).text("获取验证码");
					$(o).css("color", "#ededed");
					$(o).click(function() {
						time(this);
					});
					wait = 60;
				} else {
					$(o).unbind("click");
					$(o).css("color", "#ededed")
					$(o).css("fontSize", "12px")
					$(o).text("" + wait + "s");
					wait--;
					setTimeout(function() {
						time(o)
					}, 1000)
				}
			}
			//获取 验证码
			$("#getCode").click(function() {
				var phone = $("#phone").val();
				var regex=/^(1\d{10})$/;
				if(phone==""){
					alert("手机号码不能为空");$("#phone").focus();return ;
				}
				if (!regex.test(phone)) {
					alert("请输入正确的手机号码");$("#phone").focus();return ;
				}
				
				$.ajax({
					url : "../../activity/getCode.do",
					type : "post",
					dataType : "json",
					data : {
						"phone" : phone
					},
					success : function(json) {
						if (json.errorMsg){
							alert(json.errorMsg);return;
						}
						if(json.status=='success'){
							time($("#getCode"));
						}	
					}
				});
				
			});

			$("#doSubmit").click(function() {
				var phone = $("#phone").val();
				var pwd = $("#password").val();
				var registerCode = $("#registerCode").val();
				var sex=$("#sex").val();
				var regex=/^(1\d{10})$/;
				if(phone==''){
					alert("手机号码不能为空");$("#phone").focus();return ;
				}
				if (!regex.test(phone)) {
					alert("请输入正确的手机号码");
					$("#phone").focus();
					return;
				}
				if (pwd == '') {
					alert("密码不能为空");
					$("#password").focus();
					return;
				}
				if (registerCode == '') {
					alert("验证码不能为空");
					$("#registerCode").focus();
					return;
				}
				if(sex == ''){
					alert("请选择性别");
					return;
				}
				var _data={};
				_data.phone=phone;
				_data.password=pwd;
				_data.sex=sex;
				_data.registerCode=registerCode;
				$.ajax({
					url : "../../activity/weixin/register.do",
					type : "post",
					dataType : "json",
					data : _data,
						success : function(json) {
							if (json.errorMsg != null){
								alert(json.errorMsg);
							}
							if(json.status=='success'){
								window.location.href="<%=path%>/pages/register/register-success.jsp";
												}
											}
										});
							});
		});
	</script>
</body>
</html>
