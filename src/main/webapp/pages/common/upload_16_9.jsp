<!-- 上传图片按16:9比例裁剪 -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link  href="../../jquery.imgareaselect-0.9.10/css/imgareaselect-default.css" rel="stylesheet"/>
<script src="../../jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.pack.js"   type="text/javascript"></script>
<script src="../../jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.min.js" type="text/javascript"></script>
<form  id="select_photo_file" enctype="multipart/form-data" method="post">
    <table class="table table-thead-box">
        <tbody>
        <tr>
        	<td >
            	<div class="position form-group">
					<input type="file" id="file_photo_upload" name="file" style="display: none;" />
                    <input type="hidden" name="fileName" id="upload_fileName"/>
                    <input type="hidden" name="originalImgName" id="originalImgName"/>
                    <input type="hidden" name="startX" value="0" />
                    <input type="hidden" name="startY" value="0" />
                    <input type="hidden" name="endX" value="320" />
                    <input type="hidden" name="endY" value="180" />
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="showUploadPhotos" id='preview' style="text-align: center">
    </div>
</form>