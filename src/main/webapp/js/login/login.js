$(function() {
	var _form = $("#admin_login_form");

	$('#doSubmit').click(function() {
		// 印证登陆
		if ($("#account").val() == '账号') {
			$.Prompt("账号不能为空！");
			return;
		}
		if ($("#password").val() == '密码') {
			$.Prompt("密码不能为空！");
			return;
		}
		if ($("#code").val() == '验证码') {
			$.Prompt("验证码不能为空！");
			return;
		}
		doLogin();
	});

	var doLogin = function() {
		$.ajax({
			url : "../validate/doLogin.do",
			type : "post",
			dataType : "json",
			data : _form.serializeArray(),
			success : function(json) {
				if (json.errorMsg) {
					// $("#result_info").html(json.errorMsg);
					$.Prompt(json.errorMsg);
					changeImage();
					return;
				}
				if (json.data != null) {
					// window.location.href="../../validate/goMain/"+json.data.id+".do";
					window.location.href = "../pages/index/index.jsp";
				}
			}
		});

	}
	$("#changeImgObj").click(function() {
		changeImage();

	});
	var changeImage = function() {
		$("#changeImgObj").attr("src", "../verifyCode/get.do?t=" + new Date());
	};
	// util.serializeJson(_from)
	// var changeImage = function changeImage(){
	// $("#imgObj").attr("src","<%=path%>/xuan/verifyCode.do?t="+new Date());
	// }
	// 密码输入框显示控制
	$(".mima_ipt").focus(function() {
		if($(this).val()=='密码')
			$(this).val("");
		$(this).attr("type", "password");
	}).blur(function() {
		var length = $(this).val().length;
		if (length <= 0) {
			$(this).attr("type", "text");
			$(this).val("密码");
		}
	});

	// 记住密码显示
	$(".remove_btn").click(
			function() {
				if ($(this)[0].checked == true) {
					$(this).css("background-image",
							"url(../images/login/remove_btn_in.png)")
				} else {
					$(this).css("background-image",
							"url(../images/login/remove_btn.png)")
				}
				saveUserInfo();
			});
});

$(document).keyup(function(event) {
	if (event.keyCode == 13) {
		$("#doSubmit").trigger("click");
	}
});

var input_focus = function(t) {
	if(t.value=='账号')
		t.value = '';
	t.style.color = '#ededed';
};
var input_blur = function(t) {
	if (t.value == '') {
		t.value = '账号';
		t.style.color = '#a5a5a5';
	}
};

var yzm_input_focus = function(t) {
	if(t.value=='验证码')
		t.value = '';
	t.style.color = '#ededed';
};
var yzm_input_blur = function(t) {
	if (t.value == '') {
		t.value = '验证码';
		t.style.color = '#a5a5a5';
	}
};

$(".sign_in_btn").click(
		function() {
			$(".sign_in_btn").css("background-image",
					"url(../images/login/sign_btn_click.png)")
			$(".sign_in_btn").css("color", "#ededed")
			$(".sign_in_btn").text("登录中...")
		});
// 登录与登录中切换
$(".sign_in_btn").hover(
		function() {
			$(".sign_in_btn").css("background-image",
					"url(../images/login/sign_btn_hover.png)")
		},
		function() {
			$(".sign_in_btn").css("background-image",
					"url(../images/login/sign_btn.png)")
			$(".sign_in_btn").css("color", "#e5e5e5")
			$(".sign_in_btn").text("登录")//
		})
;

// 初始化页面时验证是否记住了密码
// $(document).ready(function() {
// 	if ($.cookie("rmbUser") == "true") {
// 		$("#rmbUser").css("background-image","url(../images/login/remove_btn_in.png)")
// 		$("#account").val($.cookie("account"));
// 		$("#password").val($.cookie("password"));
// 		$("#password").attr("type", "password");
// 	}
// });

// 保存用户信息
function saveUserInfo() {
	if ($("#rmbUser")[0].checked == true) {
		var account = $("#account").val();
		var password = $("#password").val();
		$.cookie("rmbUser", "true", { expires: 30 }); // 存储一个带天期限的 cookie
		$.cookie("account", account, { expires: 30 }); // 存储一个带天期限的 cookie
		// $.cookie("password", password, { expires: 30 }); // 存储一个带天期限的 cookie
	} else {
		$.cookie("rmbUser", "false", {
			expires : -1
		});
		$.cookie("account", '账号', {
			expires : -1
		});
		// $.cookie("password", '密码', {
		// 	expires : -1
		// });
	}
};