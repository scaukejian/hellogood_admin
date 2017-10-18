<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .custom_li input {
        margin-left: 5px;
    }
    .custom_li button {
        margin-left: 5px;
    }
</style>
<script>
    function showUserName() {
        var userName = $('#note_userName').val();
        if ($.trim(userName) != '') {
            util.hellogoodAjax({
                url: "user/getUserByUserName/" + userName + ".do",
                succFun: function (json) {
                    if (json != null && json.dataList != null) {
                        data = json.dataList;
                        //将下拉列表至空
                        $("#showInfo").empty();
                        var size = data.length;
                        var selectedItem = 0;
                        //拼接对象
                        $.each(data, function (index, team) {
                            $("<li id='" + team.id + "'></li>").text(team.userName + (team.phone != null && team.phone != '' ? "(" + team.phone + ")" : ''))
                                .appendTo($("#showInfo")).addClass('clickable').hover(function () {
                                $(this).siblings().removeClass('highlight');
                                $(this).addClass('highlight');
                                selectedItem = index;
                            }, function () {
                                $(this).removeClass('highlight');
                                selectedItem = -1;
                            }).click(function () {
                                var userInfo = team.userName;
                                $('#note_userId').val(team.id);
                                $('#note_userName').val(userInfo);
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
    <form id="noteProfileFrom">
        <table class="table table-thead-box">
            <tbody>
            <tr>
                <th width="20%">姓名：</th>
                <td width="70%">
                    <div class="position form-group">
                        <input class="form-control" name="userName" id="note_userName" autocomplete="off" onkeyup="showUserName()">
                        <div style="width:100%;display:none;z-index:9999;position: absolute;
                    		background-color: white;filter:alpha(opacity=100);" id="showInfo">
                        </div>
                    </div>
                    <input type="hidden" id="note_userId" name="userId">
                    <input type="hidden" id="note_id" name="id">
                </td>
            </tr>
            <tr>
                <th width="20%">终端唯一码：</th>
                <td width="70%">
                    <input class="form-control" name="phoneUniqueCode" id="note_phoneUniqueCode" />
                </td>
            </tr>
            <tr>
                <th width="20%">有效状态：</th>
                <td width="70%">
                    <select type="text" name="validStatus" class="form-control" id="note_validStatus">
                        <option value="">请选择</option>
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th width="20%">是否置顶：</th>
                <td width="70%">
                    <select type="text" name="top" class="form-control" id="note_top">
                        <option value="">请选择</option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th width="20%">计划类型：</th>
                <td width="70%">
                    <select type="text" name="type" class="form-control" id="note_type" datatype="Require">
                        <option value="">请选择</option>
                        <option value="日">日计划</option>
                        <option value="周">周计划</option>
                        <option value="月">月计划</option>
                        <option value="季">季计划</option>
                        <option value="年">年计划</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th width="20%">颜色(16进制)：</th>
                <td width="70%">
                    <input class="form-control" name="color" id="note_color" placeholder="16进制（如：#FFFFFF）"/>
                </td>
            </tr>
            <tr>
                <th width="20%">内容：<span style="color:red">*</span></th>
                <td width="70%">
                    <div id="content"></div>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common/base64.js?${ts}"></script>