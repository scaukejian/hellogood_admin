<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>基本数据列表</title>
    <%@ include file="../common/header.jsp"%>
    <link href="../../css/bootstrap/bootstrap.min.css">
    <script src="../../js/common/ajaxfileupload.js" type="text/javascript"></script>
    <script src="baseData-list.js?${ts}"></script>
    <link rel="stylesheet" href="../../css/lightbox.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="../../js/common/jquery.lightbox.js"></script>
    <script type="text/javascript">
        window.onload = function () {
            var baseData = new window.hellogood.baseData();
            baseData.init();
            baseData.load(page.pageParams.page, page.pageParams.pageSize);
        }
    </script>
</head>
<body>
<%@ include file="../common/location.jsp" %>
<div class="tab-content padd">
    <div id="home">
        <div class="padd-bg">
            <form id="baseDataForm">
                <table class="table">
                    <tr>
                        <th width="5%">类型：</th>
                        <td width="15%">
                            <div class="position form-group">
                                <select id="baseDataType" name="type" class="form-control">
                                    <option value="">请选择</option>
                                </select>
                            </div>
                        </td>
                        <th width="5%">编号：</th>
                        <td width="15%">
                            <input type="text" name="code" class="form-control">
                        </td>
                        <th width="5%">名称：</th>
                        <td width="15%">
                            <input type="text" name="name" class="form-control">
                        </td>
                         <th width="5%">状态：</th>
                        <td width="15%">
                            	<select id="baseDataStatus" name="status" class="form-control">
                                    <option value="1">使用中</option>
                                    <option value="9999">请选择</option>
                                    <option value="0">作废</option>
                                </select>
                        </td>
                        <td width="20%">
                        </td>
                        <td width="15%">
                            <button type="button" id="baseData_list_select" class="btn btn-blue">查询</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="userOperation_div">
            <button type="button" class="btn btn-blue" id="baseData_add">新增</button>
            <!-- 
            <button type="button" class="btn btn-blue" id="baseData_del">删除</button>
             -->
        </div>
    </div>
    <hr class="hr">
    <div id="baseData_List_grid"></div>
    <div class="text-right" id="baseData_list_pagetool"></div>
</div>
</body>
</html>
