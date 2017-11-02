<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件夹列表</title>
    <%
        String currPage = request.getParameter("currPage");
    %>
    <%@ include file="../common/header.jsp" %>
</head>
<%@ include file="../common/location.jsp" %>
<div class="tab-content padd">
    <div id="home">
        <div class="padd-bg">
            <form id="folderDataForm">
                <table class="table">
                    <tr>
                        <th width="5%">文件夹名称：</th>
                        <td width="15%"><input type="text" name="name" class="form-control"></td>
                        <th width="5%">是否系统文件夹：</th>
                        <td width="15%">
                            <select class="form-control" name="systemFolder">
                                <option value="">请选择</option>
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                        </td>
                        <th width="5%">用户姓名：</th>
                        <td width="15%"><input type="text" name="userName" class="form-control"></td>
                        <th width="5%">手机号码：</th>
                        <td width="15%"><input type="text" name="phone" class="form-control"></td>
                    </tr>
                    <tr>
                        <th>创建时间：</th>
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
                                       name="deadline" id="deadline"/>
                                <i class="icon-calendar-a"></i>
                            </div>
                        </td>
                        <th width="5%"></th>
                        <td width="15%">
                            <button type="button" id="folder_list_select" class="btn btn-blue">查询</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="folderOperation_div">
            <ul>
                <li class="ftl">
                    <button type="button" class="btn btn-blue" id="folder_add">新增</button>&nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-blue" id="folder_del">删除</button>&nbsp;&nbsp;&nbsp;&nbsp;
                </li>
            </ul>
        </div>
    </div>
    <hr class="hr">
    <div id="folder_List_grid"></div>
    <div class="text-right" id="folder_list_pagetool"></div>
</div>
<script src="../user/user-list.js?${ts}"></script>
<script src="folder-list.js?${ts}"></script>
<script src="https://cdn.bootcss.com/ckeditor/4.7.3/ckeditor.js"></script>
<script type="text/javascript">
    var currPage = <%=currPage%>;
    window.onload = function () {
        var folder = new window.hellogood.folder();
        folder.init();
        if (currPage != null) {
            folder.load(currPage, page.pageParams.pageSize);
        } else {
            folder.load(page.pageParams.page, page.pageParams.pageSize);
        }
        $('.form_datetime').datepicker();
    }
</script>
</body>
</html>
