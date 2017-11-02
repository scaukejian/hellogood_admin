window.hellogood.picture = function() {
	var pageData = page.pageParams;
	var listUrl = 'picture/pageJson.do';
	var _from = $("#pictureDataForm");
	var isSubmit = true;
	var windowWidth = 650;
	var windowHeight = 550;
	var hasCheckbox = true;
	var _grid = null;
	var columns = [
        {
            name : '图片类型',
            dataIndex : 'type'
        },
        {
            name : '图片',
            renderer : function(data){
                if(util.isBlank(data.fileName)) return '';
                var content = " <div id='picture_" + data.id + "' class='image-set' style='height:60px;'>";
                content += "<a class='example-image-link' data-lightbox='example-set-photo'  href='../../app/download.do?fileName="
                    + data.fileName + "' ><img class='example-image' height='60px' width='60px'  src='../../app/download.do?fileName="
                    + data.fileName + "'/> </a></div>";
                return content;
            }
        },
        {
            name : '图片名称',
            dataIndex : 'fileName'
        },
        {
            name : '图片原始名称',
            dataIndex : 'originalFileName'
        },
		{
			name : '创建时间',
			renderer : function(data) {
				if (data.createTime != null) {
					return util.date.toDateAll(data.createTime);
				}
				return '';
			}
		}
		];
	var operateCol = {
		name : '操作',
        width: '70px',
		align: "center",
		renderer : function(data) {
			var _html = '<a id="update"><font color="#099854"><b>修改</b></font></a>';
			return _html;
		},
		rendererCall : function(obj) {
            updatePicture(obj.data.id);
		}
	};
    columns.push(operateCol);
	_grid = grid({
			renderTo : 'picture_List_grid',
			prefix : 'picture',
			checkboxs : {
				checkbox : hasCheckbox,
				checkAll : hasCheckbox,
				dataIndex : 'id'
			},
			columns : columns
		});

	var pageTool = page({
		renderTo : "picture_list_pagetool",
		pageSize : pageData.pageSize,
		subHandler : function(data) {
			pageData.page = data.currNo;
			pageData.pageSize = data.pageSize;
			loadData(data.currNo, data.pageSize);
		}
	});

	var loadData = function(page, pageSize) {
		var _data = $.extend(util.serializeJson(_from), {
			'page' : page,
			'pageSize' : pageSize
		});
		util.hellogoodAjax({
			url : listUrl,
			data : _data,
			succFun : function(json) {
				if (json.errorMsg) {
					$.Prompt(json.errorMsg);
					return;
				}
				_grid.addDatas({
					rowDatas : json.dataList
				});
				pageTool.render(json.total, page);
				pageData.page = page;
			}
		});
	};

	window.hellogood.pictureLoad = loadData;

	var uiInit = function() {
		_grid.init();
		pageTool.init();
	};

	var showPictureInfo = function(id) {
		var viewPictureMsgBox = msgBox({
			title : '图片详情',
			width : windowWidth,
			height : windowHeight,
			url : "picture/list/picture-saveOrUpdate.do",
			Btn: true,
			middleBtn: true,
			btnName: [ '关闭' ],
			okHandle: function () {
				viewPictureMsgBox.close();
			}

		});
		viewPictureMsgBox.show();
		window.hellogood.updatePictureMsgBox = viewPictureMsgBox;
		getPicture(id);
		// 查看禁用下拉列表与文本框
		$('form[id^="pictureProfileFrom"]').each(function() {
			util.setReadonlyElement($(this));
		});
	};

	var addPicture = function() {
		var addPictureMsgBox = msgBox({
			title : '新增图片',
			width : windowWidth,
			height : windowHeight,
			url : "picture/list/picture-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#pictureProfileFrom")[0], 3)) return;
				var uObj = util.serializeJson($("#pictureProfileFrom"));
				util.hellogoodAjax({
					url : 'picture/add.do',
					data : uObj,
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json != null && json.status == 'success') {
							$.Prompt("新增成功！");
							$("input[name^='pictureId']").val(json.data);
                            addPictureMsgBox.close();
                            setTimeout(function () {
                                loadData(pageData.page, pageData.pageSize);
                            }, 300);
						}
					}
				});
			}
		});
		addPictureMsgBox.show();
	};

	var showUpdatePictureBox = function(id){
		var updatePictureMsgBox = msgBox({
			title : '修改图片信息',
			width : windowWidth,
			height : windowHeight,
			url : "picture/list/picture-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
            btnName : [ "确定", "取消" ],
            okHandle : function() {
                if (!Validator.Validate($("#pictureProfileFrom")[0], 3)) return;
                var uObj = util.serializeJson($("#pictureProfileFrom"));
                util.hellogoodAjax({
                    url : 'picture/update.do',
                    data : uObj,
                    succFun : function(json) {
                        if (json.errorMsg) {
                            $.Prompt(json.errorMsg);
                            return;
                        }
                        if (json.status == 'success') {
                            $.Prompt("修改成功！");
                            updatePictureMsgBox.close();
                            setTimeout(function () {
                                loadData(pageData.page, pageData.pageSize);
                            }, 300);
                        }
                    }
                });
			}
		});
		window.hellogood.updatePictureMsgBox = updatePictureMsgBox;
		updatePictureMsgBox.show();
		getPicture(id);
		return updatePictureMsgBox;
	}

	var updatePicture = function(id) {
		var updatePictureMsgBox = showUpdatePictureBox(id);
	};

	var getPicture = function(id) {
		util.hellogoodAjax({
			url: "picture/get/" + id + ".do",
			succFun: function (json) {
				if (json.errorMsg) {
					$.Prompt(json.errorMsg);
					return;
				}
				for (var index in json.data) {
					if(json.data[index] == null){
						continue;
					}
					$("#picture_" + index).val(json.data[index]);
				}
                var pictureId = json.data.id;
                var fileName = json.data.fileName;
                if (util.isNotBlank(fileName)) {//详情图
                    var temp = "updatePhotosUpload('upload_1_1.do',200,200,'1:1'," + pictureId + ")";
                    var deleteTemp = "removeShareImageById(" + pictureId + ")";
                    var photoText = "<div><span class='phototText'><a onclick=" + deleteTemp + "><font size='2'>删除</font></a></span>&nbsp;&nbsp;&nbsp;&nbsp;<span class='phototText'><a onclick=" + temp + "><font size='1'>修改</font></a></span></div>";
                    var _div = "<div id='plist_" + pictureId
                        + "' class='image-set' style='float: left;margin : 0px 0px 0px 10px;'>"
                        + "<a class='example-image-link' data-lightbox='example-set-photo'  href='../../app/download.do?fileName="
                        + fileName
                        + "' ><img class='example-image' height='300px' width='300px' style='margin : 0px' src='../../app/download.do?fileName="
                        + fileName + "'/>"
                        + "</a>" + photoText
                        + '</div>';
                    $('#picture_show').html(_div);
                }
                
			}
		});
	};

	var bindEvent = function() {
		// 查询
		$('#picture_list_select').click(function() {
			pageData.page = 1;
			loadData(pageData.page, pageData.pageSize);
		});
		// 新增
		$('#picture_add').click(function() {
			addPicture();
		});
		// 删除
		$('#picture_del').click(function() {
            delPicture();
		});
	};

    var delPicture = function() {
        var ids = _grid.getselecValues();
        if (ids == null || ids.length == 0) {
            $.Prompt("请选择需要删除的记录! ");
            return;
        }
        ui.dialogBox({
            html : "确定删除选中的记录？",
            okHandle : function() {
                util.hellogoodAjax({
                    url : "picture/delete/" + ids + ".do",
                    succFun : function(json) {
                        if (json.errorMsg) {
                            $.Prompt(json.errorMsg);
                            return;
                        }
                        if (json.status == "success") {
                            $.Prompt("删除成功！");
                            pageData.page = 1;
                            loadData(pageData.page, pageData.pageSize);
                        }
                    }
                });
            }
        });
    };

	var init = function() {
		uiInit();
		bindEvent();
	};

	return {
		init : init,
		load : loadData,
		showPictureInfo : showPictureInfo,
		updatePictureInfo : updatePicture
	};
};