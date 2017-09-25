<%--
  Created by IntelliJ IDEA.
  User: kejian
  Date: 2017/5/21
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="padd">
    <form id="index_box">
        <table class="table table-thead-box">
            <tbody>
            <tr>
                <th style="width:85px">原密码：<span class="required">*</span></th>
                <td>
                    <div class="position form-group">
                        <input class="form-control" name="oldPassword" id="old_password" type="password" dataType="Require"/>

                    </div>
                </td>
            </tr>
              <tr>
                <th style="width:85px">新密码：<span class="required">*</span></th>
                <td>
                    <div class="position form-group">
                        <input class="form-control" name="newPassword" id="new_password"  type="password" dataType="Require"/>

                    </div>
                </td>
            </tr>
              <tr>
                <th style="width:85px">再次输入新密码：<span class="required">*</span></th>
                <td>
                    <div class="position form-group">
                        <input class="form-control" name="newPasswordTow" id="new_password_tow"  type="password" dataType="Require"/>

                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>