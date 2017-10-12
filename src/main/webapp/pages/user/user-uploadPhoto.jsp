<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link  href="../../jquery.imgareaselect-0.9.10/css/imgareaselect-default.css" rel="stylesheet"/>
<form  id="select_photo_file" enctype="multipart/form-data" method="post">
    <table class="table table-thead-box">
        <tbody>
        <tr>
            <th>
            </th>
            <td>
                <div class="position form-group">
                    <input type="hidden" name="fileName" id="upload_fileName"/>
                    <input type="hidden" name="userId" id="upload_userId"/>
                    <input type="hidden" name="startX" value="0" />
                    <input type="hidden" name="startY" value="0" />
                    <input type="hidden" name="endX" value="200" />
                    <input type="hidden" name="endY" value="200" />
                </div>
            </td>
            <td >
            </td>
        </tr>
        </tbody>
    </table>
    <div class="showUploadPhotos" id='preview' style="text-align: center">
    </div>
</form>
<script src="../../jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.pack.js"   type="text/javascript"></script>
<script src="../../jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.min.js" type="text/javascript"></script>
