window.hellogood.action = function() {
	var pageData = page.pageParams;
	var listUrl = 'action/pageJson.do';
	var _from = $("#actionDataForm");
	var isSubmit = true;
	var windowWidth = 500;
	var windowHeight = 500;
	var hasCheckbox = true;
	var actionGrid = null;
	var initGrid = function() {
		var columns = [ {
			name : '名称',
			renderer : function(data) {
				return '<a id="view">' + data.name + '</a>';
			},
			rendererCall : function(obj) {
				if ("view" === obj.id) {
					showActionInfo(obj.data.id);
				}
			}
		}, {
			name : '菜单类型',
			dataIndex : 'typeName',
		}, {
			name : 'url',
			dataIndex : 'url'
		}, {
			name : '菜单序号',
			dataIndex : 'seqnum'
		}, {
			name : '所属角色',
			width : 200,
			align : 'left',
			renderer : function(data) {
				if (data.role != null) {
					var str = "";
					var tem = 0;
					for ( var index in data.role) {
						if (tem != 0) {
							str += ",";
						}
						str += data.role[index];
						tem++;
					}
					return str;
				}
				return "";
			}

		}, {
			name : '操作',
			renderer : function(data) {
				return '<a id="update">修改</a>';
			},
			rendererCall : function(obj) {
				updateAction(obj.data.id);
			}
		} ];

		actionGrid = grid({
			renderTo : 'action_List_grid',
			prefix : 'action',
			checkboxs : {
				checkbox : hasCheckbox,
				checkAll : hasCheckbox,
				dataIndex : 'id'
			},
			columns : columns
		});
		actionGrid.init();
	};

	var pageTool = page({
		renderTo : "action_list_pagetool",
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
				actionGrid.addDatas({
					rowDatas : json.dataList
				});
				pageTool.render(json.total, page);
			}
		});
	};

	var uiInit = function() {
		initGrid();
		pageTool.init();
	};

	var showActionInfo = function(id) {
		var viewActionMsgBox = msgBox({
			title : '查看菜单信息',
			width : windowWidth,
			height : windowHeight,
			url : "action/list/action-saveOrUpdate.do"
		});
		viewActionMsgBox.show();
		getActionInfo(id);
		// 查看禁用下拉列表与文本框
		util.disabledElement($("#action_box"));
	};
	
	var getActionInfo = function(id) {
		util.hellogoodAjax({
			url : "action/get/" + id + ".do",
			succFun : function(json) {
				if (json.errorMsg) {
					$.Prompt(json.errorMsg);
					return;
				}
				if (json.dataList) {
					$("#action_parent").empty();
					$("#action_parent").prepend(
							"<option value='0'>请选择</option>");
					var domMsgs = [ {
						domId : 'action_parent',
						jsonObj : 'dataList'
					} ];
					util.selectPadData(domMsgs, json);
				}

				for ( var index in json.data) {
					if (index == "parent") {
						$('#action_parent option').each(function() {
							if ($(this).val() == json.data[index]) {
								$(this).attr("selected", "selected");
							}
						});
					} else if (index == "type") {
						$('#action_type option').each(function() {
							if ($(this).val() == json.data[index]) {
								$(this).attr("selected", "selected");
							}
						});
					} else {
						$("#action_" + index).val(json.data[index]);
					}
				}

				if (json.roleList != null) {
					var str = "<table>";
					for ( var index in json.roleList) {

						str += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
								+ json.roleList[index].name + "</td></tr>";
					}
					str += "</table>";
					$("#role_relation").html(str)
				}
			}
		});

	}

	var addAction = function() {
		var addActionMsgBox = msgBox({
			title : '新增基础数据',
			width : windowWidth,
			height : windowHeight,
			url : "action/list/action-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#action_box")[0], 3))
					return;
				if (!isSubmit) {
					return;
				}
				isSubmit = false;
				var str = "";
				$("input[name='selects']:checked").each(function() {
					if (str == '')
						str += this.value;
					else
						str += "," + this.value;
				});
				var _data = $.extend(util.serializeJson($("#action_box")), {
					'roleIds' : str
				});
				util.hellogoodAjax({
					url : 'action/add.do',
					data : _data,
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json != null && json.status == 'success') {
							$.Prompt("新增成功！");
							addActionMsgBox.close();
							loadData(pageData.page, pageData.pageSize);// 重新加载
						}
					},
					complete : function() {
						isSubmit = true;
					}
				});
			}
		});
		addActionMsgBox.show();
		getAllRole();
	};

	var getAllRole = function() {
		util
				.hellogoodAjax({
					url : 'role/getAll.do',
					async : false,
					succFun : function(json) {

						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json == null || json.allroleList == null) {
							$.Prompt("获取角色信息失败！");
							return;
						}
						var str = "<table>"
						for ( var index in json.allroleList) {

							str += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkBox' name='selects' value='"
									+ json.allroleList[index].id
									+ "'/></td><td>"
									+ json.allroleList[index].name
									+ "</td></tr>"

						}
						str += "</table>";
						$("#role_relation").html(str);
					}
				});
	}

	var updateAction = function(id) {
		var updateActionMsgBox = msgBox({
			title : '修改菜单信息',
			width : windowWidth,
			height : windowHeight,
			url : "action/list/action-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#action_box")[0], 3))
					return;
				var str = "";
				$("input[name='selects']:checked").each(function() {
					if (str == '')
						str += this.value;
					else
						str += "," + this.value;
				});
				var _data = $.extend(util.serializeJson($("#action_box")), {
					'roleIds' : str
				});
				util.hellogoodAjax({
					url : 'action/update.do',
					data : _data,
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json.status == 'success') {
							$.Prompt("修改成功！");
							updateActionMsgBox.close();
							loadData(pageData.page, pageData.pageSize);// 重新加载
						}
					}
				});
			}
		});
		updateActionMsgBox.show();
		getAction(id);
	};

	var getAction = function(id) {
		util
				.hellogoodAjax({
					url : "action/get/" + id + ".do",
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json != null && json.dataList) {
							var domMsgs = [ {
								domId : 'action_parent',
								jsonObj : 'dataList'
							} ];
							util.selectPadData(domMsgs, json);
						}
						for ( var index in json.data) {
							if (index == "parent") {
								$('#action_parent option').each(function() {
									if ($(this).val() == json.data[index]) {
										$(this).attr("selected", "selected");
									}
								});
							} else if (index == "type") {
								$('#action_type option').each(function() {
									if ($(this).val() == json.data[index]) {
										$(this).attr("selected", "selected");
									}
								});
							} else {
								$("#action_" + index).val(json.data[index]);
							}
						}
						if (json.allRoleList != null) {
							var str = "<center><table>";
							for ( var index in json.allRoleList) {
								if (json.roleList != null) {
									var flag = true;
									for ( var index1 in json.roleList) {
										if (json.allRoleList[index].id == json.roleList[index1].id) {
											str += "<tr><td><input type='checkBox' name='selects' value='"
													+ json.allRoleList[index].id
													+ "'  checked=true/></td><td>"
													+ json.allRoleList[index].name
													+ "</td></tr>";
											flag = false;
										}
									}
									if (flag) {
										str += "<tr><td><input type='checkBox' name='selects' value='"
												+ json.allRoleList[index].id
												+ "'/></td><td>"
												+ json.allRoleList[index].name
												+ "</td></tr>";
									}
								} else {
									str += "<tr><td><input type='checkBox' name='selects' value='"
											+ json.allRoleList[index].id
											+ "'/></td><td>"
											+ json.allRoleList[index].name
											+ "</td></tr>";
								}
							}
							str += "</table></center>";
							$("#role_relation").html(str);
						}

					}
				});
	};

	var removeAction = function() {
		var ids = actionGrid.getselecValues();
		if (ids == null || ids.length == 0) {
			$.Prompt("请选择需要删除的行! ");
			return;
		}
		ui.dialogBox({
			html : "确定删除选中的行？",
			okHandle : function() {
				util.hellogoodAjax({
					url : "action/delete/" + ids + ".do",
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json.status == "success") {
							$.Prompt("删除成功！");
							loadData(pageData.page, pageData.pageSize);
						}
					}
				});

			}
		});
	};

	var bindEvent = function() {
		// 查询
		$('#action_list_select').click(function() {
			pageData.page = 1;
			loadData(pageData.page, pageData.pageSize);
		});
		// 新增
		$('#action_add').click(function() {
			addAction();
		});
		// 删除
		$('#action_del').click(function() {
			removeAction();
		});

	};

	var init = function() {
		uiInit();
		bindEvent();
	};

	return {
		init : init,
		load : loadData
	};
};

function actionTypeChange() {
	var actionType = $("#action_type").val();
	util.hellogoodAjax({
		url : 'action/getAllByType/' + actionType + '.do',
		async : false,
		succFun : function(json) {
			if (json.errorMsg) {
				$.Prompt(json.errorMsg);
				return;
			}
			if (json != null && json.dataList) {
				$("#action_parent").empty();
				$("#action_parent").prepend("<option value='0'>请选择</option>");
				var domMsgs = [ {
					domId : 'action_parent',
					jsonObj : 'dataList'
				} ];
				util.selectPadData(domMsgs, json);

			}

		}
	});
}

function checkNum(num) {
	var regex = /^[1-9][0-9]*/;
	if (num == '') {
		return;
	}
	if (!num.match(regex)) {
		$.Prompt("必须是以非0开头的数字");
		$("#action_seqnum").val("")
	}
}
