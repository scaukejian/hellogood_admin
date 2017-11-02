<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>图片列表</title>
    <%
        String currPage = request.getParameter("currPage");
    %>
    <%@ include file="../common/header.jsp" %>
</head>
<%@ include file="../common/location.jsp" %>
<div class="tab-content padd">
    <div id="home">
        <div class="padd-bg">
            <form id="pictureDataForm">
                <table class="table">
                    <tr>
                        <th width="5%">图片类型：</th>
                        <td width="15%">
                            <select class="form-control" name="type">
                                <option value="">请选择</option>
                                <option value="app封面图">app封面图</option>
                            </select>
                        </td>
                        <th width="5%">图片名称：</th>
                        <td width="15%"><input type="text" name="fileName" class="form-control"></td>
                        <th width="5%">图片原始名称：</th>
                        <td width="15%"><input type="text" name="originalFileName" class="form-control"></td>
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
                            <button type="button" id="picture_list_select" class="btn btn-blue">查询</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="pictureOperation_div">
            <ul>
                <li class="ftl">
                    <button type="button" class="btn btn-blue" id="picture_add">新增</button>&nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-blue" id="picture_del">删除</button>&nbsp;&nbsp;&nbsp;&nbsp;
                </li>
            </ul>
        </div>
    </div>
    <hr class="hr">
    <div id="picture_List_grid"></div>
    <div class="text-right" id="picture_list_pagetool"></div>
</div>
<script src="picture-list.js?${ts}"></script>
<script type="text/javascript">
    var currPage = <%=currPage%>;
    window.onload = function () {
        var picture = new window.hellogood.picture();
        picture.init();
        if (currPage != null) {
            picture.load(currPage, page.pageParams.pageSize);
        } else {
            picture.load(page.pageParams.page, page.pageParams.pageSize);
        }
        $('.form_datetime').datepicker();
    }
</script>
</body>
</html>
