<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/21
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色列表</title>
    <%@ include file="../common/header.jsp"%>
    <script src="role-list.js?${ts}"></script>
    <script type="text/javascript">
        window.onload = function(){
            var role = new window.hellogood.role();
            role.init();
            role.load(page.pageParams.page , page.pageParams.pageSize);
        }
    </script>
</head>
<body>
<%@ include file="../common/location.jsp" %>
<div class="tab-content padd">
    <div id="home">
        <div class="padd-bg">
            <form id="roleDataForm">
                <table class="table">
                    <tr>
                        <th width="5%">名称：</th>
                        <td width="15%">
                            <input type="text" name="name" class="form-control" >
                        <td >
                        <td width="15%">
                            <button type="button" id="role_list_select" class="btn btn-blue" >查询</button>
                    </td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="roleOperation_div">
            <ul>
                <li class="ftl">
                    <button type="button" class="btn btn-blue" id="role_add">新增</button>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-blue" id="role_del">删除</button>
                </li>
            </ul>
        </div>
    </div>
    <hr class="hr">
    <div id="role_List_grid"></div>
    <div class="text-right" id="role_list_pagetool"></div>
</div>
</body>
</html>
