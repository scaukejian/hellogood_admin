window.hellogood.folder = function() {
	var pageData = page.pageParams;
	var listUrl = 'folder/pageJson.do';
	var _from = $("#folderDataForm");
    var user=new window.hellogood.user();
	var isSubmit = true;
	var windowWidth = 650;
	var windowHeight = 550;
	var hasCheckbox = true;
	var _grid = null;
	var columns = [
        {
            name : '文件夹名称',
            dataIndex : 'name'
        },
        {
            name : '是否系统文件夹',
            renderer : function(data) {
                if (data.systemFolder == 1) {
                    return "<a>是</a>";
                } else {
                    return '否';
                }
            }
        },
		{
			name : '所属用户',
			width:'200px',
			dataIndex : 'userName',
			renderer : function(data) {
				if(data.userName != null && data.userName != ''){
					return '<a id="view">' + data.userName + '(' + data.userCode + ')</a>';
				}
				return '';
			},
			rendererCall : function(obj) {
				if ("view" === obj.id) {
                    showUserInfo(obj.data.userId);
				}
			}
		},
		{
			name : '手机号',
			dataIndex : 'phone'
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
            updateFolder(obj.data.id);
		}
	};
    columns.push(operateCol);
	_grid = grid({
			renderTo : 'folder_List_grid',
			prefix : 'folder',
			checkboxs : {
				checkbox : hasCheckbox,
				checkAll : hasCheckbox,
				dataIndex : 'id'
			},
			columns : columns
		});

	var pageTool = page({
		renderTo : "folder_list_pagetool",
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

	window.hellogood.folderLoad = loadData;

	var uiInit = function() {
		_grid.init();
		pageTool.init();
	};

    var showUserInfo=function(id){
        user.showUserInfo(id);
    };

	var showFolderInfo = function(id) {
		var viewFolderMsgBox = msgBox({
			title : '文件夹详情',
			width : windowWidth,
			height : windowHeight,
			url : "folder/list/folder-saveOrUpdate.do",
			Btn: true,
			middleBtn: true,
			btnName: [ '关闭' ],
			okHandle: function () {
				viewFolderMsgBox.close();
			}

		});
		viewFolderMsgBox.show();
		window.hellogood.updateFolderMsgBox = viewFolderMsgBox;
		getFolder(id);
		// 查看禁用下拉列表与文本框
		$('form[id^="folderProfileFrom"]').each(function() {
			util.setReadonlyElement($(this));
		});
	};

	var addFolder = function() {
		var addFolderMsgBox = msgBox({
			title : '新增文件夹',
			width : windowWidth,
			height : windowHeight,
			url : "folder/list/folder-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#folderProfileFrom")[0], 3)) return;
				var uObj = util.serializeJson($("#folderProfileFrom"));
				util.hellogoodAjax({
					url : 'folder/add.do',
					data : uObj,
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json != null && json.status == 'success') {
							$.Prompt("新增成功！");
							$("input[name^='folderId']").val(json.data);
                            addFolderMsgBox.close();
                            setTimeout(function () {
                                loadData(pageData.page, pageData.pageSize);
                            }, 300);
						}
					}
				});
			}
		});
		addFolderMsgBox.show();
	};

	var showUpdateFolderBox = function(id){
		var updateFolderMsgBox = msgBox({
			title : '修改文件夹信息',
			width : windowWidth,
			height : windowHeight,
			url : "folder/list/folder-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
            btnName : [ "确定", "取消" ],
            okHandle : function() {
                if (!Validator.Validate($("#folderProfileFrom")[0], 3)) return;
                var uObj = util.serializeJson($("#folderProfileFrom"));
                util.hellogoodAjax({
                    url : 'folder/update.do',
                    data : uObj,
                    succFun : function(json) {
                        if (json.errorMsg) {
                            $.Prompt(json.errorMsg);
                            return;
                        }
                        if (json.status == 'success') {
                            $.Prompt("修改成功！");
                            updateFolderMsgBox.close();
                            setTimeout(function () {
                                loadData(pageData.page, pageData.pageSize);
                            }, 300);
                        }
                    }
                });
			}
		});
		window.hellogood.updateFolderMsgBox = updateFolderMsgBox;
		updateFolderMsgBox.show();
		getFolder(id);
       $('#folder_userName').attr("disabled","disabled");
       $('#folder_systemFolder').attr("disabled","disabled");
		return updateFolderMsgBox;
	}

	var updateFolder = function(id) {
		var updateFolderMsgBox = showUpdateFolderBox(id);
	};

	var getFolder = function(id) {
		util.hellogoodAjax({
			url: "folder/get/" + id + ".do",
			succFun: function (json) {
				if (json.errorMsg) {
					$.Prompt(json.errorMsg);
					return;
				}
				for (var index in json.data) {
					if(json.data[index] == null){
						continue;
					}
					$("#folder_" + index).val(json.data[index]);
				}
                if ($("#folder_systemFolder").val() == 0) {
                    $("#userTr").show();
                } else {
                    $("#userTr").hide();
                }
			}
		});
	};

	var bindEvent = function() {
		// 查询
		$('#folder_list_select').click(function() {
			pageData.page = 1;
			loadData(pageData.page, pageData.pageSize);
		});
		// 新增
		$('#folder_add').click(function() {
			addFolder();
		});
		// 删除
		$('#folder_del').click(function() {
            delFolder();
		});
	};

    var delFolder = function() {
        var ids = _grid.getselecValues();
        if (ids == null || ids.length == 0) {
            $.Prompt("请选择需要删除的记录! ");
            return;
        }
        ui.dialogBox({
            html : "确定删除选中的记录？",
            okHandle : function() {
                util.hellogoodAjax({
                    url : "folder/delete/" + ids + ".do",
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
		showFolderInfo : showFolderInfo,
		updateFolderInfo : updateFolder
	};
};