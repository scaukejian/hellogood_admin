<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>帮助</title>
<script type="text/javascript" src="../js/common/jquery-1.11.2.min.js"></script>
<style>
body{margin:0;padding:0;}
span{display:block;}
.box{width:100%;margin-top:1px;border-bottom:1px dotted #CCCCCC;}
.wt{border-radius:4px;width:96%;margin:auto;line-height:40px;font-size:15px;}
.da{width:96%;margin:auto;font-size:13px; text-indent:1em;color:#3d3d3d;margin-bottom:10px;line-height:20px;}
.kk{text-indent:1em;margin-bottom:6px;}
</style>
<script type="text/javascript">
function showAndHide(obj){
	if($(obj).next().is(":visible")){
		$(obj).next().hide();
	}else{
		$(obj).next().show();
	}
}
function loadData(){
	var html = "";
	$.ajax({
		url:"getContentEditByType.do",
		data:{"type":"helpCenter"},
		dataType:"json",
		success:function(data){
			var dataList = data.data;
			for(var i=0;i<dataList.length;i++){
				/*html+="<div class='wt' onclick='showAndHide(this)'>&gt;&nbsp;"+dataList[i].title+"</div>" */
				html+="<div class='da' >"+dataList[i].content+"</div>"
			}
			$(".box").html(html);
		},
		error:function(){
			console.log(data);
		}
	});
}
$(function(){
	loadData();
});
</script>
</head>
<body>

<div class="box" style="margin-top: 3px;">
</div>
</body>
</html>