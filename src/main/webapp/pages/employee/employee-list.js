window.hellogood.employee = function() {
	var roleData = null;
	var pageData = page.pageParams;
	var listUrl = 'employee/pageJson.do';
	var _from = $("#employeeDataForm");
	var isSubmit = true;
	var windowWidth = 450;
	var windowHeight = 330;
	var hasCheckbox = true;
	var employeeGrid = null;
	var initGrid = function() {
		var columns = [ {
			name : '名称',
			width : '15%',
			renderer : function(data) {
				return '<a id="view">' + data.name + '</a>';
			},
			rendererCall : function(obj) {
				if ("view" === obj.id) {
					showEmployeeInfo(obj.data.id);
				}
			}
		}, {
			name : '账号',
			width : '10%',
			dataIndex : 'account'
		}, {
			name : '联系手机',
			width : '10%',
			dataIndex : 'mobilePhone'
		}, {
			name : '权限',
			width : '25%',
			align : 'left',
			renderer : function(data) {
				if (data.role != null) {
					var str = "";
					var tem = 1;
					for ( var index in data.role) {
						if (tem > 1) {
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
			name : '禁/启用',
			width : '7%',
			renderer : function(data) {
				if (data.status == '0') {
					return '<a id="modifyqy">启用</a>';
				} else {
					return '<a id="modifyjy">禁用</a>';
				}
			},
			rendererCall : function(obj) {
				// 更新用户状态。
				if (obj.id == 'modifyqy')
					updateEmployeeStatus(obj.data.id, 1);
				else
					updateEmployeeStatus(obj.data.id, 0);
			}

		},

		{
			name : '操作',
			width : '7%',
			renderer : function(data) {

				return '<a id="update">修改</a>';
			},
			rendererCall : function(obj) {
				updateEmployee(obj.data.id);
			}

		}, {
			name : "权限",
			width : '10%',
			renderer : function(data) {
				return '<a id="permission">赋权</a>';
			},
			rendererCall : function(obj) {
				modifyPermission(obj.data.id);
			}

		} ];

		employeeGrid = grid({
			renderTo : 'employee_List_grid',
			prefix : 'employee',
			checkboxs : {
				checkbox : hasCheckbox,
				checkAll : hasCheckbox,
				dataIndex : 'id'
			},
			columns : columns
		});
		employeeGrid.init();
	};

	var pageTool = page({
		renderTo : "employee_list_pagetool",
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
				employeeGrid.addDatas({
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

	/**
	 * 查看员工信息
	 */
	var showEmployeeInfo = function(id) {
		var viewEmployeeMsgBox = msgBox({
			title : '查看员工信息',
			width : windowWidth,
			height : windowHeight,
			url : "employee/list/employee-saveOrUpdate.do"
		});
		viewEmployeeMsgBox.show();
		getEmployee(id);
		// 查看禁用下拉列表与文本框
		util.disabledElement($("#employee_box"));
	};

	/**
	 * 更新员工状态 （禁用 启用）
	 */
	var updateEmployeeStatus = function(id, status) {
		util.hellogoodAjax({
			url : 'employee/refuse/' + id + "-" + status + '.do',
			succFun : function(json) {
				if (json.errorMsg) {
					$.Prompt(json.errorMsg);
					return;
				}
				if (json.status == 'success') {
					$.Prompt("操作成功！");
					loadData(pageData.page, pageData.pageSize);// 重新加载
				}
			}
		});
	};
	
	/**
	 * 修改权限
	 */
	var modifyPermission = function(id) {
		var modifyPermissionMsgBox = msgBox({
			title : '赋权',
			width : 400,
			height : 600,
			url : "employee/list/employee-permission.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#employee_box")[0], 3))
					return;
				// 获取权限选择json数据
				var str = "";
				$("input[name='selects']:checked").each(function() {
					if (str == '')
						str += this.value;
					else
						str += "," + this.value;
				});
				util.hellogoodAjax({
					url : 'employee/permission/' + str + '-' + id + '.do',
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json.status == 'success') {
							$.Prompt("操作成功！");
							modifyPermissionMsgBox.close();
							loadData(pageData.page, pageData.pageSize);// 重新加载
						}
					}
				});
			}
		});
		modifyPermissionMsgBox.show();
		getRoles(id);
	};

	/**
	 * 获取角色信息
	 */
	var getRoles = function(id) {
		util
				.hellogoodAjax({
					url : "role/getAll/" + id + ".do",
					succFun : function(json) {
						if(json.errorMsg){
							$.Prompt(json.errorMsg);return;
						}
						if (json == null || json.dataList == null) {
							$.Prompt("获取角色信息失败！");
							return;
						}
						var str = "";
						for ( var index in json.dataList) {
							if (json.roleIds != null && json.roleIds != '') {
								var flag = true;
								for ( var index1 in json.roleIds) {
									if (json.roleIds[index1] == (json.dataList[index].id)) {
										str += "<tr><td>"
												+ json.dataList[index].name
												+ "<td><td><input type='checkBox' name='selects' value='"
												+ json.dataList[index].id
												+ "' checked=true/></td></tr>"
										flag = false;
									}
								}
								if (flag) {
									str += "<tr><td>"
											+ json.dataList[index].name
											+ "<td><td><input type='checkBox' name='selects' value='"
											+ json.dataList[index].id
											+ "'/></td></tr>"
								}
							} else {
								str += "<tr><td>"
										+ json.dataList[index].name
										+ "<td><td><input type='checkBox' name='selects' value='"
										+ json.dataList[index].id
										+ "'/></td></tr>"
							}
						}
						$("#check_box").html(str);

					}
				});

	};
	
	/*
	 * 添加员工信息
	 */
	var addEmployee = function() {
		var addEmployeeMsgBox = msgBox({
			title : '新增基础数据',
			width : windowWidth,
			height : windowHeight,
			url : "employee/list/employee-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#employee_box")[0], 3))
					return;
				if (!isSubmit) {
					return;
				}
				isSubmit = false;
				util.hellogoodAjax({
					url : 'employee/add.do',
					data : util.serializeJson($("#employee_box")),
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json != null && json.status == 'success') {
							$.Prompt("新增成功！");
							addEmployeeMsgBox.close();
							loadData(pageData.page, pageData.pageSize);// 重新加载
						}
					},
					complete : function() {
						isSubmit = true;
					}
				});
			}
		});
		addEmployeeMsgBox.show();
	};

	/**
	 * 更新员工信息
	 */
	var updateEmployee = function(id) {
		var updateEmployeeMsgBox = msgBox({
			title : '修改员工信息',
			width : windowWidth,
			height : windowHeight,
			url : "employee/list/employee-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#employee_box")[0], 3))
					return;
				util.hellogoodAjax({
					url : 'employee/update.do',
					data : util.serializeJson($("#employee_box")),
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json.status == 'success') {
							$.Prompt("修改成功！");
							updateEmployeeMsgBox.close();
							loadData(pageData.page, pageData.pageSize);// 重新加载
						}
					}
				});
			}
		});
		updateEmployeeMsgBox.show();
		getEmployee(id);
	};

	/**
	 * 获取员工信息
	 */
	var getEmployee = function(id) {
		util.hellogoodAjax({
			url : "employee/get/" + id + ".do",
			succFun : function(json) {
				if(json.errorMsg){
					$(json.errorMsg);return;
				}
				if (json.data == null) {
					$.Prompt("获取员工信息失败！");
					return;
				}
				for ( var index in json.data) {
					$("#employee_" + index).val(json.data[index]);
				}

			}
		});
	};

	/**
	 * 删除员工信息
	 */
	var removeEmployee = function() {
		var ids = employeeGrid.getselecValues();
		if (ids == null || ids.length == 0) {
			$.Prompt("请选择需要删除的行! ");
			return;
		}
		ui.dialogBox({
			html : "确定删除选中的行？",
			okHandle : function() {
				util.hellogoodAjax({
					url : "employee/delete/" + ids + ".do",
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

	/**
	 * 绑定事件
	 */
	var bindEvent = function() {
		// 查询
		$('#employee_list_select').click(function() {
			pageData.page = 1;
			loadData(pageData.page, pageData.pageSize);
		});
		// 新增
		$('#employee_add').click(function() {
			addEmployee();
		});
		// 删除
		$('#employee_del').click(function() {
			removeEmployee();
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
