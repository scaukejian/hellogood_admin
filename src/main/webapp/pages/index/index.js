window.hellogood.index = function() {
	
	var bindEvent = function() {
	}
	
	// 加载页面信息
	var loadData = function() {
		util.hellogoodAjax({
			url : "validate/loadInfo.do",
			succFun : function(json) {
			}
		});
	}

	var init = function() {
		loadData();
		bindEvent();
		// laodAction
	};

	return {
		init : init

	};
};
var isSubmit = true;
function modifyPassword() {
	var addroleMsgBox = msgBox({
		title : '修改密码',
		width : 400,
		height : 330,
		url : "validate/list/index-modifyPassword.do",
		Btn : true,
		middleBtn : true,
		btnName : [ "确定", "取消" ],
		okHandle : function() {
			var oldPwd = $("#old_password").val();
			var newPwd = $("#new_password").val();
			var newPwd2 = $("#new_password_tow").val();
			if (oldPwd == '' || newPwd == '' || newPwd2 == '') {
				$.Prompt("不能为空");
				return;
			}
			if (!(newPwd == newPwd2)) {
				$.Prompt("两次输入的新密码不一样，请重新输入。");
				return;
			}
			if (!isSubmit) {
				return;
			}
			isSubmit = false;
			util.hellogoodAjax({
				url : 'validate/modifyPwd/' + newPwd + '-' + oldPwd + '.do',
				succFun : function(json) {
					if (json.errorMsg) {
						$.Prompt(json.errorMsg);
						return;
					}
					if (json != null && json.status == 'success') {
						$.Prompt("修改成功！");
						addroleMsgBox.close();
					}
				},
				complete : function() {
					isSubmit = true;
				}
			});
		}
	});
	addroleMsgBox.show();
};

function goAction(id, empId) {
	$("[id^=setting_][id!=setting_" + id + "]").css("display", "none")
	if ($("#setting_" + id).css("display") == 'none') {
		$("#setting_" + id).css("display", "");
	} else {
		$("#setting_" + id).css("display", "none");
		return;
	}
	util
			.hellogoodAjax({
				url : "validate/loadSon/" + id + "-" + empId + ".do", // "validate/loadSon/"+id+".do",
				succFun : function(json) {
					if (json == null || json.dataList == null) {
						$.Prompt("获取菜单失败！");
						return;
					}
					var str = "";
					for ( var index in json.dataList) {
						if (json.dataList[index].url == '') {
							str += "<li  class='glyphicon' ><a href='javaScript:goAction(\""
									+ json.dataList[index].id
									+ "\",\""
									+ empId
									+ "\");'>"
									+ json.dataList[index].name
									+ "</a>"
									+ " <span class='pull-right glyphicon'></span>"
									+ "<ul id='setting_"
									+ json.dataList[index].id
									+ "' class='nav nav-list secondmenu collapse in' style='display:none'></ul>"
									+ "</li>";
						} else {
							str += "<li id='action_url_"
									+ json.dataList[index].id
									+ "'><a href='javaScript:goUrl(\""
									+ json.dataList[index].url + "\",\""
									+ json.dataList[index].id + "\");'>"
									+ json.dataList[index].name + "</a></li>";
						}
					}
					$("#setting_" + id).html(str);
				}
			});
};

function regout() {
	util.hellogoodAjax({
		url : "validate/regout.do", // "validate/loadSon/"+id+".do",
		succFun : function(json) {
			if (json != null && json.status != null) {
				window.location.href = "../login.jsp";
			}
		}
	});
}
