/**
 * 常工方法类
 * 
 * @author fb 
 */
var util = window.hellogood.util;

util.date = {
	toDate : function(strDate) {
		var date = "";
		var d = new Date(strDate);
		date += d.getFullYear() + "-";
		date += ("0" + (d.getMonth() + 1)).slice(-2) + "-";
		date += ("0" + d.getDate()).slice(-2);
		return date;
	},
	toDateAll : function(strDate) {
		var date = "";
		var d = new Date(strDate);
		date += d.getFullYear() + "-";
		date += ("0" + (d.getMonth() + 1)).slice(-2) + "-";
		date += ("0" + d.getDate()).slice(-2) + " ";
		date += ("0" + d.getHours()).slice(-2) + ":";
		date += ("0" + d.getMinutes()).slice(-2) + ":";
		date += ("0" + d.getSeconds()).slice(-2);
		return date;
	},
	toDateHM : function(strDate) {
		var date = "";
		var d = new Date(strDate);
		date += d.getFullYear() + "-";
		date += ("0" + (d.getMonth() + 1)).slice(-2) + "-";
		date += ("0" + d.getDate()).slice(-2) + " ";
		date += ("0" + d.getHours()).slice(-2) + ":";
		date += ("0" + d.getMinutes()).slice(-2) ;
		//date += ("0" + d.getSeconds()).slice(-2);
		return date;
	},
	toHourM : function(strDate) {
		var date = "";
		var d = new Date(strDate);
		date += ("0" + d.getHours()).slice(-2) + ":";
		date += ("0" + d.getMinutes()).slice(-2) ;
		//date += ("0" + d.getSeconds()).slice(-2);
		return date;
	},
	getWeek : function(date) {
		var weeks = {
			0 : '周日',
			1 : '周一',
			2 : '周二',
			3 : '周三',
			4 : '周四',
			5 : '周五',
			6 : '周六'
		};
		return weeks[date.getDay()] + '(' + date.format("MM-dd") + ')';
	},
	/**
	 * 时间格式只能是yyyy-MM-dd才行，用new Date(str)这样的格式ie7 8不兼容 
	 */
	parseToDate : function(dateStringInRange) {
		var isoExp = /^\s*(\d{4})-(\d\d)-(\d\d)\s*$/, date = new Date(NaN), month, parts = isoExp
				.exec(dateStringInRange);

		if (parts) {
			month = +parts[2];
			date.setFullYear(parts[1], month - 1, parts[3]);
			if (month != date.getMonth() + 1) {
				date.setTime(NaN);
			}
		}
		return date;
	},
    /**
     * 时间格式只能是yyyy-MM-dd
     */
    getAge: function (dateStr) {
        var r = dateStr.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
        if (r == null)return   false;
        var d = new Date(r[1], r[3] - 1, r[4]);
        if (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]) {
            var Y = new Date().getFullYear();
            return (Y - r[1]);
        }
        return("输入的日期格式错误！");
    }
};

// 获取页面参数
util.iGet = {
	// 获取页面滚动位置,返回object,水平位移:X和垂直位移:Y
	PageScroll : function() {
		var x, y;
		if (window.pageYOffset) {
			y = window.pageYOffset;
			x = window.pageXOffset;
		} else if (document.documentElement
				&& document.documentElement.scrollTop) {
			y = document.documentElement.scrollTop;
			x = document.documentElement.scrollLeft;
		} else if (document.body) {
			y = document.body.scrollTop;
			x = document.body.scrollLeft;
		}
		return {
			X : x,
			Y : y
		};
	},
	// 获取页面尺寸，返回objectpageW(页面宽度)和pageH(页面高度)winW(窗口宽度)和winH(窗口高度)
	PageSize : function() {
		var scrW, scrH;
		if (window.innerHeight && window.scrollMaxY) {
			scrW = window.innerWidth + window.scrollMaxX;
			scrH = window.innerHeight + window.scrollMaxY;
		} else if (document.body.scrollHeight > document.body.offsetHeight) {
			scrW = document.body.scrollWidth;
			scrH = document.body.scrollHeight;
		} else if (document.body) {
			scrW = document.body.offsetWidth;
			scrH = document.body.offsetHeight;
		}
		var winW, winH;
		if (window.innerHeight) {
			winW = window.innerWidth;
			winH = window.innerHeight;
		} else if (document.documentElement
				&& document.documentElement.clientHeight) {
			winW = document.documentElement.clientWidth;
			winH = document.documentElement.clientHeight;
		} else if (document.body) {
			winW = document.body.clientWidth;
			winH = document.body.clientHeight;
		}
		// 定义并获取有效高度，有效高度的规则为，滚动高度和window高度，谁大就取谁
		var pageW = (scrW < winW) ? winW : scrW;
		// 定义并获取有效宽度，有效宽度的规则为，滚动宽度和window宽度，谁大就宽度
		var pageH = (scrH < winH) ? winH : scrH;
		// 返回一个对象，存储了页面高度及宽度，window高度及宽度，
		return {
			PageW : pageW,
			PageH : pageH,
			WinW : winW,
			WinH : winH
		};
	}
};

// 屏蔽层对象
util.maskDiv = {
	hasMask : true,// 标识是否存在屏蔽层

	/**
	 * 显示屏蔽层
	 * 
	 * @param id
	 *            当前显示窗体的id,可为空
	 */
	show : function(id) {
		if (id) {
			// 判断屏蔽层是否存在
			if ($('#overlay').length > 0) {
				// 判断显示的窗体是否已经存在于层数组util.layerIndex.layerStor
				if ($.inArray(id, util.layerIndex.layerStor) != -1) {
					// 获取显示窗体的z-index属性值
					var z_index = $('#' + id).css('z-index');
					// 设置屏蔽层的z-index属性值为当前显示窗体的z-index值小1
					$('#overlay').css('z-index', parseInt(z_index, 10) - 1);
				}
				$('#overlay').show();
			} else {
				util.maskDiv.hasMask = true;
				util.maskDiv.set();
				$(window).bind('resize', util.maskDiv.set).bind('scroll',
						util.maskDiv.scroll);

				$('#overlay').show();
			}
		} else {
			if (util.maskDiv.hasMask) {
				return;
			} else {
				util.maskDiv.hasMask = true;
				util.maskDiv.set();
				$(window).bind('resize', util.maskDiv.set).bind('scroll',
						util.maskDiv.scroll);
				$('#overlay').show();
			}
		}
	},

	/**
	 * 隐藏屏蔽层
	 * 
	 * @param id
	 *            当前显示窗体的id,可为空
	 */
	hide : function(id) {
		if (id) {
			var z_index;
			if (util.layerIndex.layerStor.length > 0) {
				z_index = $(
						'#'
								+ util.layerIndex.layerStor[util.layerIndex.layerStor.length - 1])
						.css('z-index');
				$('#overlay').css('z-index', parseInt(z_index, 10) - 1);
			} else {
				var $myLightBox = $('.myLightbox');
				if ($myLightBox.length > 0) {
					$('#overlay').css('z-index',
							parseInt($myLightBox.css('z-index'), 10) - 1);
				} else {
					$('#overlay').hide();
				}
			}

		} else {
			$('#overlay').remove();
			$('span.overlay_window').remove();
			$(window).unbind('scroll', util.maskDiv.scroll).unbind('resize',
					util.maskDiv.set);
			util.maskDiv.hasMask = false;
		}
	},

	// 设置遮罩层
	set : function() {
		var $div = $("<div id='overlay' class='mask'></div>");
		$('#overlay').get(0) || $('body').append($div);
		var $window = $(window);
		var $document = $(document);
		var w, l;
		if ($document.scrollLeft() === 0) {
			w = $window.width();
			l = w;
		} else {
			w = $document.width();
			l = $window.width() + $document.scrollLeft() * 2;
		}
		var h, t;
		if ($document.scrollTop() === 0) {
			h = $window.height();
			t = h;
		} else {
			h = $document.height();
			t = $window.height() + $document.scrollTop() * 2;
		}
		util.maskDiv.position(null, h, t, l);
	},

	/**
	 * scroll事件的处理函数
	 */
	scroll : function() {
		var $window = $(window);
		var $document = $(document);
		var w = $window.width() + $document.scrollLeft();
		var h = $window.height() + $document.scrollTop();
		var l = w - $document.scrollLeft();
		var t = h + $document.scrollTop();
		util.maskDiv.position(w, h, t, l);
	},

	/**
	 * 设置屏蔽层的坐标
	 */
	position : function(w, h, t, l) {
		if (!w) {
			if (util.maskDiv.browser) {
				$('#overlay').css({
					width : $(window).width() + "px",
					height : h + "px"
				});
			} else {

				$('#overlay').css({
					width : "100%",
					height : h + "px"
				});
			}
		} else {
			$('#overlay').css({
				width : w + "px",
				height : h + "px"
			});
		}
		var temp = $('.overlay_window');
		t = (t - temp.height()) / 2;
		l = (l - temp.width()) / 2;
		temp.css({
			top : t + "px",
			left : l + "px"
		});
	}
};

// 窗体层级管理对象
util.layerIndex = {
	// 定义窗体z-index值的数组，用于管理窗体的层级关系
	Idx : [ 900 ],
	// 定义窗体ID的数组，用于管理存在于页面中的窗体
	layerStor : [],

	/**
	 * 判断是否存在指定id窗体
	 * 
	 * @param {string}
	 *            L_id 窗体id L_id为空时,则判断是否存在窗体
	 *            L_id不为空,则判断是否存在于数组中,若不存在,则将它存入数组
	 */
	hasLayer : function(L_id) {
		if (!L_id) {
			if (util.layerIndex.layerStor.length === 0) {
				return false;
			} else {
				return true;
			}
		} else if ($.inArray(L_id, util.layerIndex.layerStor) != -1) {
			return true;
		} else {
			util.layerIndex.layerStor.push(L_id);
		}
	},

	/**
	 * 获取窗体的z-index值,每调用一次,z-index递增
	 */
	getIdx : function() {
		var zId = this.Idx[this.Idx.length - 1];
		zId = parseInt(zId, 10) + 2;
		this.Idx.push(zId);
		return zId;
	}
};

//判断字符串是否含有特殊字符
util.containSpecial = function(str) {
	var reg = /^[^@\/\'\\\"#$%&\^\*]+$/;
	if (reg.test(str)) {
		return true;
	} else {
		return false;
	}
};

//阻止冒泡和浏览器默认行为
util.stopDefault = function(e) {
	if (e && e.stopPropagation) {
		e.stopPropagation();
	} else {
		// 否则，我们需要使用IE的方式来取消事件冒泡
		e.cancelBubble = true;
	}

	if (e && e.preventDefault) {
		e.preventDefault();
	} else {
		// IE中阻止函数器默认动作的方式
		e.returnValue = false;
	}

	return false;
};

//把serializeArray获取的数据转换成struts能识别的数据格式
util.toFormJson = function(data) {
	var json = {};

	$.each(data, function() {
		//不过滤空值，过滤后数组对应的下标就乱了
		/*if (!this.value) {
			return;
		}
		
		if ($.isNumeric(this.value) && new Number(this.value) == 0) {
			return;
		}*/

		if (json[this.name]) {
			if ($.isArray(json[this.name])) {
				json[this.name].push(this.value);
			} else {
				var values = [ json[this.name] ];
				values.push(this.value);
				json[this.name] = values;
			}
		} else {
			json[this.name] = this.value;
		}
	});

	return json;
};

util.serializeJson = function($from) {
	return util.toFormJson($from.serializeArray());
};

/**
 * @param key 获取字段的key
 * @param data 数据对象
 * @param unit 单位
 */
util.dataMergeCell = function(key, data, unit, align,tipSize) {
	var html = '<ul>';
	unit = unit || '';
	tipSize = tipSize? tipSize : 20;
	var cssStyle = 'style="min-height:25px;"';
	cssStyle = align? 'style="min-height:25px;text-align:'+align+'"' : cssStyle;
	$.each(data, function(i, line) {
		var text = ((line[key] + unit || '') + '').safeDispose();
		var margeCellCalss = 'table-mergeCell-li';
		if(data.length == i + 1){
			margeCellCalss =  "table-mergeCell-li-last" ;
		} 
		//添加_gird_overflow_tip 样式，供字数太多是Tip提示。
		html += '<li class="'+margeCellCalss+'"><div class="_gird_overflow_tip" tipSize="'+tipSize+'" '+cssStyle+'>' + text + '</div></li>';
	});

	return html + '</ul>';
};


util.fileDownload = function(url, $from) {
	url = rootPath + '/' + url;
	// 每次文件下载前清除此cookie信息，防止文件下载一半时用户页面刷新而导致些cookie末正确删除问题
	document.cookie = "excelExport=; expires=" + new Date(1000).toUTCString() + "; path=/" ;

	if ( $('#fileDownFrame').length < 1) {
		$('body')
				.append(
						'<iframe style="display: none;" name ="fileDownFrame" id="fileDownFrame"></iframe>');
	}
	
	var srcUrl = $from.attr('url') || 'UTF-8';
	$from.attr('action', url);
	$from.attr('method', 'post');
	$from.attr('target', 'fileDownFrame');
	$from.submit();
	$from.attr('src', srcUrl);
	
	var loadObj = $.Prompt('<div id="hellogood_loading" class="loading spin"></div>', 0, {
		bg : true
	}); 
	
	var checkFileDownloadComplete = function() {
		window.setTimeout(function(){
			if (document.cookie.indexOf('excelExport=true') != -1) {
				loadObj.fastClose();
				document.cookie = "excelExport=; expires=" + new Date(1000).toUTCString() + "; path=/" ;
			} else {
				checkFileDownloadComplete();
			}
		}, 200); 
	};
	
	checkFileDownloadComplete();
};

util.htmlShow = function(url, $from) {
	var iframe = $('#htmlShowFrame');
	url = rootPath + '/' + url;
	if (iframe.length < 1) {
		$('body')
				.append(
						'<iframe name ="htmlShowFrame" id="htmlShowFrame" height="400" width="1100" style="border: 1px  #EBEAEC solid"></iframe>');
	}

	var srcUrl = $from.attr('url') || 'UTF-8';
	$from.attr('action', url);
	$from.attr('method', 'post');
	$from.attr('target', 'htmlShowFrame');
	$from.submit();
	$from.attr('src', srcUrl);
};

util.load = function(_$, url, success) {

	if (pageLoad) {
		pageLoad.abort();
	}

	pageLoad = util.hellogoodAjax({
		dataType : 'html',
		url : url,
		async : false,
		succFun : function(_html) {
			_$.html(_html);
			$('.datepicker').datepicker();//日期控件初始化
			if (success) {
				success();
			}
		}
	});

};

util.enableElement = function($form){
	$form.find('input').removeAttr('disabled');
	$form.find('select').removeAttr('disabled');
	$form.find('textarea').removeAttr('disabled');
}

util.disabledElement = function($form){
	$form.find('input').attr("disabled","disabled");
	$form.find('select').attr("disabled","disabled");
	$form.find('textarea').attr("disabled","disabled");
};

util.setReadonlyElement = function($form){
	$form.find('input').attr("readonly","readonly");
	$form.find('select').attr("readonly","readonly");
	$form.find('textarea').attr("readonly","readonly");
};

util.hellogoodAjax = function(param) {
	var params = {
		type : 'post',
		timestamp : new Date().getTime(),
		dataType : 'json',
		loading : true,
		async : true
	};
	$.extend(params, param);

	var obj = {};
	$.extend(obj, params);
	obj.url = rootPath + '/' + params.url;
	var loadObj = null;

	if (param.succFun) {
		obj.success = function(resjson) {
			if (params.loading) {
				loadObj.fastClose();
			}
			if (resjson && resjson.error) {
				$.Prompt(resjson.error);
				return;
			}
			param.succFun.apply(null, [ resjson, params ]);
		};
	}
	if (params.errFun) {
		obj.error = function(request, textStatus, errorThrown) {
			if (params.loading) {
				//移除小菊花
				//$('#hellogood_loading').remove();
				loadObj.fastClose();
			}
			
			param.errFun.apply(null,
					[ request, textStatus, errorThrown, params ]);
		};
	}

	obj.complete = function(request, textStatus) {

		if (request.responseText) {
			var istimeout = (params.dataType.toLowerCase() == "json")
					&& (request.responseText.indexOf('<!DOCTYPE html PUBLIC') != -1);

			if (istimeout) {
				top.window.location.href = rootPath + "/login.do";
			}
            if(request.responseText.indexOf('<title>后台登陆</title>') > -1){
                if (window != top)
                    top.location.href = location.href;
            }
		}
		
		if (params.tokenKey) {
			delete hellogoodToken[params.tokenKey];
		}

		if (params.complete) {
			param.complete.apply(null, [ request, textStatus, params ]);
		}


	};

	if (params.loading) {
		loadObj = $.Prompt('<div id="hellogood_loading" class="loading spin"></div>', 0, {
			bg : true
		}); 
		
	}
	/* 暂时屏蔽，待测试人员提出相应需求后再改
	if (params.gridLoading){
		if ($('#'+params.gridLoading+'_msgbody').length < 1) {
			$('#' + params.gridLoading).css('position', 'relative').append('<div > </div><div id="'+params.gridLoading+'_msgbody" style="position:absolute; width:100%; height:100%; top:0;" ><div style="width:200px;" id="'+params.gridLoading+'_dialog" class="grid_floatBox" ><div id="'+params.gridLoading+'_content" class="gridcontent"><div  class="loading spin"></div></div></div></div>');
		}
	}*/
	
	if (!hellogoodToken[params.tokenKey]) {
		return $.ajax(obj);
	}
};

/**
 * *********************** 以下为自定义类和原生方法扩展
 */
String.prototype.trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/g, "");   
};

//判断字符串长度是否超出范围  n 字符个数(1个汉字占2字符,gbk编码)
String.prototype.isOutSize = function(n) {
	var w = 0;
	for ( var i = 0; i < this.length; i++) {
		var c = this.charCodeAt(i);
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
			w++;
		} else {
			w += 2;
		}
	}
	return w > n;
};

String.prototype.safeDispose = function() {
	var specialCharacts = {
		'&' : '&amp;',
		'\'' : '&#39;',
		'\"' : '&quot;',
		'<' : '&lt;',
		'>' : '&gt;'
	};
	var value = this.toString();

	for ( var key in specialCharacts) {
		value = value.replace(new RegExp(key, 'g'), specialCharacts[key]);
	}

	return value;
};

/*
 * 截取字符串长度 n 字符个数
 */
String.prototype.substringName = function(n) {
	if (this.isOutSize(n)) {
		var w = 0;
		var str = '';
		for ( var i = 0; i < this.length; i++) {
			var c = this.charCodeAt(i);
			if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
				w++;
			} else {
				w += 2;
			}
			if (w > n) {
				break;
			}
			str += this.charAt(i);
		}
		return str + "...";
	} else {
		return this.toString();
	}
};

// 自定义的对象,模拟java的stringBuilder类
function StringBuilder(str) {
	this._stringBuilder = [];
	if (str)
		this._stringBuilder.push(str);
}
StringBuilder.prototype = {
	append : function(str) {
		this._stringBuilder.push(str);
		return this;
	},
	empty : function() {
		this._stringBuilder = [];
	},
	toString : function() {
		return this._stringBuilder.join('');
	}
};

//对Date的扩展，将 Date 转化为指定格式的String   
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
//例子：   
//(new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
//(new Date()).format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, //月份   
		"d+" : this.getDate(), //日   
		"h+" : this.getHours(), //小时   
		"m+" : this.getMinutes(), //分   
		"s+" : this.getSeconds(), //秒   
		"q+" : Math.floor((this.getMonth() + 3) / 3), //季度   
		"S" : this.getMilliseconds()
	//毫秒   
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

Date.prototype.getWeekFirstDay = function() {
	var _this = this;
	if (this.getDay() == 0) {
		_this = new Date(this).addDay(-1);
	}
	return new Date(_this - (_this.getDay() - 1) * 86400000);
};

Date.prototype.getWeekLastDay = function() {
	var _this = this;
	if (this.getDay() == 0) {
		_this = new Date(this).addDay(-1);
	}
	return new Date((_this.getWeekFirstDay() / 1000 + 6 * 86400) * 1000);
};

Date.prototype.getMonthFirstDay = function() {
	return new Date(this.getFullYear(), this.getMonth(), 1);
};

Date.prototype.getMonthLastDay = function() {
	var monthNextFirstDay = new Date(this.getFullYear(), this.getMonth() + 1, 1);
	return new Date(monthNextFirstDay - 86400000);
};

Date.prototype.addDay = function(day) {
	var date = new Date(this);
	date.setDate(date.getDate() + day);
	return date;
};

Date.prototype.addWeek = function(week) {
	return new Date(this.getTime() + 86400000 * 7 * week);
};

Date.prototype.weekOfYear = function() {
	var totalDays = 0;
	years = this.getFullYear();

	if (years < 1000) {
		years += 1900;
	}

	var days = new Array(12);
	days[0] = 31;
	days[2] = 31;
	days[3] = 30;
	days[4] = 31;
	days[5] = 30;
	days[6] = 31;
	days[7] = 31;
	days[8] = 30;
	days[9] = 31;
	days[10] = 30;
	days[11] = 31;

	//判断是否为闰年，针对2月的天数进行计算
	if (Math.round(this.getFullYear() / 4) == this.getFullYear() / 4) {
		days[1] = 29;
	} else {
		days[1] = 28;
	}

	if (this.getMonth() == 0) {
		totalDays = totalDays + this.getDate();
	} else {
		var curMonth = this.getMonth();

		for ( var count = 1; count <= curMonth; count++) {
			totalDays = totalDays + days[count - 1];
		}

		totalDays = totalDays + this.getDate();
	}

	//得到第几周
	var week = Math.round(totalDays / 7) + 1;
	return week;
};

/**
 * *********************** 以下为公用方法
 */
// 假期数据获取
var holidaysInit = function() {
	var curDate = new Date();
	var start = curDate.getMonthFirstDay().addDay(-1).getMonthFirstDay(); 
	var end = curDate.getMonthLastDay().addDay(1).getMonthLastDay(); 

	util.hellogoodAjax({
		url : 'core/holiday/getHolidayType.koala',
		async : false,
		data : {
			startDate : start.format('yyyy-MM-dd'),
			endDate : end.format('yyyy-MM-dd')
		},
		succFun : function(json) {
			json = json.data;
			var dataObj = {};
			for (var key in json) {
				dataObj[key] = json[key].type;
			}

			hellogoodHolidays = dataObj;
		}
	});
};

String.prototype.getByteLen = function () {
	var val = this;
    var len = 0; 
    for (var i = 0; i < val.length; i++) { 
        if (val[i].match(/[^x00-xff]/ig) != null) //全角 
            len += 2; 
        else
            len += 1; 
    }; 
    return len; 
}; 

$.fn.tip = function(config){
	var $this = $(this);
	var cfgs = {
		className: 'tip-yellowsimple',
		showTimeout: 10,
		offsetY: 5,
		offsetX: 5,
		alignX: 'center',
		alignY: "bottom",
		alignTo: 'target'
	};
	$.extend(cfgs,config);
	$this.poshytip(cfgs);
};

util.selectPadData = function(domMsgs, json) {
    $.each(domMsgs, function(i, dom) {
        if (json[dom.jsonObj]) {
            var $dom = $('#' + dom.domId);
            $.each(json[dom.jsonObj], function(i, data) {
                if (data) {
                    if (dom.exclude && dom.exclude == data[dom.key]) {
                        return;
                    }
                    var value = data[dom.value || 'name'].safeDispose();

                    $dom.append('<option title="' + value + '" value="'
                        + data[dom.key || 'id'] + '">'
                        + value.substringName(30) + '</option>');
                }
            });
        }
    });
};

util.isBlank = function(str){
    if(str == null || str == ''){
        return true;
    }
}

util.isNotBlank = function(str){
    return !util.isBlank(str);
}

function isWysiwygareaAvailable() {
	if ( CKEDITOR.revision == ( '%RE' + 'V%' ) ) {
		return true;
	}
	return !!CKEDITOR.plugins.get( 'wysiwygarea' );
}
util.initEditor = function(domId,width,height){
	if ( CKEDITOR.env.ie && CKEDITOR.env.version < 9 ){
		CKEDITOR.tools.enableHtml5Elements( document );
	}
	if(height!=''&&height!=0){
		CKEDITOR.config.height = height;
	}
	if(width!=''&&width!=0){
		CKEDITOR.config.width = width;
	}
	var wysiwygareaAvailable = isWysiwygareaAvailable(),
	isBBCodeBuiltIn = !!CKEDITOR.plugins.get( 'bbcode' );
	var editorElement = CKEDITOR.document.getById(domId);
	if ( wysiwygareaAvailable ) {
		CKEDITOR.replace(domId);
	} else {
		editorElement.setAttribute( 'contenteditable', 'true' );
		CKEDITOR.inline(domId);
	}
}
util.editComfig = function(domId,config,width,height){
	
	if ( CKEDITOR.env.ie && CKEDITOR.env.version < 9 ){
		CKEDITOR.tools.enableHtml5Elements( document );
	}
	if(height!=''&&height!=0){
		CKEDITOR.config.height = height;
	}
	if(width!=''&&width!=0){
		CKEDITOR.config.width = width;
	}
//	CKEDITOR.replace(domId,{
//	    customConfig : '../../js/ckeditor/'+config+'_config.js'
//	});
	var wysiwygareaAvailable = isWysiwygareaAvailable(),
	isBBCodeBuiltIn = !!CKEDITOR.plugins.get( 'bbcode' );
	var editorElement = CKEDITOR.document.getById(domId);
	if ( wysiwygareaAvailable ) {
		CKEDITOR.replace(domId,{
			customConfig : '../../js/ckeditor/'+config+'_config.js'
		});
	} else {
		editorElement.setAttribute( 'contenteditable', 'true' );
		CKEDITOR.inline(domId);
	}
}

util.removeHTMLTag = function(str) {
    str = str.replace(/<\/?[^>]+>/g, '');
    str = str.replace(/\&[a-z]+;/gi, '');
    return str;
}

util.role = {
	//客服
	isCustomerService : function(){
		var roleCodes  = $.cookie('roleCodes');
		if(util.isBlank(roleCodes)) return false;
		return roleCodes.indexOf('customerService') >= 0;
	},

	//系统管理员
	isSysadmin : function(){
		var roleCodes  = $.cookie('roleCodes');
		if(util.isBlank(roleCodes)) return false;
		return roleCodes.indexOf('sysadmin') >= 0;
	},

	//客服经理
	isCustomerManager : function(){
		var roleCodes  = $.cookie('roleCodes');
		if(util.isBlank(roleCodes)) return false;
		return roleCodes.indexOf('customerManager') >= 0;
	},

	//UAT 测试验收
	isUat : function(){
		var roleCodes  = $.cookie('roleCodes');
		if(util.isBlank(roleCodes)) return false;
		return roleCodes.indexOf('uat') >= 0;
	}
}












