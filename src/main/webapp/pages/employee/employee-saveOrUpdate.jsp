<%--
  update by Eclipse
  User: kejian
  Date: 2017/3/9
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>

<link href="<%=request.getContextPath() %>/js/citySelect/zyzn_1.css?${ts}" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/citySelect/City_data.js?${ts}"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/citySelect/areadata.js?${ts}"></script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function showUserCode() {
        var userCode = $('#employee_userCode').val();
        if ($.trim(userCode) != '') {
            util.hellogoodAjax({
                url: "user/getUser/" + userCode + ".do",
                succFun: function (json) {
                    if (json != null && json.dataList != null) {
                        data = json.dataList;
                        //将下拉列表至空
                        $("#showInfo").empty();
                        var size = data.length;
                        var selectedItem = 0;
                        //拼接对象
                        $.each(data, function (index, team) {
                            $("<li id='" + team.id + "'></li>").text(team.userCode + (team.userName != null && team.userName != '' ? "(" + team.userName + ")" : ''))
                                    .appendTo($("#showInfo")).addClass('clickable').hover(function () {
                                $(this).siblings().removeClass('highlight');
                                $(this).addClass('highlight');
                                selectedItem = index;
                            }, function () {
                                $(this).removeClass('highlight');
                                selectedItem = -1;
                            }).click(function () {
                                var userInfo = team.userCode;
                                $('#employee_userCode').val(userInfo);
                                $("#showInfo").empty().hide();
                            });
                        });
                        $("#showInfo").show();
                        $("#showInfo").css({"height": 25 * data.length + "px", "border": "1px solid #817F82"});
                        if (size == 0) {
                            $("#showInfo").hide();
                            $("#showInfo").css({"border": "0"});
                        }
                    }
                }
            });
        } else {
            $("#showInfo").hide();
        }
    }
</script>
<div class="padd">
    <form id="employee_box">
        <table class="table table-thead-box">
            <tbody>
            <tr>
                <th style="width:120px">名称：<span class="required">*</span></th>
                <td>
                    <div class="position form-group">
                        <input class="form-control" name="name" id="employee_name" dataType="Require"/>
                        <input type="hidden" name="id" id="employee_id"/>
                        <input type="hidden" name="status" id="employee_status"/>
                    </div>
                </td>

            </tr>
            <tr>
                <th style="width:120px">账号：<span class="required">*</span></th>
                <td>
                    <div class="position form-group">
                        <input class="form-control" name="account" id="employee_account" dataType="Require"/>
                    </div>
                </td>
            </tr>
              <tr>
                <th style="width:120px">密码：<span class="required">*</span></th>
                <td>
                    <div class="position form-group">
                        <input class="form-control" name="password" type="password" id="employee_password" dataType="Require"/>
                    </div>
                </td>
            </tr>
            
             <tr>
                <th style="width:120px">联系手机：<span class="required">*</span></th>
                <td>
                    <div class="position form-group">
                        <input class="form-control" name="mobilePhone" id="employee_mobilePhone" dataType="Mobile"/>
                    </div>
                </td>
            </tr>
             <tr>
                <th style="width:120px">邮箱：<span class="required">*</span></th>
                <td>
                    <div class="position form-group">
                        <input class="form-control" name="email" id="employee_email" dataType="Email"/>

                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>