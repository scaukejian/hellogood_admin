<%--
  Created by IntelliJ IDEA.
  User: kejian
  Date: 16/8/10
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>VIP内容</title>
    <script type="text/javascript" src="../../js/common/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="../../js/common/base64.js"></script>
    <script src="../../js/common/jquery.lazyload.min.js"></script>
    <%
        String vipId = request.getParameter("vipId");
        String inviteType = request.getParameter("inviteType");
        String inviteUid = request.getParameter("inviteUid");
        String path = request.getContextPath();
    %>
    
    <script type="text/javascript">
    
	    var vipId = <%=vipId%>;
	    var inviteUid = <%=inviteUid%>;
	    var inviteType = '<%=inviteType%>';
	    var path = '<%=path%>';
    
        
        window.onload = function(){
            if(null == vipId) return;
            $.ajax({
                url : path + '/app/getVipContent/' +vipId+ '.do',
                dataType : 'json',
                success : function(json){
                    if(json.errorMsg){
                        alert(json.errorMsg);
                        return;
                    }
                    if(null == json.data.content){
                        return;
                    }
                    $('#contentDiv').html(Base64.decode(json.data.content));


                    $('#contentDiv img').each(function(){
                        if( !$(this).attr("data-original") ){
                            $(this).attr("data-original", $(this).attr("src") ).attr("src", "../img/loading_2.gif");
                        }
                    });

                    $('#contentDiv img').lazyload({
                        placeholder : "../img/loading_2.gif",
                        effect: "fadeIn"
                    });
                }
            })
            
         	 //是否为分享
    	    var shareUrl = path + '/app/invite/register.jsp';
            //旧版分享不传inviteUid
            if(null != inviteUid  && 'null' != inviteUid && '0' != inviteUid){
                $('.footer').css('display', 'block');
                shareUrl += '?inviteType=' + inviteType; //邀请类型
                shareUrl += '&functionType=vip';//邀请类型
                shareUrl += '&inviteUid=' + inviteUid; //邀请人
                shareUrl += '&sourceId=' + vipId; //来源id(活动ID)
            }
            
            $('.footer').click(function () {
    	        window.open(shareUrl);
    	    });
        }
    </script>
    <style>
        body{
            margin:0;
            padding:0;
            width: 100%;
        }
        #contentDiv{
            margin: 0;
        }

        #contentDiv img{
            width: 100%;
        }
    </style>
</head>
<body>
<div id="contentDiv">

</div>
<a class="footer" style="display:none;width: 100%;position: fixed;bottom: -5px;">
    <img src="../../images/footerSignUpPay.png" width="100%" height="100%"/>
</a>
</body>
</html>
