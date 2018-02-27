<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <th width="15%">账号：<span class="required">*</span></th>
                <td width="35%">
                    <input class="form-control" name="userCode" id="user_userCode" dataType="Require" />
                    <input type="hidden" id="user_id" name="id">
                    <input type="hidden" name="headPhotoId" id="headPhotoId"/>
                </td>
                <th width="15%">姓名：<span class="required">*</span></th>
                <td width="35%">
                    <input class="form-control" name="userName" id="user_userName" dataType="Require"/>
                </td>
            </tr>
            <tr>
                <th>手机号：<span class="required">*</span></th>
                <td>
                    <input class="form-control" name="phone" id="user_phone" />
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
                    <input class="form-control" name="age" id="user_age"/>
                </td>
            </tr>
            <tr>
                <th>身高：</th>
                <td>
                    <input type="number" max="220" min="140" class="form-control" name="height" id="user_height" />
                </td>
                <th>体重：</th>
                <td>
                    <input type="number"  class="form-control" name="weight" id="user_weight" />
                </td>
            </tr>
            <tr>
                <th>微信：<span class="required">*</span></th>
                <td>
                    <input class="form-control" name="weixinName" id="user_weixinName" datatype="Require" />
                </td>
                <th>QQ：<span class="required">*</span></th>
                <td>
                    <input class="form-control" name="qq" id="user_qq" datatype="Require" />
                </td>
            </tr>
            <tr>
                <th>Email：</th>
                <td>
                    <input class="form-control" name="email" id="user_email" />
                </td>
                <th>常住城市：</th>
                <td>
                    <input class="form-control" name="liveCity" id="user_liveCity"/>
                </td>
            </tr>
            <tr>
                <th>学历：</th>
                <td>
                    <input class="form-control" name="degree" id="user_degree" />
                </td>
                <th>星座：</th>
                <td>
                    <input class="form-control" name="constellation" id="user_constellation"/>
                </td>
            </tr>
            <tr>
                <th>婚姻状况：</th>
                <td>
                    <select class="form-control" name="maritalStatus" id="user_maritalStatus">
                        <option value="">请选择</option>
                        <option value="未婚">未婚</option>
                        <option value="已婚">已婚</option>
                    </select>
                </td>
                <th>学校：</th>
                <td>
                    <input class="form-control" name="school" id="user_school" />
                </td>
            </tr>
            <tr>
                <th>公司：</th>
                <td>
                    <input class="form-control" name="company" id="user_company" />
                </td>
                <th>职位：</th>
                <td>
                    <input class="form-control" name="job" id="user_job" />
                </td>
            </tr>
            <tr>
                <th>个性签名：</th>
                <td colspan="3">
                    <input class="form-control" name="characteristicSignature" id="user_characteristicSignature" />
                </td>
            </tr>
            <tr>
                <th>备注：</th>
                <td colspan="3">
                    <div id="userRemarkEditor"></div>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common/base64.js"></script>

