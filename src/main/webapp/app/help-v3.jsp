<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>帮助</title>
    <link rel="stylesheet" href="https://h.happyeasy.com.cn/dist/screen.css?v=2">
    <link rel="stylesheet" href="https://h.happyeasy.com.cn/dist/ui.min.css">
    <script type="text/javascript" src="../js/common/jquery-1.11.2.min.js"></script>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color:white;
        }

        img {
            max-width: 100%;
        }

        p {
            display: block;
            -webkit-margin-before: 1em;
            -webkit-margin-after: 1em;
            -webkit-margin-start: 0px;
            -webkit-margin-end: 0px;
        }

        .box {
            width: 100%;
            margin-top: 0px;
            background-color:white;
        }

        .da {
            margin: auto;
            line-height: 20px;
            margin-left: 15px;
            margin-right: 15px;
            font-size: 16px;
            background-color:white;
        }
    </style>
    <%
        String id = request.getParameter("id");
    %>
    <script type="text/javascript">
        function showAndHide(obj) {
            if ($(obj).next().is(":visible")) {
                $(obj).next().hide();
            } else {
                $(obj).next().show();
            }
        }
        function loadData() {
            var id = <%=id%>;
            var html = "";
            $.ajax({
                url: "get/" + id + ".do",
                dataType: "json",
                success: function (data) {
                    html += "<div class='da'>" + data.data.content + "</div>"
                    $(".box").html(html);
                    if (data.data.type == "news")
                        $("#pop").show();
                },
                error: function () {
                    console.log(data);
                }
            });
        }
        $(function () {
            loadData();
        });
    </script>
</head>
<body>

<div class="box">
</div>
<div data-v-4fed07d1="" id="pop" class="pop" style="display: none">
    <div data-v-4fed07d1="" class="pop-content i2app-pop" style="height: 50px;">
        <div data-v-4fed07d1="" class="pop-main">
            <div data-v-4fed07d1="" class="mt-downclient">
                <div data-v-4fed07d1="" class="mt-downclient-title" style="padding-top: 10px;padding-bottom: 10px;"><span data-v-4fed07d1="" style="margin-left: 45px;font-size: 12px;">易悦</span>
                    <br data-v-4fed07d1="" ><span style="font-size: 10px;margin-left: 45px;padding-top: 10px;">加入易悦，与精英同行</span>
                </div>
                <img data-v-4fed07d1="" style="margin-left:5px;margin-top:4px;height: 40px;width: 40px;" src="https://h.happyeasy.com.cn/images/icons/120.png" class="mt-downclient-img"><a
                    data-v-4fed07d1=""
                    gaevent="imt/bdown"
                    rel="nofollow"
                    data-com="opendownload"
                    href="http://a.app.qq.com/o/simple.jsp?pkgname=com.hellogood.appointment"
                    target="_blank"
                    class="btn mt-downclient-btn mj-down btn-weak" style="width:80px;font-size: 10px;height: 30px;line-height: 30px;margin-top: 7px;margin-right: 5px;">点击下载</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>