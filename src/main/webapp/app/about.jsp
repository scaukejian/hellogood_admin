<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>关于我们</title>
<script type="text/javascript" src="../js/common/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
function loadData(){
	var html = "";
	$.ajax({
		url:"getContentEditByType.do",
		data:{"type":"about"},
		dataType:"json",
		success:function(data){
			var dataList = data.data;
			/* for(var i=0;i<dataList.length;i++){
				html+="<span>"+dataList[i].title+"</span>"
				html+="<div>"+dataList[i].content+"</div>"
			} */
			if(dataList.length>0){
				html+="<div>"+dataList[0].content+"</div>"
			}
			$(".aboutShow").html(html);
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
<div class="aboutShow">
</div>
</body>
</html>