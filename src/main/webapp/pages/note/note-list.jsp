<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>计划列表</title>
    <%
        String currPage = request.getParameter("currPage");
    %>
    <%@ include file="../common/header.jsp" %>
</head>
<body>
<%@ include file="../common/location.jsp" %>
<div class="tab-content padd">
    <div id="home">
        <div class="padd-bg">
            <form id="noteDataForm">
                <table class="table">
                    <tr>
                        <th width="5%">姓名：</th>
                        <td width="15%"><input type="text" name="userName" class="form-control"></td>
                        <th width="5%">手机号码：</th>
                        <td width="15%"><input type="text" name="phone" class="form-control"></td>
                        <th width="5%">终端唯一码：</th>
                        <td width="15%"><input type="text" name="phoneUniqueCode" class="form-control"></td>
                        <th width="5%">内容：</th>
                        <td width="15%"><input type="text" name="content" class="form-control"></td>
                    <tr>
                        <th width="5%">类型：</th>
                        <td width="15%">
                            <select class="form-control" name="type">
                                <option value="">请选择</option>
                                <option value="日">日计划</option>
                                <option value="周">周计划</option>
                                <option value="月">月计划</option>
                                <option value="季">季计划</option>
                                <option value="年">年计划</option>
                            </select>
                        </td>
                        <th width="5%">是否置顶：</th>
                        <td width="15%">
                            <select class="form-control" name="top">
                                <option value="">请选择</option>
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                        </td>
                        <th width="5%">有效状态：</th>
                        <td width="15%">
                            <select class="form-control" name="validStatus">
                                <option value="">请选择</option>
                                <option value="1">有效</option>
                                <option value="0">无效</option>
                            </select>
                        </td>
                        <th width="5%"></th>
                        <td width="15%">
                            <button type="button" id="note_list_select" class="btn btn-blue">查询</button>
                        </td>
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
                    </tr>
                </table>
            </form>
        </div>
        <div id="noteOperation_div">
            <ul>
                <li class="ftl">
                    <button type="button" class="btn btn-blue" id="note_add">新增</button>&nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-blue" id="note_del">删除</button>&nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-blue" id="note_disabled">停用</button>&nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-blue" id="note_enabled">启用</button>&nbsp;&nbsp;&nbsp;&nbsp;
                </li>
            </ul>
        </div>
    </div>
    <hr class="hr">
    <div id="note_List_grid"></div>
    <div class="text-right" id="note_list_pagetool"></div>
</div>
<script src="../user/user-list.js?${ts}"></script>
<script src="note-list.js?${ts}"></script>
<script src="https://cdn.bootcss.com/ckeditor/4.7.3/ckeditor.js"></script>
<script type="text/javascript">
    var currPage = <%=currPage%>;
    window.onload = function () {
        var note = new window.hellogood.note();
        note.init();
        if (currPage != null) {
            note.load(currPage, page.pageParams.pageSize);
        } else {
            note.load(page.pageParams.page, page.pageParams.pageSize);
        }
        $('.form_datetime').datepicker();
    }
</script>
</body>
</html>
