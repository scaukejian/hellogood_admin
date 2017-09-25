<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<link rel="shortcut icon" href="../../images/register/favicon.ico" type="image/x-icon">
<link rel="Bookmark" href="../../images/register/favicon.ico" type="image/x-icon">
<script type="text/javascript"
	src="../../js/common/jquery-1.11.2.min.js"></script>
<title>易约，您的专属约会管家</title>
<style>
body{padding:0;margin:0;background-color:#252525;width:100%;height:auto;}
img{padding:0;margin:0;display:block;}
.top_zhuce{margin-top:6.25%;width:80%;margin-left:10%;margin-bottom:1%;}
.top_zhuce img{width:100%;}
.banner{width:80%;margin-left:10%;margin-bottom:8%;}
.banner img{width:100%;}
.xiazai{width:81.4%;margin:auto;}
.xiazai img{width:100%;}
.text{width:80%;margin-left:10%;margin-top:5%;}.text img{width:100%;}
.bottom_bg{width:100%;background-color:#f9f9f9;position:fixed;bottom:0;z-index:-1;}
</style>
</head>

<body>
<div class="top_zhuce"><img src="../../images/ruici/zhuce.png"></div>
<div class="banner"><img src="../../images/ruici/banner.jpg"></div>
<div class="xiazai"><a class="xiazai_btn"><img src="../../images/register/xiazai.png"></a></div>
<div class="text"></div>
<div class="bottom_bg"></div>
<script>
$(function(){
		var gao = $(window).height()*0.7;
		$(".bottom_bg").css("height",gao+"px");
	});
</script>
<script type="text/javascript">
        function checkPlatform(){

				  if(/android/i.test(navigator.userAgent)){
				
					   $(".xiazai_btn").attr("href","http://ws.hellogood.com.cn/app/Appointment.apk");//这是Android平台下浏览器
				
				  }
				
				  if(/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)){
				
					  $(".xiazai_btn").attr("href","https://itunes.apple.com/cn/app/yi-yue-yue-hui/id1003136897?mt=8");//这是iOS平台下浏览器

				  }
								}
				  $(document).ready(function(){
					
						//alert(navigator.platform);
					
						checkPlatform();
					
					  });

        </script>
        <script type="text/javascript">
		window.onload = function(){
			if(isWeiXin()){
				$(".xiazai_btn").attr("href","http://a.app.qq.com/o/simple.jsp?pkgname=com.hellogood.appointment&g_f=991653")
			}else{
				
			}
		}
		function isWeiXin(){
			var ua = window.navigator.userAgent.toLowerCase();
			if(ua.match(/MicroMessenger/i) == 'micromessenger'){
				return true;
			}else{
				return false;
			}
		}
		</script>
</body>
</html>