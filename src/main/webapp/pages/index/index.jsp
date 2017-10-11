<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<!-- saved from url=(0058)http://ruyo-net-demo.qiniudn.com/Bootstrap_left_menu.html# -->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>后台主页</title>
    <%@ include file="../common/header.jsp" %>
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <style>
        #main-nav {
            margin-left: 1px;
        }

        #main-nav.nav-tabs.nav-stacked > li > a {
            padding: 10px 8px;
            font-size: 12px;
            font-weight: 600;
            color: #4A515B;
            background: #E9E9E9;
            background: -moz-linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
            background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #FAFAFA),
            color-stop(100%, #E9E9E9));
            background: -webkit-linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
            background: -o-linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
            background: -ms-linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
            background: linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#FAFAFA',
            endColorstr='#E9E9E9');
            -ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr='#FAFAFA', endColorstr='#E9E9E9')";
            border: 1px solid #D5D5D5;
            border-radius: 4px;
        }

        #main-nav.nav-tabs.nav-stacked > li > a > span {
            color: #4A515B;
        }

        #main-nav.nav-tabs.nav-stacked > li.active > a, #main-nav.nav-tabs.nav-stacked > li > a:hover {
            color: #FFF;
            background: #48a9ff;
            background: -moz-linear-gradient(top, #5eb4ff 0%, #48a9ff 100%);
            background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #5eb4ff),
            color-stop(100%, #48a9ff));
            background: -webkit-linear-gradient(top, #5eb4ff 0%, #48a9ff 100%);
            background: -o-linear-gradient(top, #5eb4ff 0%, #48a9ff 100%);
            background: -ms-linear-gradient(top, #5eb4ff 0%, #48a9ff 100%);
            background: linear-gradient(top, #5eb4ff 0%, #48a9ff 100%);
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#5eb4ff',
            endColorstr='#48a9ff');
            -ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr='#5eb4ff', endColorstr='#48a9ff')";
        }

        #main-nav.nav-tabs.nav-stacked > li.active > a, #main-nav.nav-tabs.nav-stacked > li > a:hover > span {
            color: #FFF;
        }

        #main-nav.nav-tabs.nav-stacked > li {
            margin-bottom: 4px;
        }

        /*定义二级菜单样式*/
        .secondmenu a {
            font-size: 10px;
            color: #4A515B;
            text-align: center;
        }

        .navbar-static-top {
            background-color: #212121;
            margin-bottom: 5px;
        }

        .navbar-brand {
            background: url('') no-repeat 10px 8px;
            display: inline-block;
            vertical-align: middle;
            padding-left: 50px;
            color: #fff;
        }

        header {
            width: 100%;
            height: 62px;
            background-color: #f5f5f5;
            border-bottom: 2px #e8e8e8 solid;
        }

        header img {
            margin-top: -25px;
            width: 180px;
            margin-left: 10px;
        }

        .top_right {
            float: right;
            margin-right: 30px;
        }

        .top_right * {
            color: #2b2b2b;
            font-size: 12px;
            float: left;
            line-height: 82px;
            margin: 0 20px;
        }

        .urlclick {
            background: #48a9ff;
        }
    </style>

    <script type="text/javascript" src="index.js"></script>
    <script>
        var browserVersion = window.navigator.userAgent.toUpperCase();
        var isOpera = false, isFireFox = false, isChrome = false, isSafari = false, isIE = false;

        function reinitIframe(iframeId, minHeight) {
            try {
                var iframe = document.getElementById(iframeId);
                var bHeight = 0;
                if (isChrome == false && isSafari == false)
                    bHeight = iframe.contentWindow.document.body.scrollHeight;

                var dHeight = 0;
                if (isFireFox == true)
                    dHeight = iframe.contentWindow.document.documentElement.offsetHeight + 2;
                else if (isIE == false && isOpera == false)
                    dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
                else if (isIE == true && ! -[1, ] == false) { } //ie9+
                else
                    bHeight += 3;

                console.log('bHeight = ' + bHeight + ', dHeight = ' + dHeight );
                var height = Math.max(bHeight, dHeight);
                if (height < minHeight) height = minHeight;
                iframe.style.height = height + "px";
            } catch (ex) { }
        }

        function startInit(iframeId, minHeight) {
            isOpera = browserVersion.indexOf("OPERA") > -1 ? true : false;
            isFireFox = browserVersion.indexOf("FIREFOX") > -1 ? true : false;
            isChrome = browserVersion.indexOf("CHROME") > -1 ? true : false;
            isSafari = browserVersion.indexOf("SAFARI") > -1 ? true : false;
            if (!!window.ActiveXObject || "ActiveXObject" in window)
                isIE = true;
            window.setInterval("reinitIframe('" + iframeId + "'," + minHeight + ")", 200);
        }

        startInit('index_iframe', 650);

        window.onload = function () {
            var index = new window.hellogood.index();
            index.init();
        }
        function displayMenu(){
            if($('#_left').css("display") == 'block'){
                $('#_right').removeClass('col-md-10').addClass('col-md-pull-12');
                $('#_right').css('margin-left', '20px');
                $('#_left').css('display', 'none');
                $('#_display').text('显示菜单')
            }else{
                $('#_right').removeClass('col-md-pull-12').addClass('col-md-10');
                $('#_right').css('margin-left', '0');
                $('#_left').css('display', 'block');
                $('#_display').text('隐藏菜单');
            }

        }
        function goUrl(url, id) {
        	if(url=='/pages/welcome.jsp'){
        		 $("#index_iframe").attr("src", "<%=path%>/" + url);
        		 return;
        	}
            $("[id^=action_url_]").removeClass("urlclick");
            $("#action_url_" + id).addClass("urlclick");
            util
                    .hellogoodAjax({
                        url: "validate/getLocation.do", // "validate/loadSon/"+id+".do",
                        data: {"url": url},
                        succFun: function (json) {
                            $("#index_iframe").attr("src", "<%=path%>/" + url);
                        },
                        errFun: function (e) {
                            top.window.location.href = "<%=path%>" + "/login.do";
                        }
                    });
        }
    </script>
</head>

<body>
<header>
    <img src="../../images/index/logo2.png"/>
    <div class="top_right">
        <span>当前用户：${employee.name }</span>
        <a href="javaScript:modifyPassword();">修改密码</a>
        <a href="javascript:regout();">注销</a>
        <%--<a href="javascript:displayMenu();" id="_display">隐藏菜单</a>--%>
    </div>
</header>


<div class="container-fluid">
    <div class="row">
        <div class="col-md-2" id="_left">
            <ul id="main-nav" class="nav nav-tabs nav-stacked" style="">
                <li><a href="javascript:goUrl('/pages/welcome.jsp');">
                    <!--<i class="glyphicon glyphicon-
                    h-large"></i>--> 首页
                </a></li>
                <c:forEach items="${actions }" var="action">
                    <li id="action_url_${action.id}">
                    <c:choose>
                        <c:when test="${( not empty action ) and (action.url ne '') }">

                            <a href="javaScript:goUrl('${action.url }','${action.id }');">${action.name }</a>
                        </c:when>
                        <c:otherwise>
                            <a id="action_id" onclick="goAction('${action.id}','${employee.id }');">
                                    ${action.name } <span
                                    class="pull-right glyphicon glyphicon-chevron-down"></span>
                            </a>
                        </c:otherwise>
                    </c:choose>
                        <ul id="setting_${action.id }"
                            class="nav nav-list secondmenu collapse in"
                            style="display: none;">

                        </ul>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="line col-md-1"></div>
        <div class="col-md-10" id="_right" style="overflow:hidden;">
            <iframe id="index_iframe" src="<%=path%>/pages/welcome.jsp"
                    frameborder="0" width="100%" onload="this.height=100"  scrolling="no"></iframe>
        </div>
    </div>
</div>
<!--
<script src="jquery-2.1.4.min.js"></script>
<script src="bootstrap.min.js"></script>
 -->
<script type="text/javascript">


</script>

</body>
</html>