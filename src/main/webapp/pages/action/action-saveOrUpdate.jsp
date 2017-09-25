<%--
  Created by IntelliJ IDEA.
  User: kejian
  Date: 2017/5/21
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="padd">
    <form id="action_box">
        <table class="table table-thead-box">
            <tbody>
            <tr>
                <th style="width:85px">名称：<span class="required">*</span></th>
                <td>
                    <div class="position form-group">
                        <input class="form-control" name="name" id="action_name" dataType="Require"/>
                        <input type="hidden" name="id" id="action_id"/>

                    </div>
                </td>

            </tr>
            <tr>
                <th style="width:85px">url：</th>
                <td>
                    <div class="position form-group">
                        <input class="form-control" name="url" id="action_url" />

                    </div>
                </td>
            </tr>
            
            <tr>
                <th style="width:85px">菜单类型：</th>
                <td>
                    <div class="position form-group" onchange="actionTypeChange();" >
                        <select id="action_type"  name="type"  >
                        <option value="0" >--请选择--</option>
                     	<option value="1" >一级菜单</option>
                     	<option value="2" >二级菜单</option>
                     	<option value="3" >三级菜单</option>
                     	<option value="4" >四级菜单</option>
                     	<option value="5" >五级菜单</option>
                        </select>
                    </div>
                </td>
            </tr>
            
             <tr>
                <th style="width:85px">父菜单：</th>
                <td>
                    <div class="position form-group" >
                        <select id="action_parent"  name="parent">
                        <option value="0" >请选择</option>
                        </select>
                    </div>
                </td>
            </tr>
            
            <tr>
                <th style="width:85px">菜单序号：</th>
                <td>
                    <div class="position form-group" >
                         <input class="form-control" name="seqnum" id="action_seqnum" onblur="checkNum(this.value);" dataType="Number"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th>备注：</th>
                <td colspan="3"><textarea class="form-control" name="description" id="action_description"></textarea></td>
            </tr>
            <tr>
                <th style="width:85px">控制角色：</th>
                <td>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
    	<div id="role_relation" class="position form-group">
                	
         </div>
</div>