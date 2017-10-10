window.hellogood.user = function() {
	var pageData = page.pageParams;
	var listUrl = 'user/pageJson.do';
	var _from = $("#userDataForm");
	var isSubmit = true;
	var windowWidth = 650;
	var windowHeight = 530;
	var hasCheckbox = true;
	var _grid = null;
	var columns = [
		{
			name : '账号',
			width:'250px',
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
			name : '手机号',
			dataIndex : 'phone'
		},
		{
			name : '微信号',
			dataIndex : 'weixinName'
		},
		{
			name : 'QQ',
			dataIndex : 'qq'
		},
		{
			name : '生日',
            renderer : function(data) {
                if (data.birthday != null) {
                    return util.date.toDate(data.birthday);
                }
                return '';
            }
		},
		{
			name : '公司',
			dataIndex : 'company'
		},
		{
			name : '职位',
			dataIndex : 'job'
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
			width:'100px',
			dataIndex : 'remark',
			renderer : function(data) {
				var remark = '';
				if (data.remark != null) {
					remark = util.removeHTMLTag(data.remark);
					if (remark.length > 5) {
						remark = remark.substr(0, 5).concat('...');
					}
				}
				return remark;
			}
		}];

	var operateCol = {
		name : '操作',
		align: "center",
		renderer : function(data) {
			var _html = '<a id="update"><font color="#099854"><b>修改</b></font></a>';
			return _html;
		},
		rendererCall : function(obj) {
            updateUser(obj.data.id);
		}
	};
    columns.push(operateCol);

	_grid = grid({
			renderTo : 'user_List_grid',
			prefix : 'user',
			checkboxs : {
				checkbox : hasCheckbox,
				checkAll : hasCheckbox,
				dataIndex : 'id'
			},
			columns : columns
		});

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
				_grid.addDatas({
					rowDatas : json.dataList
				});
				pageTool.render(json.total, page);
				pageData.page = page;
			}
		});
	};

	window.hellogood.userLoad = loadData;

	var uiInit = function() {
		_grid.init();
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
		getUser(id);
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
				if (!Validator.Validate($("#userProfileFrom")[0], 3)) return;
                if (!checkCommon(util.serializeJson($('#userProfileFrom')))) return;
				var uObj = util.serializeJson($("#userProfileFrom"));
                var remark = CKEDITOR.instances.userRemarkEditor.getData();
				var dString = uObj.birthday;
				dString = dString.replace(/-/g, "/");
				uObj.birthday = new Date(dString);
                uObj.remark = remark;
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
                            addUserMsgBox.close();
                            setTimeout(function () {
                                loadData(pageData.page, pageData.pageSize);
                            }, 1000);
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
        util.editComfig("userRemarkEditor", "user", 0, 80);
        $('#profilePictureDiv').html(' <input type="file" id="photo_upload" name="file" style="display: none;"/> ' +
            '<div class="position form-group showPhotos" id="photos_show"> ' +
            '<div onclick="photoUpload()" style="float: left; width: 55px; height: 55px; border: 1px solid #000; text-align: center; ' +
            'cursor: pointer; line-height: 55px;margin: 5 15 0 0;"> ' +
            '<font size="12px">+</font> </div></div>');
	};

    var checkCommon = function (obj) {
        if ('' != obj.age && !/^([1-9]\d*)$/.test(obj.age)) {
            $.Prompt('年龄必须为正整数');
            $('#user_age').focus();
            return false;
        }
        if ('' != obj.height && !/^([1-9]\d*)$/.test(obj.height)) {
            $.Prompt('身高必须为正整数');
            $('#user_height').focus();
            return false;
        }
        return true;
    }

	var showUpdateUserBox = function(id){
		var updateUserMsgBox = msgBox({
			title : '修改用户信息',
			width : windowWidth,
			height : windowHeight,
			url : "user/list/user-saveOrUpdate.do",
			Btn : true,
			middleBtn : true,
            btnName : [ "确定", "取消" ],
            okHandle : function() {
                if (!Validator.Validate($("#userProfileFrom")[0], 3)) return;
                if (!checkCommon(util.serializeJson($('#userProfileFrom')))) return;
                var uObj = util.serializeJson($("#userProfileFrom"));
                var dString = uObj.birthday;
                dString = dString.replace(/-/g, "/");
                uObj.birthday = new Date(dString);
                uObj.remark = CKEDITOR.instances.userRemarkEditor.getData();
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
			}
		});
		window.hellogood.updateUserMsgBox = updateUserMsgBox;
		updateUserMsgBox.show();
		msgBoxDataInit();
		getUser(id);
       $('#user_userCode').attr("readonly","readonly");
		return updateUserMsgBox;
	}

	var updateUser = function(id) {

		var updateUserMsgBox = showUpdateUserBox(id);

		$('#user_birthday').change(function(){
			var birthday = $('#user_birthday').val();
			$('#user_birthday').val(util.date.getAge(birthday));
		})

	};

	var getUser = function(id) {
		util.hellogoodAjax({
			url: "user/get/" + id + ".do",
			succFun: function (json) {
				if (json.errorMsg) {
					$.Prompt(json.errorMsg);
					return;
				}
				// HTML编辑器设置data数据
				util.editComfig("userRemarkEditor", "user", 0, 80);
				if (util.isNotBlank(json.data.remark)) {
					CKEDITOR.instances.userRemarkEditor
						.setData(json.data.remark)
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
						$('#userRemarkEditor').val(json.data[index]);
					}
				}
                showHeadPhoto(json.data['headPhoto']);
			}
		});
	};


    var showHeadPhoto = function(userPhoto){
        if(null == userPhoto || null == userPhoto.imgName){
            $('#profilePictureDiv').html(' <input type="file" id="photo_upload" name="file" style="display: none;"/> ' +
                '<div class="position form-group showPhotos" id="photos_show"> ' +
                '<div onclick="photoUpload()" style="float: left; width: 55px; height: 55px; border: 1px solid #000; text-align: center; ' +
                'cursor: pointer; line-height: 55px;margin: 5 15 0 0;"> ' +
                '<font size="12px">+</font> </div></div>');
        } else {
            $('#profilePictureDiv').append("<div class='image-set' style='float: left;'>" +
                "<a class='example-image-link' data-lightbox='example-set-photo'  href='../../user/download.do?fileName=" +
                userPhoto.imgName + "' ><img class='example-image' height='60px' width='60px'  src='../../user/download.do?fileName=" +
                userPhoto.imgName + "'/>" +
                "</a><div style='display: inline;margin-left: 5px'><a onclick='deletePhoto("+userPhoto.id+");' style='font-size: 12px;'>删除</a></div></div>");
            //<div><a onclick='deletePhoto("+userPhoto.id+");' style='font-size: 12px'>删除</a></div>
        }
    }

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
		// 删除
		$('#user_del').click(function() {
            delUser();
		});
	};

    var delUser = function() {
        var ids = _grid.getselecValues();
        if (ids == null || ids.length == 0) {
            $.Prompt("请选择需要删除的记录! ");
            return;
        }
        ui.dialogBox({
            html : "确定删除选中的记录？",
            okHandle : function() {
                util.hellogoodAjax({
                    url : "user/delete/" + ids + ".do",
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

	var msgBoxDataInit = function() {
		// 下拉初始化
		util.hellogoodAjax({
			url : 'select/user.do',
			async : false,
			succFun : function(json) {
				var domMsgs = [ {
					domId : 'user_maritalStatus',
					jsonObj : 'marry',
					key : 'name',
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
		updateUserInfo : updateUser
	};
};