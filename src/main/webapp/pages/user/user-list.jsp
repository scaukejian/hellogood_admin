<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户列表</title>
    <%
        String currPage = request.getParameter("currPage");
    %>
    <%@ include file="../common/header.jsp" %>
    <script type="text/javascript" src="../../js/lightbox/js/lightbox.js"></script>
    <link rel="shortcut icon" href="../../js/lightbox/img/demopage/favicon.png">
    <link rel="stylesheet" href="../../js/lightbox/css/lightbox.css">
    <link href="../../css/bootstrap/bootstrap.min.css">
    <script src="../../js/common/ajaxfileupload.js" type="text/javascript"></script>
    <script src="user-list.js?${ts}"></script>
    <script src="user-upload.js?${ts}"></script>
    <link rel="shortcut icon" href="../../">
    <link rel="stylesheet" href="../../css/user.css" type="text/css" media="screen"/>
    <script type="text/javascript">
        var currPage = <%=currPage%>;
        window.onload = function () {
            var user = new window.hellogood.user();
            user.init();
            if (currPage != null) {
                user.load(currPage, page.pageParams.pageSize);
            } else {
                user.load(page.pageParams.page, page.pageParams.pageSize);
            }
            $('.form_datetime').datepicker();
        }
    </script>
</head>
<body>
<%@ include file="../common/location.jsp" %>
<div class="tab-content padd">
    <div id="home">
        <div class="padd-bg">
            <form id="userDataForm">
                <table class="table">
                    <tr>
                        <th width="7%">账号：</th>
                        <td width="17%"><input type="text" name="userCode"
                                               class="form-control"></td>
                        <th width="3%">姓名：</th>
                        <td width="13%"><input type="text" name="userName"
                                               class="form-control"></td>
                        <th width="5%">性别：</th>
                        <td width="15%"><select class="form-control" name="sex">
                            <option value="">请选择</option>
                            <option value="男">男</option>
                            <option value="女">女</option>
                        </select></td>
                        <th>年龄：</th>
                        <td>
                            <div class="position form-group">
                                <input class="form-control" style="width:45%;float:left;" name="minAge"/>
                                <span style="float:left;width:10%;">~</span>
                                <input class="form-control" style="width:45%;float:left;" name="maxAge"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>身高:</th>
                        <td>
                            <div class="position form-group">
                                <input class="form-control" style="width:45%;float:left;" name="minHeight"/>
                                <span style="float:left;width:10%;">~</span>
                                <input class="form-control" style="width:45%;float:left;" name="maxHeight"/>
                            </div>
                        </td>
                        <th width="5%">电话：</th>
                        <td width="15%"><input type="text" name="phone"
                                               class="form-control"></td>
                        <th>婚姻状况:</th>
                        <td>
                            <select class="form-control" name="maritalStatus" id="user_maritalStatus_list">
                                <option value="">请选择</option>
                            </select>
                        </td>
                        <th>常住城市：</th>
                        <td><input type="text" name="liveCity"
                                   class="form-control"></td>
                    </tr>
                    <tr>
                    <tr>
                    <th>注册时间：</th>
                    <td>
                        <div class="position form-group">
                            <input class="form-control form_datetime"
                                   onkeydown="javaScript:window.event.returnValue= false;"
                                   name="startDate" id="startDate">
                            <i class="icon-calendar-a"></i>
                        </div>
                    </td>
                    <th>至：</th>
                    <td>
                        <div class="position form-group">
                            <input class="form-control form_datetime"
                                   onkeydown="javaScript:window.event.returnValue= false;"
                                   name="deadline" id="deadline" dataType="Require"/>
                            <i class="icon-calendar-a"></i>
                        </div>
                    </td>
                        <th width="5%">审核状态：</th>
                        <td width="15%">
                            <select class="form-control" name="checkStatus">
                                <option value="">请选择</option>
                                <option value="审核中">审核中</option>
                                <option value="通过">通过</option>
                                <option value="拒绝">拒绝</option>
                            </select>
                        </td>
                    <td width="5%">
                        <button type="button" id="user_list_select" class="btn btn-blue">查询</button>
                    </td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="userOperation_div">
            <ul>
                <li class="ftl">
                    <button type="button" class="btn btn-blue" id="user_add">新增</button>
                </li>
            </ul>
        </div>
    </div>
    <hr class="hr">
    <div id="user_List_grid"></div>
    <div class="text-right" id="user_list_pagetool"></div>
</div>
</body>
</html>