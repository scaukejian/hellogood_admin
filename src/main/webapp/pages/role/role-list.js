window.hellogood.role = function() {
	var pageData = page.pageParams;
	var listUrl = 'role/pageJson.do';
	var _from = $("#roleDataForm");
	var isSubmit = true;
	var windowWidth = 400;
	var windowHeight = 330;
	var hasCheckbox = true;
	var roleGrid = null;
	var initGrid = function() {
		var columns = [ {
			name : '代码',
			renderer : function(data) {
				return '<a id="view">' + data.code + '</a>';
			},
			rendererCall : function(obj) {
				if ("view" === obj.id) {
					showRoleInfo(obj.data.id);
				}
			}
		}, {
			name : '名称',
			dataIndex : 'name'
		}, {
			name : '备注',
			dataIndex : 'description'
		}, {
			name : '操作',
			renderer : function(data) {
				return '<a id="update">修改</a>';
			},
			rendererCall : function(obj) {
				updateRole(obj.data.id);
			}
		}, {
			name : '功能菜单',
			renderer : function(data) {
				return '<a id="modify">功能菜单维护</a>';
			},
			rendererCall : function(obj) {
				modifyAction(obj.data.id);
			}
		} ];

		roleGrid = grid({
			renderTo : 'role_List_grid',
			prefix : 'role',
			checkboxs : {
				checkbox : hasCheckbox,
				checkAll : hasCheckbox,
				dataIndex : 'id'
			},
			columns : columns
		});
		roleGrid.init();
	};
	var modifyAction = function(id) {
		var modifyModifyActionMsgBox = msgBox({
			title : '菜单维护',
			width : 500,
			height : 600,
			url : "role/list/role-modifyAction.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#employee_box")[0], 3))
					return;
				// 获取权限选择json数据
				var str = "";
				$("input:checked").each(function() {
					if (this.value.indexOf('root') != -1) {
						this.value = this.value.substr(4, this.value.length);
					}
					if (str == '')
						str += this.value;
					else
						str += "," + this.value;
				});
				util.hellogoodAjax({
					url : 'role/modifyAction/' + str + '-' + id + '.do',
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json.status == 'success') {
							$.Prompt("操作成功！");
							modifyModifyActionMsgBox.close();
							loadData(pageData.page, pageData.pageSize);// 重新加载
						}
					}
				});
			}
		});
		modifyModifyActionMsgBox.show();
		getActions(id);

	};
	var getActions = function(id) {

		util
				.hellogoodAjax({
					url : "action/getAll/" + id + ".do",
					succFun : function(json) {
						if(json.errorMsg){
							$.Prompt(json.errorMsg);return;
						}
						if (json.dataList == null) {
							$.Prompt("获取菜单信息失败！");
							return;
						}
						if (json.dataList != null) {
							var str = "<script>"
									+ "	function selectBox(obj){"
									+ "  if(obj.value.indexOf('root')!=-1){var nodes=document.getElementsByName(obj.value);"
									+ "for(var i=0;i<nodes.length;i++){ nodes[i].checked=obj.checked;}}"
									+ "if(obj.checked==true){"
									+ "		var dom =document.getElementById(obj.name.substr(4,obj.name.length));"
									+ ""
									+ "dom.checked=true;}	"
									+ "				"
									+ "	}"
									+ "		$(function () {"
									+ "    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');"
									+ "    $('.tree li.parent_li > span').on('click', function (e) {"
									+ "       var children = $(this).parent('li.parent_li').find(' > ul > li');"
									+ "        if (children.is(':visible')) {"
									+ "           children.hide('fast');"
									+ "           $(this).attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');"
									+ "      } else {"
									+ "          children.show('fast');"
									+ "           $(this).attr('title', 'Collapse this branch').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');"
									+ "       }"
									+ "       e.stopPropagation();" + "   });"
									+ "});	" + "</script>";
							for ( var index in json.dataList) {
								if (json.actionIds != null) {
									var flag = true;
									// 一级菜单
									for ( var index11 in json.actionIds) {
										if (json.actionIds[index11] == json.dataList[index].id) {
											flag = false;
											str += "<li><input type='checkBox' id='"
													+ json.dataList[index].id
													+ "'  checked='checked' onclick='selectBox(this);' value='root"
													+ json.dataList[index].id
													+ "'/><span><i class='icon-folder-open'></i>"
													+ json.dataList[index].name
													+ "</span>";
											break;
										}
									}
									if (flag) {
										str += "<li><input type='checkBox' id='"
												+ json.dataList[index].id
												+ "'  onclick='selectBox(this);' value='root"
												+ json.dataList[index].id
												+ "'/><span><i class='icon-folder-open'></i>"
												+ json.dataList[index].name
												+ "</span>";
									}
									if (json.allSonMap != null) {
										for ( var s in json.allSonMap) {
											if (s == json.dataList[index].id) {
												str += "<ul>";
												for ( var tem in json.allSonMap[s]) {
													var flag = true;
													for ( var i in json.actionIds) {
														if (json.actionIds[i] == json.allSonMap[s][tem].id) {
															str += "<li><input type='checkBox' id='root"
																	+ json.allSonMap[s][tem].id
																	+ "' checked='checked'  name='root"
																	+ s
																	+ "' value='"
																	+ json.allSonMap[s][tem].id
																	+ "' onclick='selectBox(this);'/><span><i class='icon-minus-sign'></i> "
																	+ json.allSonMap[s][tem].name
																	+ "</span></li>";
															flag = false;
														}
													}
													if (flag)
														str += "<li><input type='checkBox' id='root"
																+ json.allSonMap[s][tem].id
																+ "'  name='root"
																+ s
																+ "' value='"
																+ json.allSonMap[s][tem].id
																+ "' onclick='selectBox(this);'/><span><i class='icon-minus-sign'></i> "
																+ json.allSonMap[s][tem].name
																+ "</span></li>";
												}
												str += "</ul>";
											}
										}
									}
								} else {
									str += "<li><input type='checkBox' id='"
											+ json.dataList[index].id
											+ "'  onclick='selectBox(this);' value='root"
											+ json.dataList[index].id
											+ "'/><span><i class='icon-folder-open'></i>"
											+ json.dataList[index].name
											+ "</span>";

									if (json.allSonMap != null) {
										for ( var s in json.allSonMap) {
											if (s == json.dataList[index].id) {
												str += "<ul>";
												for ( var tem in json.allSonMap[s]) {
													// 子菜单内容
													str += "<li><input type='checkBox' id='root"
															+ json.allSonMap[s][tem].id
															+ "'  name='root"
															+ s
															+ "' value='"
															+ json.allSonMap[s][tem].id
															+ "' onclick='selectBox(this);'/><span><i class='icon-minus-sign'></i> "
															+ json.allSonMap[s][tem].name
															+ "</span></li>";
												}
												str += "</ul>";
											}
										}
									}
								}
								str += "</li>";
							}
							$("#action_tree").html(str)
						}
					}
				});

	};

	var pageTool = page({
		renderTo : "role_list_pagetool",
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
				roleGrid.addDatas({
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

	var showRoleInfo = function(id) {
		var viewRoleMsgBox = msgBox({
			title : '查看角色信息',
			width : windowWidth,
			height : windowHeight,
			url : "role/list/role-saveOrUpdate.do"
		});
		viewRoleMsgBox.show();
		getRole(id);
		// 查看禁用下拉列表与文本框
		util.disabledElement($("#role_box"));
	};

	var addRole = function() {
		var addRoleMsgBox = msgBox({
			title : '新增基础数据',
			width : windowWidth,
			height : windowHeight,
			url : "role/list/role-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#role_box")[0], 3))
					return;
				if (!isSubmit) {
					return;
				}
				isSubmit = false;
				util.hellogoodAjax({
					url : 'role/add.do',
					data : util.serializeJson($("#role_box")),
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json != null && json.status == 'success') {
							$.Prompt("新增成功！");
							addRoleMsgBox.close();
							loadData(pageData.page, pageData.pageSize);// 重新加载
						}
					},
					complete : function() {
						isSubmit = true;
					}
				});
			}
		});
		addRoleMsgBox.show();
	};

	var updateRole = function(id) {
		var updateRoleMsgBox = msgBox({
			title : '修改角色信息',
			width : windowWidth,
			height : windowHeight,
			url : "role/list/role-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#role_box")[0], 3))
					return;
				util.hellogoodAjax({
					url : 'role/update.do',
					data : util.serializeJson($("#role_box")),
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json.status == 'success') {
							$.Prompt("修改成功！");
							updateRoleMsgBox.close();
							loadData(pageData.page, pageData.pageSize);// 重新加载
						}
					}
				});
			}
		});
		updateRoleMsgBox.show();
		getRole(id);
	};

	var getRole = function(id) {
		util.hellogoodAjax({
			url : "role/get/" + id + ".do",
			succFun : function(json) {
				if(json.errorMsg){
					$.Prompt(json.errorMsg);return;
				}
				if ( json.data == null) {
					$.Prompt("获取角色信息失败！");
					return;
				}
				for ( var index in json.data) {
					$("#role_" + index).val(json.data[index]);
				}
			}
		});
	};

	var removeRole = function() {
		var ids = roleGrid.getselecValues();
		if (ids == null || ids.length == 0) {
			$.Prompt("请选择需要删除的行! ");
			return;
		}
		ui.dialogBox({
			html : "确定删除选中的行？",
			okHandle : function() {
				util.hellogoodAjax({
					url : "role/delete/" + ids + ".do",
					succFun : function(json) {
						if (json.error) {
							$.Prompt(json.error);
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
		$('#role_list_select').click(function() {
			pageData.page = 1;
			loadData(pageData.page, pageData.pageSize);
		});

		// 新增
		$('#role_add').click(function() {
			addRole();
		});
		// 删除
		$('#role_del').click(function() {
			removeRole();
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
