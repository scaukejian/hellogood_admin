<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ckeditor/ckeditor.js?${ts}"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common/base64.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common/jquery.qrcode.min.js"></script>
<style>
    .custom_li input {
        margin-left: 5px;
    }

    .custom_li button {
        margin-left: 5px;
    }
</style>
<div class="padd">
    <form id="userProfileFrom">
        <table class="table table-thead-box">
            <tbody>
            <tr>
                <th>头像：</th>
                <td colspan="2">
                    <div class="position form-group">
                        <div class="position form-group showPhotos" id="profilePictureDiv">

                        </div>
                    </div>
                </td>
                <td>
                    <span style="color: red;font-weight: bold;font-size: 26px" id="operationCheckStatus"></span>
                </td>
            </tr>
            <tr>
                <th>账号：</th>
                <td>
                    <input class="form-control" name="userCode" id="user_userCode" readonly/>
                    <input type="hidden" id="user_id" name="id">
                </td>
                <th>姓名：<span class="required">*</span></th>
                <td>
                    <input class="form-control" name="userName" id="user_userName" dataType="Require"/>
                </td>
            </tr>
            <tr>
                <th>电话：</th>
                <td>
                    <input class="form-control" name="phone" id="user_phone" readonly/>
                </td>
                <th>性别：<span class="required">*</span></th>
                <td>
                    <select type="text" name="sex" class="form-control" id="user_sex" dataType="Require">
                        <option value="">请选择</option>
                        <option value="男">男</option>
                        <option value="女">女</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>生日：<span class="required">*</span></th>
                <td>
                    <div class="position form-group">
                        <input class="form-control form_datetime" id="user_birthday" name="birthday"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                               dataType="Require"/>
                        <i class="icon-calendar-a" id="selectTime"></i>
                    </div>
                </td>
                <th>年龄：</th>
                <td>
                    <input class="form-control" name="age" id="user_age" readonly/>
                </td>
            </tr>
            <tr>
                <th>身高：</th>
                <td>
                    <input type="number" max="220" min="140" class="form-control" name="height" id="user_height"/>
                </td>
                <th>常住城市：<span class="required">*</span></th>
                <td>
                    <input class="form-control" name="liveCity" id="user_liveCity" dataType="Require"/>
                </td>
            </tr>
            <tr>
                <th>学历：</th>
                <td>
                    <input class="form-control" name="degree" id="user_degree" />
                </td>
                <th>星座：<span class="required">*</span></th>
                <td>
                    <input class="form-control" name="constellation" id="user_constellation" dataType="Require"/>
                </td>
            </tr>
            <tr>
                <th>婚姻状况：</th>
                <td>
                    <select class="form-control" name="maritalStatus" id="user_maritalStatus">
                        <option value="">请选择</option>
                    </select>
                </td>
                <th>学校:</th>
                <td>
                    <input class="form-control" name="school" id="user_school" />
                </td>
            </tr>
            <tr>
                <th>公司:</th>
                <td>
                    <input class="form-control" name="company" id="user_company" />
                </td>
                <th>公司:</th>
                <td>
                    <input class="form-control" name="job" id="user_job" />
                </td>

            </tr>
            <tr>
                <th>微信:</th>
                <td>
                    <input class="form-control" name="weixinName" id="user_weixinName" />
                </td>
                <th></th>
                <td>
                </td>
            </tr>
            <tr>
                <th>备注</th>
                <td colspan="3">
                    <div id="userRemarkEditor"></div>
                </td>
            </tr>
            </tbody>
        </table>
        <div id="profileOperationDiv" style="clear:both; padding: 20px;text-align: center">
            <button class="btn" style="background-color: rgb(9, 152, 84)" id="updateUserProfileBtn"
                    onclick="return false">修改
            </button>
        </div>
    </form>

</div>

<script type="text/javascript">
    $(function () {
        $('#userProfileFrom').css('display', 'block');
    });

    function updateRemark() {
        var userId = $('#user_id').val();
        var remark = $('#user_remark').val();
        var vo = {'userId': userId, 'remark': remark};
        util.hellogoodAjax({
            url: 'user/updateUserRemark.do',
            data: vo,
            succFun: function (json) {
                if (json.errorMsg) {
                    $.Prompt(json.errorMsg);
                    return;
                }
                $.Prompt('操作完成');
            }
        })
    }

    $('.my_title div').click(function () {
        var divId = $(this).attr('id');
        switch (divId) {
            case 'profile' :
                $('#userProfileFrom').css('display', 'block');
                break;
        }
    });
</script>