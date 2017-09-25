window.hellogood.user = function() {
	var pageData = page.pageParams;
	var listUrl = 'user/pageJson.do';
	var _from = $("#userDataForm");
	var isSubmit = true;
	var windowWidth = 650;
	var windowHeight = 580;
	var hasCheckbox = true;
	var userGrid = null;
	var columns = [
		{
			name : '账号',
			dataIndex : 'userCode',
			renderer : function(data) {
				if(data.userName != null && data.userName != ''){
					return '<a id="view">' + data.userCode + '(' + data.userName + ')</a>';
				}else{
					return '<a id="view">' + data.userCode + '</a>';
				}
			},
			rendererCall : function(obj) {
				if ("view" === obj.id) {
					showUserInfo(obj.data.id);
				}
			}
		},
		{
			name : '性别',
			dataIndex : 'sex'
		},
		{
			name : '年龄',
			renderer : function(data) {
				if(data.age == null || data.age == 0){
					return '';
				}
				return data.age;
			},
			dataIndex : 'age'
		},
		{
			name : '常住</br>城市',
			dataIndex : 'liveCity'
		},
		{
			name : '审核</br>状态',
			renderer : function(data) {
				if (data.checkStatus) {
					return "<a id='update'>" + data.checkStatus
						+ "</a>";
				}
				return "";
			},
			rendererCall : function(obj) {
				showCheck(obj.data.id);
			}
		},
		{
			name : '注册时间',
			renderer : function(data) {
				if (data.createTime != null) {
					return util.date.toDateAll(data.createTime);
				}
				return '';
			}
		},
		{
			name : "备注",
			dataIndex : 'userRemark',
			renderer : function(data) {
				var remark = '';
				if (data.userRemark) {
					remark = util.removeHTMLTag(data.userRemark);
					if (remark.length > 5) {
						remark = remark.substr(0, 5).concat('...');
					}
				}
				return "<a id='seeRemark'>"+remark+"</a>";
			},
			rendererCall:function(obj){
				seeRemark(obj.data.id,obj.data.userRemark);
			}
		}];

	var operateCol = {
		name : '操作',
		align: "left",
		renderer : function(data) {
			var _html = '<a id="update"><font color="#F4D241"><b>修改</b></font></a>&nbsp;&nbsp;<a id="remark"><font color="#099854"><b>备注</b></font></a>';
			return _html;
		},
		rendererCall : function(obj) {
			if ('remark' === obj.id) {
				addUserRemark(obj.data.id);
			} else {
				updateUser(obj.data.id);
			}
		}
	};

	userGrid = grid({
			renderTo : 'user_List_grid',
			prefix : 'user',
			checkboxs : {
				checkbox : hasCheckbox,
				checkAll : hasCheckbox,
				dataIndex : 'id'
			},
			columns : columns
		});

	var seeRemark=function(userId,remark){
		var showContent = msgBox({
    		title : "备注内容详细",
    		width : 600,
    		height : 450,
    		url : "user/list/user-userRemark.do",
    		Btn : true,
			middleBtn : true,
			btnName : ['关闭'],
			okHandle : function() {
				showContent.close();
			}
    	});
    	showContent.show();
    	loadRemark(userId);
	};
	
	/**
	 * 展示用户审核记录
	 */
	var showCheck = function(userId) {
		var showCheckBox = msgBox({
			title : "用户审核记录",
			width : 650,
			height : 500,
			url : "user/list/user-showCheck.do",
		});
		showCheckBox.show();
		getShowCheck(userId);
	};

	/**
	 * 获取审核记录
	 */
	var getShowCheck = function(userId) {
		util
				.hellogoodAjax({
					url : "userCheck/getUserCheck/" + userId + ".do",
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json.dataList != null) {
							var tr = "<tr><td width='20%'>审核人</td><td width='20%'>审核时间</td><td width='20%'>审核状态</td><td width='40%'>备注</td></tr>";
							if (json.dataList.length > 0) {
								for ( var index in json.dataList) {
									tr += "<tr><td width='15%'>"
											+ json.dataList[index].employeeVO.name
											+ "</td><td width='15%'>"
											+ util.date
													.toDateAll(json.dataList[index].checkTime)
											+ "</td><td width='10%'>"
											+ getCheckState(json.dataList[index].checkState)
											+ "</td><td width='50%'>"
											+ json.dataList[index].remarks
											+ "</td></tr>";
								}
							} else {
								tr += "<tr><td colspan='4'><font color='red' ><strong>没有审核记录</strong></font></td></tr>";
							}
							$("#user_check_table").html(tr);
						}
					}
				});
	};

	// 1.未审核 2.通过 3.拒绝
	var getCheckState = function(checkState) {
		var tip = '';
		switch (checkState) {
		case 1:
			tip = '未审核';
			break;
		case 2:
			tip = '通过';
			break;
		case 3:
			tip = '拒绝';
			break;
		}
		return tip;
	};

	/**
	 * 添加备注
	 */
	var addUserRemark = function(userId) {
		var viewUserMsgBox = msgBox({
			title : '备注',
			width : 800,
			height : 600,
			url : "user/list/user-userRemark.do",
			btnName : [ "确定", "取消" ],
			Btn : true,
			middleBtn : true,
			okHandle : function() {
				var remark = CKEDITOR.instances.userRemarkEditor.getData();
				if (remark == '') {
					$.Prompt("备注内容不能为空");
					$("#userRemarkEditor").focus();
					return;
				}
				util.hellogoodAjax({
					url : "user/updateUserRemark.do",
					data : {
						"remark" : remark,
						"userId" : userId
					},
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json.status == 'success') {
							$.Prompt("保存成功");
							viewUserMsgBox.close();
							loadData(pageData.page, pageData.pageSize);
						}
					}
				});
			}
		});
		viewUserMsgBox.show();
		loadRemark(userId);
	};

	/**
	 * 加载备注
	 */
	var loadRemark = function(userId) {
		util.hellogoodAjax({
			url : "user/getRemark/" + userId + ".do",
			succFun : function(json) {
				if (json.errorMsg) {
					$.Prompt(json.errorMsg);
					return;
				}
				// HTML编辑器设置data数据
				util.editComfig("userRemarkEditor", "user");
				if (util.isNotBlank(json.data)
						&& util.isNotBlank(json.data.remark)) {
					CKEDITOR.instances.userRemarkEditor
							.setData(json.data.remark)
				}
				for ( var index in json.userVo) {
					$("#user_" + index).val(json.userVo[index])
				}
			}

		});
	};

	var pageTool = page({
		renderTo : "user_list_pagetool",
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
		if(util.isNotBlank(_data.minHeight)&& !(/^[1-9][0-9]*$/.test(_data.minHeight))){
			$.Prompt("身高必须是<font color='red'>大于0的数字</font>");return;
		}
		if(util.isNotBlank(_data.maxHeight )&& !(/^[1-9][0-9]*$/.test(_data.maxHeight))){
			$.Prompt("身高必须是<font color='red'>大于0的数字</font>");return;
		}
		if(util.isNotBlank(_data.minAge)&& !(/^[1-9][0-9]*$/.test(_data.minAge))){
			$.Prompt("年龄必须是<font color='red'>大于0的数字</font>");return;
		}
		if(util.isNotBlank(_data.maxAge)&& !(/^[1-9][0-9]*$/.test(_data.maxAge))){
			$.Prompt("年龄必须是<font color='red'>大于0的数字</font>");return;
		}
		util.hellogoodAjax({
			url : listUrl,
			data : _data,
			succFun : function(json) {
				if (json.errorMsg) {
					$.Prompt(json.errorMsg);
					return;
				}
				userGrid.addDatas({
					rowDatas : json.dataList
				});
				pageTool.render(json.total, page);
				pageData.page = page;
			}
		});
	};

	window.hellogood.userLoad = loadData;

	var uiInit = function() {
		userGrid.init();
		pageTool.init();
	};

	var showUserInfo = function(id) {
		var viewUserMsgBox = msgBox({
			title : '用户详情',
			width : windowWidth,
			height : windowHeight,
			url : "user/list/user-saveOrUpdate.do",
			Btn: true,
			middleBtn: true,
			btnName: [ '关闭' ],
			okHandle: function () {
				viewUserMsgBox.close();
			}

		});
		viewUserMsgBox.show();
		window.hellogood.updateUserMsgBox = viewUserMsgBox;
		msgBoxDataInit();
		getUser(id, 'VIEW');
		// 查看禁用下拉列表与文本框
		$('form[id^="userProfileFrom"]').each(function() {
			util.setReadonlyElement($(this));
		});
		$('#profileOperationDiv').css('display', 'none');
		$('#updateCustomValBtn').css('display', 'none');
		$('#addCustomVal_a').css('display', 'none');

	};

	var addUser = function() {
		var addUserMsgBox = msgBox({
			title : '新增用户',
			width : windowWidth,
			height : windowHeight,
			url : "user/list/user-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#user_box")[0], 3))
					return;
				if (!isSubmit) {
					return;
				}
				var uObj = util.serializeJson($("#user_box"));
				var dString = uObj.birthday;
				dString = dString.replace(/-/g, "/");
				uObj.birthday = new Date(dString);

				isSubmit = false;
				util.hellogoodAjax({
					url : 'user/add.do',
					data : uObj,
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json != null && json.status == 'success') {
							$.Prompt("新增成功！");
							$("input[name^='userId']").val(json.data);
						}
					},
					complete : function() {
						isSubmit = true;
					}
				});
			}
		});
		addUserMsgBox.show();
		msgBoxDataInit();
	};

	var showUpdateUserBox = function(id){
		var updateUserMsgBox = msgBox({
			title : '修改用户信息',
			width : windowWidth,
			height : windowHeight,
			url : "user/list/user-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "关闭" ],
			okHandle : function() {
				updateUserMsgBox.close();
			}
		});
		window.hellogood.updateUserMsgBox = updateUserMsgBox;
		updateUserMsgBox.show();
		msgBoxDataInit();
		getUser(id, 'EDIT');
		return updateUserMsgBox;
	}

	var updateUser = function(id) {

		var updateUserMsgBox = showUpdateUserBox(id);


		$('#user_birthday').change(function(){
			console.log(' user_birthday change .... ');
			var birthday = $('#user_birthday').val();
			$('#user_birthday').val(util.date.getAge(birthday));
		})

		$('#updateUserProfileBtn').unbind('click').bind('click', function(){
			if (!Validator.Validate($("#userProfileFrom")[0], 3))
				return;
			var uObj = util.serializeJson($("#userProfileFrom"));
			var dString = uObj.birthday;
			dString = dString.replace(/-/g, "/");
			uObj.birthday = new Date(dString);
			uObj.userRemark = CKEDITOR.instances.userRemarkEditor.getData();
			util.hellogoodAjax({
				url : 'user/update.do',
				data : uObj,
				succFun : function(json) {
					if (json.errorMsg) {
						$.Prompt(json.errorMsg);
						return;
					}
					if (json.status == 'success') {
						$.Prompt("修改成功！");
						updateUserMsgBox.close();
						loadData(pageData.page, pageData.pageSize);// 重新加载
					}
				}
			});
		})
	};

	var getUser = function(id, operateType) {
		util.hellogoodAjax({
			url: "user/get/" + id + ".do",
			succFun: function (json) {
				if (json.errorMsg) {
					$.Prompt(json.errorMsg);
					return;
				}
				// HTML编辑器设置data数据
				util.editComfig("userRemarkEditor", "user", 0, 80);
				if (util.isNotBlank(json.data.userRemark)) {
					CKEDITOR.instances.userRemarkEditor
						.setData(json.data.userRemark)
				}
				for (var index in json.data) {
					if(json.data[index] == null){
						continue;
					}
					$("#user_" + index).val(json.data[index]);
					if (index == 'birthday') {
						$("#user_" + index).val(
							util.date.toDate(json.data[index]));
					} else if (index == 'userRemark') {
						$('#user_remark').val(json.data[index]);
					}
				}
			}
		});
	};

	var bindEvent = function() {
		// 查询
		$('#user_list_select').click(function() {
			pageData.page = 1;
			loadData(pageData.page, pageData.pageSize);
		});

		// 新增
		$('#user_add').click(function() {
			addUser();
		});
	};

	var msgBoxDataInit = function() {
		// 下拉初始化
		util.hellogoodAjax({
			url : 'select/user.do',
			async : false,
			succFun : function(json) {
				var domMsgs = [ {
					domId : 'user_degree',
					jsonObj : 'degree',
					key : 'name',
					value : 'name'
				}, {
					domId : 'user_maritalStatus',
					jsonObj : 'marry',
					key : 'name',
					value : 'name'
				}, {
					domId : 'user_asset',
					jsonObj : 'asset',
					key : 'name',
					value : 'name'
				}, {
					domId : 'user_family',
					jsonObj : 'family',
					key : 'name',
					value : 'name'
				}, {
					domId : 'user_trystStatus',
					jsonObj : 'ownness',
					key : 'name',
					value : 'name'
				}, {
					domId : 'user_customerId',
					jsonObj : 'empList',
					key : 'id',
					value : 'name'
				}];
				util.selectPadData(domMsgs, json);
			}
		});
	};

	var queryDataInit = function () {
		util.hellogoodAjax({
			url: 'select/user.do',
			async: false,
			succFun: function (json) {
				var domMsgs = [{
					domId: 'user_degree_list',
					jsonObj: 'degree',
					key: 'name',
					value: 'name'
				}, {
					domId: 'user_customerId_list',
					jsonObj: 'empList',
					key: 'id',
					value: 'name'
				}, {
					domId : 'user_maritalStatus_list',
					jsonObj : 'marry',
					key : 'name',
					value : 'name'
				}];
				util.selectPadData(domMsgs, json);
			}
		});
	};

	var init = function() {
		uiInit();
		queryDataInit();
		bindEvent();
	};

	return {
		init : init,
		load : loadData,
		msgBoxDataInit : msgBoxDataInit,
		showUserInfo : showUserInfo,
		showCheck : showCheck,
		updateUserInfo : updateUser
	};
};