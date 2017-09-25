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
    <title>菜单列表</title>
    <%@ include file="../common/header.jsp"%>
    <script src="action-list.js?${ts}"></script>
    <script type="text/javascript">
        window.onload = function(){
            var action = new window.hellogood.action();
            action.init();
            action.load(page.pageParams.page , page.pageParams.pageSize);
        }
    </script>
</head>
<body>
<%@ include file="../common/location.jsp" %>
<div class="tab-content padd">
    <div id="home">
        <div class="padd-bg">
            <form id="actionDataForm">
                <table class="table">
                    <tr>
                        <th width="5%">名称：</th>
                        <td width="15%">
                            <input type="text" name="name" class="form-control" >
                        <td >
                        <th width="5%">类型：</th>
                        <td width="15%">
                            	<select name="type" class="form-control">
                            		<option value="0">请选择</option>
                            		<option value="1">一级菜单</option>
                            		<option value="2">二级菜单</option>
                            		<option value="3">三级菜单</option>
                            	</select>
                        <td >
                          <th width="5%"></th>
                        <td width="15%">
                            
                        <td >
                        <td width="15%">
                            <button type="button" id="action_list_select" class="btn btn-blue" >查询</button>
                    	</td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="actionOperation_div">
            <ul>
                <li class="ftl">
                    <button type="button" class="btn btn-blue" id="action_add">新增</button>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-blue" id="action_del">删除</button>
                </li>
            </ul>
        </div>
    </div>
    <hr class="hr">
    <div id="action_List_grid"></div>
    <div class="text-right" id="action_list_pagetool"></div>
</div>
</body>
</html>
