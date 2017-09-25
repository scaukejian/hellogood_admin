<%@ page contentType="text/html;charset=UTF-8" language="java" %>
 <script src="baseData-list.js?${ts}"></script>

<div class="padd">
    <form id="baseData_box" method="post">
        <table class="table table-thead-box">
            <tbody>
            <tr>
                <th style="width:85px">类型：<span class="required">*</span></th>
                <td>
                    <div class="position form-group">
                       <select id="baseData_type" name="type" class="form-control">
                            	<option value="">请选择</option>
                           </select>
                        <input type="hidden" name="id" id="baseData_id"/>
 						<input type="hidden" name="status" id="baseData_status"/>
                    </div>
                </td>
                </tr>
                <tr>
                <th style="width:85px">编号：<span class="required">*</span></th>
                <td>
                    <div class="position form-group">
                        <input class="form-control" name="code" id="baseData_code" dataType="Require"/>

                    </div>
                </td>
            </tr>
            
            <tr>
                <th style="width:85px">名称：<span class="required">*</span></th>
                <td>
                    <div class="position form-group">
                        <textarea class="form-control" name="name" id="baseData_name" dataType="Require"></textarea>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>