<%--
  Created by IntelliJ IDEA.
  User: kejian
  Date: 2017/5/21
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="padd">
    <form id="role_box">
        <table class="table table-thead-box">
            <tbody>
            <tr>
                <th style="width:85px">代码：<span class="required">*(仅支持英文)</span></th>
                <td>
                    <div class="position form-group">
                        <input class="form-control" name="code" id="role_code" dataType="Require"/>
                        <input type="hidden" name="id" id="role_id"/>
                    </div>
                </td>

            </tr>
            <tr>
                <th style="width:85px">名称：<span class="required">*</span></th>
                <td>
                    <input class="form-control" name="name" id="role_name" dataType="Require"/>
                </td>
            </tr>
            <tr>
                <th>备注：</th>
                <td colspan="3"><textarea class="form-control" name="description" id="role_description"></textarea></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>