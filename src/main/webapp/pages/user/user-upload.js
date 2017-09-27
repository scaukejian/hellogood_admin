/**
 * 裁剪图上传
 * */
function photoUpload() {
    var updatePhotoBox = msgBox({
        title: '上传图片',
        width: 550,
        height: 420,
        url: 'user/list/user-uploadPhoto.do',
        Btn: true,
        middleBtn: true,
        okHandle: function () {
            var _data = util.serializeJson($('#select_photo_file'));
            util.hellogoodAjax({
                url: 'photo/uploadSelectPhoto.do',
                data: _data,
                succFun: function (json) {
                    if (json.errorMsg) {
                        $.Prompt(json.errorMsg);
                        return;
                    }
                    var photoName = json.data.imgName;
                    var pId = json.data.id;
                    $("#headPhotoId").val(json.data.id);
                    $("#profilePictureDiv").html("<div id='plist" + pId + "' class='image-set' style='float: left;'>" +
                        "<a class='example-image-link' data-lightbox='example-set-photo'  href='../../user/download.do?fileName=" +
                        photoName + "' ><img class='example-image' height='80px' width=80px' src='../../user/download.do?fileName=" +
                        photoName + "'/> </a><div style='display: inline;margin-left: 5px;'><a onclick='deletePhoto(" + pId + ");' style='font-size: 12px'>删除</a>&nbsp;&nbsp;</div></div>");
                    $('#imghead').imgAreaSelect({
                        remove: true
                    });
                    updatePhotoBox.close();
                }
            });
        }
    });
    updatePhotoBox.show();
    tempFileUpload();
}


/**
 * 临时图片上传
 */
var tempFileUpload = function () {
    var a = document.createEvent('MouseEvents');// FF的处理
    a.initEvent('click', true, true);
    document.getElementById('photo_upload').dispatchEvent(a);
    $('#photo_upload').change(function () {
        if ($("#photo_upload").val() == "") {
            $.Prompt("请选择文件");
            return;
        }
        $.ajaxFileUpload({
            fileElementId: 'photo_upload',
            url: rootPath + '/photo/uploadTmpPhoto.do',
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
                var imgName = json.fileName;
                $('#upload_userId').val($('#user_id').val());
                $("#upload_fileName").val(imgName);
                $("#select_file_photo_div").html('');
                var _html = "<img id='imghead' src='../../photo/selectPhotoDownload.do?fileName=" + imgName + "' style='height:300px;width:auto;'/>"
                $(".showUploadPhotos").html(_html);
                $('#imghead').imgAreaSelect({
                    // maxWidth: 400, maxHeight:400,
                    minWidth: 0, minHeight: 0,
                    x1: 0, y1: 0, x2: 200, y2: 200,
                    aspectRatio: '1:1',
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
 * 删除相册照片
 * @param id
 */
function deletePhoto(id) {
    if (!id) {
        return;
    }
    ui.dialogBox({
        html: "确定删除照片？", okHandle: function () {
            util.hellogoodAjax({
                url: '/photo/deletePhoto.do?id=' + id,
                succFun: function (json) {
                    if (json.errorMsg) {
                        $.Prompt(json.errorMsg);
                        return;
                    }
                    if (json.status == "success") {
                        $.Prompt("删除成功！");
                        $('#profilePictureDiv').html(' <input type="file" id="photo_upload" name="file" style="display: none;"/> ' +
                            '<div class="position form-group showPhotos" id="photos_show"> ' +
                            '<div onclick="photoUpload()" style="float: left; width: 55px; height: 55px; border: 1px solid #000; text-align: center; ' +
                            'cursor: pointer; line-height: 55px;margin: 5 15 0 20;"> ' +
                            '<font size="12px">+</font> </div></div>');
                    }
                }
            });
        }
    });
}
