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
    <title>职工列表</title>
    <%@ include file="../common/header.jsp"%>
    <script src="employee-list.js?${ts}"></script>
    <script type="text/javascript">
        window.onload = function(){
            var employee = new window.hellogood.employee();
            employee.init();
            employee.load(page.pageParams.page , page.pageParams.pageSize);
        }
    </script>
</head>
<body>
<%@ include file="../common/location.jsp" %>
<div class="tab-content padd">
    <div id="home">
        <div class="padd-bg">
            <form id="employeeDataForm">
                <table class="table">
                    <tr>
                        <th width="5%">名称：</th>
                        <td width="15%">
                            <input type="text" name="name" class="form-control" >
                        </td >
                        <th width="5%">账号：</th>
                        <td width="15%">
                            <input type="text" name="userCode" class="form-control" >
                        </td >
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td width="15%">
                            <button type="button" id="employee_list_select" class="btn btn-blue" >查询</button>
                    </td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="employeeOperation_div">
            <ul>
                <li class="ftl">
                    <button type="button" class="btn btn-blue" id="employee_add">新增</button>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-blue" id="employee_del">删除</button>
                </li>
            </ul>
        </div>
    </div>
    <hr class="hr">
    <div id="employee_List_grid"></div>
    <div class="text-right" id="employee_list_pagetool"></div>
</div>
</body>
</html>
