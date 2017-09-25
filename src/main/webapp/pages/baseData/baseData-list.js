window.hellogood.baseData = function() {
	var pageData = page.pageParams;
	var listUrl = 'baseData/pageJson.do';
	var _from = $("#baseDataForm");
	var windowWidth = 350;
	var windowHeight = 280;
	var hasCheckbox = true;
	var BaseDataGrid = null;
	var initGrid = function() {
		var columns = [
				{
					name : '编号',
					dataIndex : 'type',
					renderer : function(data) {
						return '<a id="view">' + data.code + '</a>';
					},
					rendererCall : function(obj) {
						if ("view" === obj.id) {
							showBaseDataInfo(obj.data.id);
						}
					}
				},
				{
					name : '类型',
					dataIndex : 'type'
				},
				{
					name : '名称',
					width : '50%',
					dataIndex : 'name'
				},
				{
					name : '操作',
					renderer : function(data) {
						if (data.status == '1') {
							return '<a id="update">修改</a>&nbsp;&nbsp;&nbsp;<a id="zuofei">作废</a>';
						} else {
							return '<a id="update">修改</a>&nbsp;&nbsp;&nbsp;<a id="huifu">恢复使用</a>';
						}

					},
					rendererCall : function(obj) {
						if (obj.id == 'update') {
							updateBaseData(obj.data.id);
						}
						if (obj.id == 'zuofei') {
							modifyStatus(obj.data.id, 0);
						}
						if (obj.id == 'huifu') {
							modifyStatus(obj.data.id, 1);
						}

					}
				} ];

		BaseDataGrid = grid({
			renderTo : 'baseData_List_grid',
			prefix : 'baseData',
			checkboxs : {
				checkbox : hasCheckbox,
				checkAll : hasCheckbox,
				dataIndex : 'id'
			},
			columns : columns
		});
		BaseDataGrid.init();
	};

	var modifyStatus = function(id, status) {
		util.hellogoodAjax({
			url : "baseData/modifyStatus/" + id + "-" + status + ".do",
			succFun : function(json) {
				if (json.errorMsg) {
					$.Prompt(json.errorMsg);
					return;
				}
				if (json.status == 'success') {
					loadData(pageData.page, pageData.pageSize);// 重新加载
				}
			}
		});
	}

	var pageTool = page({
		renderTo : "baseData_list_pagetool",
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
				BaseDataGrid.addDatas({
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

	var showBaseDataInfo = function(id) {
		var viewBaseDataMsgBox = msgBox({
			title : '查看基本数据信息',
			width : windowWidth,
			height : windowHeight,
			url : "baseData/list/baseData-saveOrUpdate.do"
		});
		viewBaseDataMsgBox.show();
		msgBoxDataInit();
		getBaseData(id);
		//查看禁用下拉列表与文本框
		util.disabledElement($("#baseData_box"));
	};

	var addBaseData = function() {
		var addBaseDataMsgBox = msgBox({
			title : '新增基本数据',
			width : windowWidth,
			height : windowHeight,
			url : "baseData/list/baseData-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#baseData_box")[0], 3))
					return;
				var uObj = util.serializeJson($("#baseData_box"));
				util.hellogoodAjax({
					url : 'baseData/add.do',
					data : uObj,
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json != null && json.status == 'success') {
							$.Prompt("新增成功！");
							addBaseDataMsgBox.close();
							loadData(pageData.page, pageData.pageSize);// 重新加载
						}
					}
				});
			}
		});
		addBaseDataMsgBox.show();
		msgBoxDataInit();
	};

	var updateBaseData = function(id) {
		var updateBaseDataMsgBox = msgBox({
			title : '修改基本数据信息',
			width : windowWidth,
			height : windowHeight,
			url : "baseData/list/baseData-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
			btnName : [ "确定", "取消" ],
			okHandle : function() {
				if (!Validator.Validate($("#baseData_box")[0], 3))
					return;
				util.hellogoodAjax({
					url : 'baseData/update.do',
					data : util.serializeJson($("#baseData_box")),
					succFun : function(json) {
						if (json.errorMsg) {
							$.Prompt(json.errorMsg);
							return;
						}
						if (json.status == 'success') {
							$.Prompt("修改成功！");
							updateBaseDataMsgBox.close();
							loadData(pageData.page, pageData.pageSize);// 重新加载
						}
					}
				});
			}
		});
		updateBaseDataMsgBox.show();
		msgBoxDataInit();
		getBaseData(id);
	};

	var getBaseData = function(id) {
		util.hellogoodAjax({
			url : "baseData/get/" + id + ".do",
			succFun : function(json) {
				if(json.errorMsg){
					$.Prompt(json.errorMsg);return;
				}
				if (json.data == null) {
					$.Prompt("获取基本数据信息失败！");
					return;
				}
				for ( var index in json.data) {
					$("#baseData_" + index).val(json.data[index]);
				}
			}
		});
	};

	var bindEvent = function() {
		// 查询
		$('#baseData_list_select').click(function() {
			pageData.page = 1;
			loadData(pageData.page, pageData.pageSize);
		});

		// 新增
		$('#baseData_add').click(function() {
			addBaseData();
		});

	};

	/**
	 * 弹出框下拉列表初始化
	 */
	var msgBoxDataInit = function() {
		// 下拉初始化
		util.hellogoodAjax({
			url : 'select/baseDataType.do',
			async : false,
			succFun : function(json) {
				var domMsgs = [ {
					domId : 'baseData_type',
					jsonObj : 'dataList',
					key : 'code',
					value : 'name'
				} ];
				util.selectPadData(domMsgs, json);
				console.log("下拉框初始化");
			}
		});
	}

	var queryDataInit = function() {
		// 下拉初始化
		util.hellogoodAjax({
			url : 'select/baseDataType.do',
			async : false,
			succFun : function(json) {
				var domMsgs = [ {
					domId : 'baseDataType',
					jsonObj : 'dataList',
					key : 'code',
					value : 'name'
				} ];
				util.selectPadData(domMsgs, json);
			}
		});
	};

	var init = function() {
		uiInit();
		bindEvent();
		queryDataInit();
	};

	return {
		init : init,
		load : loadData
	};
};
