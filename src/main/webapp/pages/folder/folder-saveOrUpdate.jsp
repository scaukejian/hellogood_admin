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
        var userName = $('#folder_userName').val();
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
                                $('#folder_userId').val(team.id);
                                $('#folder_userName').val(userInfo);
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
    function showUserRow() {
        if ($("#folder_systemFolder").val() == 0) {
            $("#userTr").show();
        } else {
            $("#userTr").hide();
        }
    }
</script>
<div class="padd">
    <form id="folderProfileFrom">
        <table class="table table-thead-box">
            <tbody>
                <tr>
                    <th width="20%">文件夹名称：<span style="color: red">*</span></th>
                    <td width="70%"><input type="text" class="form-control" name="name" id="folder_name" datatype="Require"/></td>
                </tr>
                <tr>
                    <th width="20%">是否系统文件夹：</th>
                    <td width="70%">
                        <select type="text" name="systemFolder" class="form-control" id="folder_systemFolder" datatype="Require" onchange="showUserRow()">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </td>
                </tr>
                <tr style="display: none" id="userTr">
                    <th width="20%">姓名：</th>
                    <td width="70%">
                        <div class="position form-group">
                            <input class="form-control" name="userName" id="folder_userName" autocomplete="off" onkeyup="showUserName()">
                            <div style="width:100%;display:none;z-index:9999;position: absolute;
                                background-color: white;filter:alpha(opacity=100);" id="showInfo">
                            </div>
                        </div>
                        <input type="hidden" id="folder_userId" name="userId">
                        <input type="hidden" id="folder_id" name="id">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>