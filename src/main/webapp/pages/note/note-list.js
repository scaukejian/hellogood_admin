window.hellogood.note = function() {
	var pageData = page.pageParams;
	var listUrl = 'note/pageJson.do';
	var _from = $("#noteDataForm");
    var user=new window.hellogood.user();
	var isSubmit = true;
	var windowWidth = 650;
	var windowHeight = 550;
	var hasCheckbox = true;
	var _grid = null;
	var columns = [
		{
			name : '姓名',
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
            name : '终端唯一码',
            dataIndex : 'phoneUniqueCode'
        },
        {
            name : '类型',
			width: '70px',
            renderer : function(data) {
                if(data.type != null && data.type != ''){
                    return data.type + '计划';
                }
                return '';
            }
        },
        {
            name : "内容",
            width:'100px',
            renderer : function(data) {
                var content = '';
                var showContent = '';
                if (data.content != null) {
                    content = util.removeHTMLTag(data.content);
                    if (content.length > 30) {
                        showContent = content.substr(0, 30).concat('...');
                    } else {
                        showContent = content;
					}
                }
                return "<a title='"+content+"'>"+showContent+"</a>";
            },
            rendererCall : function(obj) {
				showNoteInfo(obj.data.id);
            }
        },
        {
            name : '颜色',
            dataIndex : 'color'
        },
		{
			name : '创建时间',
			renderer : function(data) {
				if (data.createTime != null) {
					return util.date.toDateAll(data.createTime);
				}
				return '';
			}
		},
        {
            name : '是否置顶/收藏',
            renderer : function(data) {
                if (data.top == 1) {
                    return "<a>是</a>";
                } else {
                    return '否';
                }
            }
        },
        {
            name : '是否完成',
            renderer : function(data) {
                if (data.finish == 1) {
                    return "<a>已完成<a>";
                } else if (data.finish == 0) {
                    return "未完成";
                } else {
                    return '';
                }
            }
        },
        {
            name : '是否展示',
            renderer : function(data) {
                if (data.display == 1) {
                    return "<a>展示<a>";
                } else {
                    return '隐藏（回收站）';
                }
            }
        },
        {
            name : '是否有效',
            renderer : function(data) {
                if (data.validStatus == 1) {
                    return "<a>有效<a>";
                } else {
                    return '无效';
                }
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
            updateNote(obj.data.id);
		}
	};
    columns.push(operateCol);
	_grid = grid({
			renderTo : 'note_List_grid',
			prefix : 'note',
			checkboxs : {
				checkbox : hasCheckbox,
				checkAll : hasCheckbox,
				dataIndex : 'id'
			},
			columns : columns
		});

	var pageTool = page({
		renderTo : "note_list_pagetool",
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

	window.hellogood.noteLoad = loadData;

	var uiInit = function() {
		_grid.init();
		pageTool.init();
	};

    var showUserInfo=function(id){
        user.showUserInfo(id);
    };

	var showNoteInfo = function(id) {
		var viewNoteMsgBox = msgBox({
			title : '计划详情',
			width : windowWidth,
			height : windowHeight,
			url : "note/list/note-saveOrUpdate.do",
			Btn: true,
			middleBtn: true,
			btnName: [ '关闭' ],
			okHandle: function () {
				viewNoteMsgBox.close();
			}

		});
		viewNoteMsgBox.show();
		window.hellogood.updateNoteMsgBox = viewNoteMsgBox;
		getNote(id);
		// 查看禁用下拉列表与文本框
		$('form[id^="noteProfileFrom"]').each(function() {
			util.setReadonlyElement($(this));
		});
	};

	var addNote = function() {
		var addNoteMsgBox = msgBox({
			title : '新增计划',
			width : windowWidth,
			height : windowHeight,
			url : "note/list/note-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#noteProfileFrom")[0], 3)) return;
				var uObj = util.serializeJson($("#noteProfileFrom"));
                var content = CKEDITOR.instances.content.getData();
                uObj.content = content;
				util.hellogoodAjax({
					url : 'note/add.do',
					data : uObj,
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json != null && json.status == 'success') {
							$.Prompt("新增成功！");
							$("input[name^='noteId']").val(json.data);
                            addNoteMsgBox.close();
                            setTimeout(function () {
                                loadData(pageData.page, pageData.pageSize);
                            }, 1000);
						}
					}
				});
			}
		});
		addNoteMsgBox.show();
        CKEDITOR.replace("content");
	};

	var showUpdateNoteBox = function(id){
		var updateNoteMsgBox = msgBox({
			title : '修改计划信息',
			width : windowWidth,
			height : windowHeight,
			url : "note/list/note-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
            btnName : [ "确定", "取消" ],
            okHandle : function() {
                if (!Validator.Validate($("#noteProfileFrom")[0], 3)) return;
                var uObj = util.serializeJson($("#noteProfileFrom"));
                uObj.content = CKEDITOR.instances.content.getData();
                util.hellogoodAjax({
                    url : 'note/update.do',
                    data : uObj,
                    succFun : function(json) {
                        if (json.errorMsg) {
                            $.Prompt(json.errorMsg);
                            return;
                        }
                        if (json.status == 'success') {
                            $.Prompt("修改成功！");
                            updateNoteMsgBox.close();
                            setTimeout(function () {
                                loadData(pageData.page, pageData.pageSize);
                            }, 1000);
                        }
                    }
                });
			}
		});
		window.hellogood.updateNoteMsgBox = updateNoteMsgBox;
		updateNoteMsgBox.show();
		getNote(id);
       $('#note_userName').attr("readonly","readonly");
       $('#note_phoneUniqueCode').attr("readonly","readonly");
		return updateNoteMsgBox;
	}

	var updateNote = function(id) {
		var updateNoteMsgBox = showUpdateNoteBox(id);
	};

	var getNote = function(id) {
		util.hellogoodAjax({
			url: "note/get/" + id + ".do",
			succFun: function (json) {
				if (json.errorMsg) {
					$.Prompt(json.errorMsg);
					return;
				}
				// HTML编辑器设置data数据
                CKEDITOR.replace("content");
				if (util.isNotBlank(json.data.content)) {
					CKEDITOR.instances.content.setData(json.data.content)
				}
				for (var index in json.data) {
					if(json.data[index] == null){
						continue;
					}
					$("#note_" + index).val(json.data[index]);
					if (index == 'content') {
						$('#content').val(json.data[index]);
					}
				}
			}
		});
	};

	var bindEvent = function() {
		// 查询
		$('#note_list_select').click(function() {
			pageData.page = 1;
			loadData(pageData.page, pageData.pageSize);
		});
		// 新增
		$('#note_add').click(function() {
			addNote();
		});
		// 删除
		$('#note_del').click(function() {
            delNote();
		});
		// 停用
		$('#note_disabled').click(function() {
            setStatus(0);
		});
		// 启用
		$('#note_enabled').click(function() {
            setStatus(1);
		});
	};

    var delNote = function() {
        var ids = _grid.getselecValues();
        if (ids == null || ids.length == 0) {
            $.Prompt("请选择需要删除的记录! ");
            return;
        }
        ui.dialogBox({
            html : "确定删除选中的记录？",
            okHandle : function() {
                util.hellogoodAjax({
                    url : "note/delete/" + ids + ".do",
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

    var setStatus = function(status) {
        var statuMsg = status == 0 ? "停用" : "启用";
        var ids = _grid.getselecValues();
        if (ids == null || ids.length == 0) {
            $.Prompt("请选择需要"+statuMsg+"的记录! ");
            return;
        }
        ui.dialogBox({
            html : "确定"+ statuMsg +"选中的记录？",
            okHandle : function() {
                util.hellogoodAjax({
                    url : "note/setStatus/" + ids + "-"+status+".do",
                    succFun : function(json) {
                        if (json.errorMsg) {
                            $.Prompt(json.errorMsg);
                            return;
                        }
                        if (json.status == "success") {
                            $.Prompt("操作成功！");
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
		showNoteInfo : showNoteInfo,
		updateNoteInfo : updateNote
	};
};