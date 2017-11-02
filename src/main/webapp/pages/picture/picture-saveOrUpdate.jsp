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
    <form id="pictureProfileFrom">
        <table class="table table-thead-box">
            <tbody>
                <tr>
                    <input type="hidden" name="id" id="picture_id"/>
                    <th width="20%">图片类型：<span style="color: red">*</span></th>
                    <td width="70%"><input type="text" class="form-control" name="type" id="picture_type" datatype="Require"/></td>
                </tr>
                <tr>
                    <th width="20%">图片：</th>
                    <td width="70%">
                        <input type="file" style="display: none" name="file" id="picture_upload">
                        <input name="fileName" id="picture_fileName" type="hidden" />
                        <input name="originalFileName" id="picture_originalFileName" type="hidden" />
                        <div class="position form-group showPhotos" id="picture_show">
                            <div onclick="photosUpload('upload_1_1.do',200,200,'1:1');" style="float: left; width: 45px; height: 55px; border: 1px solid #000; text-align: center; cursor: pointer; line-height: 55px;margin: 5 15 0 20;">
                                <font size="12px">+</font>
                            </div>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<script src="../../js/common/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript">

    /**
     * 上传图片
     * url是裁剪参数页面
     * x2是裁剪长度
     * y2是裁剪宽度
     * aspectRatio是裁剪比例
     */
    function photosUpload(url, x2, y2, aspectRatio) {
        var updatePhotoBox = msgBox({
            title: '上传图片',
            width: 600,
            height: 450,
            url: 'picture/common/'+url,
            Btn: true,
            middleBtn: true,
            okHandle: function () {
                var _data = util.serializeJson($('#select_photo_file'));
                util.hellogoodAjax({
                    url: 'picture/uploadPhoto-'+$("#picture_id").val()+'.do',
                    data: _data,
                    succFun: function (json) {
                        if (json.errorMsg) {
                            $.Prompt(json.errorMsg);
                            return;
                        }
                        var pictureId = json.pictureId;
                        var imgName = json.imgName;
                        var originalImgName = json.originalImgName;
                        $('#picture_id').val(pictureId);
                        $('#picture_fileName').val(imgName);
                        $('#picture_originalFileName').val(originalImgName);
                        var photoText;
                        //当上传类型是分享图时
                        var temp = "updatePhotosUpload('"+url+"',"+x2+","+y2+",'"+aspectRatio+"',"+pictureId+")";
                        var deleteTemp = "removeShareImageById("+pictureId+")";
                        photoText = "<div><span class='phototText'><a onclick="+deleteTemp+"><font size='2'>删除</font></a></span>&nbsp;&nbsp;&nbsp;&nbsp;<span class='phototText'><a onclick="+temp+"><font size='1'>修改</font></a></span></div>";
                        var _div = "<div id='plist_" + pictureId
                            + "' class='image-set' style='float: left;margin : 0px 0px 0px 10px;'>"
                            + "<a class='example-image-link' data-lightbox='example-set-photo'  href='../../app/download.do?fileName="
                            + imgName
                            + "' ><img class='example-image' height='300px' width='300px' style='margin : 0px' src='../../app/download.do?fileName="
                            + imgName + "'/>"
                            + "</a>" + photoText
                            + '</div>';
                        $('#picture_show').html(_div);
                        $('#imghead').imgAreaSelect({
                            remove: true
                        });
                        updatePhotoBox.close();
                    }
                });
            }
        });
        updatePhotoBox.show();
        tempFileUpload(x2, y2, aspectRatio);
    }

    /**
     * 临时图片上传
     */
    var tempFileUpload = function (x2, y2, aspectRatio) {
        var a = document.createEvent('MouseEvents');// FF的处理
        a.initEvent('click', true, true);
        document.getElementById('file_photo_upload').dispatchEvent(a);
        $('#file_photo_upload').change(function () {
            if ($("#file_photo_upload").val() == "") {
                $.Prompt("请选择文件");
                return;
            }
            $.ajaxFileUpload({
                fileElementId: 'file_photo_upload',
                url: rootPath + '/picture/uploadTmpPhoto.do',
                dataType: 'json',
                complete: function (XMLHttpRequest, textStatus) {
                    var dataStr = XMLHttpRequest.responseText.substring(59,
                        (XMLHttpRequest.responseText.length - 6));
                    var json = $.parseJSON(dataStr);
                    console.log(' json -- >');
                    console.log(json);
                    if (json.errorMsg) {
                        $.Prompt(json.errorMsg);
                        return;
                    }
                    if (util.isBlank(json.fileName)) return;
                    $("#upload_fileName").val(json.fileName);
                    $("#originalImgName").val(json.originalImgName);
                    var _html = "<img id='imghead' src='../../app/download.do?fileName=" + json.fileName + "' style='height:300px;width:auto;'/>"
                    $(".showUploadPhotos").html(_html);
                    $('#imghead').imgAreaSelect({
                        minWidth: 0, minHeight: 0,
                        x1: 0, y1: 0, x2: x2, y2: y2,
                        aspectRatio: aspectRatio,
                        onSelectEnd: function (img, selection) {
                            $('input[name="startX"]').val(selection.x1);
                            $('input[name="startY"]').val(selection.y1);
                            $('input[name="endX"]').val(selection.x2);
                            $('input[name="endY"]').val(selection.y2);
                        }
                    });
                }
            });
        });
    }

    /**
     * 修改上传图片
     * url是裁剪参数页面
     * x2是裁剪长度
     * y2是裁剪宽度
     * aspectRatio是裁剪比例
     */
    function updatePhotosUpload(url, x2, y2, aspectRatio, pictureId) {
        var updatePhotoBox = msgBox({
            title: '上传图片',
            width: 600,
            height: 450,
            url: 'picture/common/'+url,
            Btn: true,
            middleBtn: true,
            okHandle: function () {
                var _data = util.serializeJson($('#select_photo_file'));
                util.hellogoodAjax({
                    url: 'picture/updatePhoto-'+pictureId+'.do',
                    data: _data,
                    succFun: function (json) {
                        if (json.errorMsg) {
                            $.Prompt(json.errorMsg);
                            return;
                        }
                        var pictureId = json.pictureId;
                        var imgName = json.imgName;
                        var originalImgName = json.originalImgName;
                        $('#picture_id').val(pictureId);
                        $('#picture_fileName').val(imgName);
                        $('#picture_originalFileName').val(originalImgName);
                        if (util.isNotBlank(imgName)) {
                            var temp = "updatePhotosUpload('"+url+"',"+x2+","+y2+",'"+aspectRatio+"',"+pictureId+")";
                            var deleteTemp = "removeShareImageById("+pictureId+")";
                            var photoText = '';
                            photoText = "<div><span class='phototText'><a onclick="+deleteTemp+"><font size='2'>删除</font></a></span>&nbsp;&nbsp;&nbsp;&nbsp;<span class='phototText'><a onclick="+temp+"><font size='1'>修改</font></a></span></div>";
                            var _div = "<div id='plist_"+ pictureId
                                + "' class='image-set' style='float: left;margin : 0px 0px 0px 10px;'>"
                                + "<a class='example-image-link' data-lightbox='example-set-photo'  href='../../app/download.do?fileName="
                                + imgName
                                + "' ><img class='example-image' height='300px' width='300px' style='margin : 0px' src='../../app/download.do?fileName="
                                + imgName + "'/>"
                                + "</a>"
                                + photoText + "</div>";
                            $('#picture_show').html(_div);//当上传类型是详情图时
                        }
                        $('#imghead').imgAreaSelect({
                            remove: true
                        });
                        updatePhotoBox.close();
                    }
                });
            }
        });
        updatePhotoBox.show();
        tempFileUpload(x2, y2, aspectRatio);
    }


    //删除图片
    function removeImageByImgId(pictureId) {
        if(pictureId == null) return;
        //删除图片数据
        util.hellogoodAjax({
            url : 'picture/deletePhoto-'+ pictureId + '.do',
            succFun : function (json) {
                if(json.errorMsg){
                    $.Prompt(json.errorMsg);
                    return;
                }
                $.Prompt('删除成功');
            }
        });
    }
    //删图
    function removeShareImageById(pictureId) {
        removeImageByImgId(pictureId); //删除图片数据
        removeShareImage();//s
    }
    //删除图片节点并初始化上传按钮
    function removeShareImage() {
        $('#picture_upload').val('');
        $('#picture_fileName').val('');
        $('#picture_originalFileName').val('');
        var temp = "photosUpload('upload_1_1.do',200,200,'1:1')";
        var content = '<div onclick="'+temp+'" style="float: left; width: 45px; height: 55px; border: 1px solid #000; text-align: center; cursor: pointer; line-height: 55px;margin: 5 15 0 20;">'
            + '<font size="12px">+</font></div>';
        $('#picture_show').html(content);
    }
</script>
