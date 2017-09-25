<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="../js/common/jquery-1.11.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	j = 1;
	$(document).ready(function(){
		$("#btn_add").click(function(){
			document.getElementById("newUpload").innerHTML+='<div id="div_'+j+'"><input  name="file_'+j+'" type="file"  /><input type="button" value="删除"  onclick="del('+j+')"/></div>';
			  j = j + 1;
		});
	});

	function del(o){
		 document.getElementById("newUpload").removeChild(document.getElementById("div_"+o));
	}

</script>
</head>
<body>
 	 <h1>springMVC包装类上传文件</h1> 
 	 <h2>用户资料上传</h2>
 	<form name="userForm" action="/hellogood-ws/userDatum/upload.do" enctype="multipart/form-data" method="post">
 		<div id="newUpload">
			<input type="file" name="files">
		</div>
		<input type="hidden" name="userId" value="2">
		类型：<select name="type">
			<option value="JOB">工作照片</option>
			<option value="IDCARD">身份证图片</option>
			<option value="MARITAL">婚姻图片</option>
			<option value="DEGREE">学历图片</option>
			<option value="VIDEO">视频</option>
		</select> 
		<input type="button" id="btn_add" value="增加一行" >
		<input type="submit" value="上传" >
 	</form>

     <form name="download" action="/hellogood-ws/userDatum/download.do" method="post">
         <input type="text" name="fileName">
         <input type="submit" value="下载" >
     </form>


     <h1></h1>
     <h2>用户头像上传</h2>
     <form name="userForm" action="/hellogood-ws/user/upload.do" enctype="multipart/form-data" method="post">
         <div>
             <input type="file" name="file">
         </div>
         <input type="hidden" name="userId" value="2">
         <input type="submit" value="上传" >
     </form>

     <h1></h1>
     <h2>相册上传</h2>
     <form name="photoForm" action="/hellogood-ws/photo/upload.do" enctype="multipart/form-data" method="post">
         <div>
             <input type="file" name="files">
         </div>
         <input type="hidden" name="userId" value="2">
         <input type="submit" value="上传" >
     </form>

     <form name="download" action="/hellogood-ws/photo/delete.do" method="post">
          userId：<input type="text" name="userId">
         <input type="submit" value="下载" >
     </form>
</body>
</html>