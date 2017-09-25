/*
 * msgBox ：弹出框组件
 * 
 * 输入参数{
 *        title 
 *        width :650,	
 *        leftBtn : false,            //左下按钮是否显示(默认否) 
 *        middleBtn : true,           //按钮是否居中  （默认：否）  
 * 		  html :"",                   //弹出框内容
 * 		  btnName :["确定","取消"],    // 右下按钮名称(默认没有)
 * 		  okHandle:                   //点击确定回调方法
 * 		  cancelHandle:               //点击取消回调方法
 * 		  creatHandle:                //右下按钮点击事件回调方法
 *        }
 * 
 * 
 * 
 * maskDiv ：屏遮层的控制方法，控制屏遮层的显示，管理和开关
 *     |- html ： 屏遮层的html代码。
 *     |- hasMask ：屏遮层的开关，控制只允许页面上出现一个屏遮层。
 *     |- show ：屏遮层的显示方法，可以传入一个值str，在屏遮层上显示loading。
 *     |- close ：屏遮层的关闭方法。
 *     
 * layerIndex ：窗体层级管理，用于管理多窗体共存时的层级调度
 *     |- Idx ： 当前文档的所有窗体z-index值的数组，用于管理窗体的层级关系
 *     |- layerStor ：当前所有窗体ID的数组，用于管理存在于页面中的窗体
 *     |- getIdx ：获取窗体z-index值，每调用一次就递增一次，理论上来说无限往上增加
 * 
 */

(function() {

	var util = window.hellogood.util;
	var UI = window.hellogood.ui;

    UI.msgBox = function(para) {

		var $html = '';
		var p = {};

		var init = function() {
			p = {
				id : 'msgBox_',
				title : "系统消息",
				width : 650,
				height: 750,
				url   : null,
				Btn : false, // 右下角是否有按钮
				masked : true, // 默认显示遮罩层
				html : "",
				appendTo : "body", // 默认添加窗体到body
				btnName : ['确定','取消'],
				closeIcon : false,
				okHandle : function() {
				},
				cancelHandle : function() {
					distroy();
				}, 
				closeBox : function() {
					distroy();
				}
			};
			$.extend(p, para);// 用传入参数覆盖默认值
			// 判断参数里面是否已经有zIdx，如果没有，就重新获取下
			if (!para.hasOwnProperty("zIdx")) {
				p.zIdx = util.layerIndex.getIdx();
				// 判断参数里是否有传入id值，如果没有，就给p.id添加个zIdx作为区别
				if (!para.hasOwnProperty("id")) {
					p.id += p.zIdx;
				}
			}

			if(p.url){
				$.ajax({
					dataType  : 'html',
					url       : rootPath + '/' +   p.url,
					async     :  false,
					success   : function(_html){
						p.html = _html;
						$html = $(getHtml());// 存储主窗体对象
					}
				});
			} else {
				$html = $(getHtml());// 存储主窗体对象
			}
			
		};

		var getHtml = function() {
			// 标题
			var headHtml = new StringBuilder();
			// 底部
			var bottomHtml = new StringBuilder();
			// 主体
			var bHtml = new StringBuilder();

			//headHtml.append('<span><a  id="').append(p.id).append('_close"  href="javascript:void(0);"><img src="'+rootPath+'/images/hellogood/close.png" border="0"></a></span>').append(p.title);
			//headHtml.append('<div class="title"><a style="text-align: right" id="').append(p.id).append('_close"  href="javascript:void(0);"><img src="'+rootPath+'/images/hellogood/close.png" border="0"></a>').append(p.title).append("</div>");
			//headHtml.append('<div class="l-dialog-tc-inner"><div class="l-dialog-title" style="cursor: move;">'+p.title+'</div><div class="l-dialog-winbtns" style="width: 22px;"><a  id="').append(p.id).append('_close"  href="javascript:void(0);"><div class="l-dialog-winbtn l-dialog-close"></div></a></div></div>');
			headHtml.append('<div class="Title-blue-msgbox"><h4><b>'+p.title+'</b></h4><span><a id="'+p.id+'_close" class="ftr close" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i></a></span></div>');
        
			// 如果有2个按钮
			if (p.Btn && p.btnName.length == 2) {
				bottomHtml.append('<input id="').append(p.id).append('_ok" name="" type="button" class="btn btn-blue" value="').append(p.btnName[0]).append('">').append('&nbsp;&nbsp;')
				          .append('<input id="').append(p.id).append('_cancel" name="" type="button" class="btn btn-blue" value="').append(p.btnName[1]).append('">');
				
			} else if (p.Btn && p.btnName.length == 1) {
				bottomHtml.append('<input id="').append(p.id).append('_ok" name="" type="button" class="btn btn-blue" value="').append(p.btnName[0]).append('">');
			}

			bHtml.append('<div id="').append(p.id).append('" class="windows blue-msgbox-window').append('" style="width:').append(p.width)
			     .append('px; position:fixed;background:#F3F9FC;">').append('<div id="'+p.id+'_title" class="win_title">').append(headHtml.toString())
			     .append('</div><div style="overflow:auto;" id="'+p.id+'_div">').append(p.html).append('</div>');
			
			if (p.Btn && p.middleBtn) {
				bHtml.append('<div id="'+p.id+'_winbut" style="text-align:center; margin:5px auto;">');
			} else {
				bHtml.append('<div id="'+p.id+'_winbut">');
			}
			bHtml.append(bottomHtml.toString()).append('</div></div>');
			return bHtml.toString();
		};

		var appendToBody = function() {
			var $h = $html;
			$h.appendTo("body");
			$h.css(sty($h));
			util.layerIndex.hasLayer(p.id);
		};

		var handle = function() {
			var ar = [];
			ar.push(
			// 关闭
			{
				objSelector : "#" + p.id + "_close",
				action : "click",
				handle : function() {
					p.closeBox();

				}
			},
			// 确定
			{
				objSelector : "#" + p.id + "_ok",
				action : "click",
				handle : function() {
					p.okHandle();
				}
			});
			
			// 如果有2个按钮，则把另一个按钮事件绑定
			if (p.btnName.length == 2) {
				var cancelBtnObj = {
					objSelector : "#" + p.id + "_cancel",
					action : "click",
					handle : function() {
						p.cancelHandle();
					}
				};
				ar.push(cancelBtnObj);
			}
			
			bindFun(ar);
		};

		/**
		 * 绑定事件
		 * 
		 * @param {Array}
		 *            Ar 存储跟事件相关信息的数组 Ar数组存储的元素包含的属性具体描述如下:
		 *            objSelector:需要绑定事件的jquery对象 action:事件名称 handle:事件处理函数 例如
		 *            Ar =
		 *            [{objSelector:"#b",action:"click",handle:function(){}}]
		 */
		var bindFun = function(Ar) {
			for ( var i = 0; i < Ar.length; i++) {
				$(Ar[i].objSelector).bind(Ar[i].action, Ar[i].handle);
			}
		};

		// 显示窗体
		var show = function() {
			if ($('#' + p.id).length) {
				$('#' + p.id).show();
			} else {
				if (p.appendTo == 'body') {
					appendToBody();
				} else {
					appendToId(p.appendTo);
				}
				handle();// 添加事件
			}
			
			$('#'+p.id+'_div').height(p.height - $('#'+p.id+'_title').height() - $('#'+p.id+'_winbut').height()- 20).width(p.width - 2);
			
			if (p.masked) {
				util.maskDiv.show(p.id);
			}
			if(p.closeIcon){
				$("#" + p.id + "_close").hide();
			}
		};

		// 隐藏窗体
		var hide = function() {
			if ($('#' + p.id).length) {
				$('#' + p.id).hide();
			}
			if (p.masked) {
				util.maskDiv.hide();
			}
		};

		// 无条件销毁窗体的方法
		var distroy = function() {
			$html.remove();
			// 获取ID名为b的元素在数组pe.util.layerIndex.layerStor中的位置，从0开始
			var i = $.inArray(p.id, util.layerIndex.layerStor);
			// 在数组pe.util.layerIndex.layerStor中删除ID名为b的元素。这里用于回收数组存储空间。
			util.layerIndex.layerStor.splice(i, 1);
			// 判断是否需要屏遮层，不要就关闭。
			util.maskDiv.hide(p.id);
		};

		// 获取窗体的相关css属性
		var sty = function($h) {
			var util = window.hellogood.util;
			var w,h,bottom, right, zIndex;
			w = p.width;
			h = p.height || $html.height();
			h = p.height == 'auto' ? $html.height()/2 : h;

			bottom = (util.iGet.PageSize().WinH - h) / 2 ;
			right = (util.iGet.PageSize().WinW - w) / 2 - util.iGet.PageScroll().X;

			zIndex = p.zIdx;
			$('#' + p.id).css({
				width : p.width,
				height : p.height
			});
			return {
//				bottom : bottom,
				top:'20px',
				right : right,
				zIndex : p.zIdx
			};
		};

		var setTitle = function(title) {
			var $title = $('div#' + p.id + ' span:eq(0)');
			if ($title.length) {
				$title.text(title);
			}
		};
		
		var getContentDiv = function() {
			return $('#' + p.id + '_div');
		};

		init();

		return {
			show 		     : show,
			hide 		     : hide,
			getContentDiv    : getContentDiv,
			close 		     : distroy,
			setTitle         : setTitle,
			getWinBody       : function() {
				return p.html;
			},
			getNewWinBody: function(){
				return $('#' + p.id+'_div').html();
			}
		};
	};
	
	UI.dialogBox = function(params){
		params.style = params.style || 'tips';
		
		var dialogBox = UI.msgBox({
				title     :    params.title ? params.title : '系统提示',
				width     :    410,
				height    :    'auto',
				html      :    '<div style="overflow:visible;text-align:center; background:white;height:60px;width:auto;"><div class="msgbox_'+params.style+'" style="overflow:visible;"></div><h4><b>'+params.html+'</b></h4></div>' ,
				Btn       :    true, 
				btnName   :    params.btnName || ['确定','取消'],
				middleBtn :    true,     
				okHandle  :    function(){
									dialogBox.close();
									
									if(params.okHandle){
										params.okHandle();
									}
								}
			});
		
		dialogBox.show();
	};
	
	UI.dialogBoxTip = function(params){
		params.style = params.style || 'tips';
		
		var dialogBox = UI.msgBox({
				title     :    params.title ? params.title : '系统提示',
				width     :    params.width ? params.width : 410,
				height    :    params.heigth ? params.heigth : 'auto',
				html      :    '<div style="overflow:visible;text-align:center; background:white;height:60px;width:auto;"><div class="msgbox_'+params.style+'" style="overflow:visible;"></div><h4><b>'+params.html+'</b></h4></div>' ,
				Btn       :    params.Btn, 
				btnName   :    params.btnName || ['确定','取消'],
				middleBtn :    true,     
				okHandle  :    function(){
									dialogBox.close();
									
									if(params.okHandle){
										params.okHandle();
									}
								}
			});
		
		dialogBox.show();
	};

    UI.dialogBoxComfirm = function(params){
        params.style = params.style || 'tips';

        var dialogBox = UI.msgBox({
            title     :    params.title ? params.title : '系统提示',
            width     :    410,
            height    :    params.height ? params.height : 'auto',
            html      :    '<div style="overflow:visible;text-align:center; background:white;height:'+params.height+'px;width:auto;"><div class="msgbox_'+params.style+'" style="overflow:visible;"></div><h4><b>'+params.html+'</b></h4></div>' ,
            Btn       :    true,
            btnName   :    params.btnName || ['确定','取消'],
            middleBtn :    true,
            okHandle  :    function(){
                if(params.okHandle){
                    params.okHandle();
                }
            }
        });
        dialogBox.show();
        return dialogBox;
    };

	UI.dialogBox2 = function(params){
		params.style = params.style || 'tips';
		var height = "auto";
		if(params.height==null||params.height==''){
			 
		}else{
			height =params.height;
		}
		var dialogBox = UI.msgBox({
				title     :    params.title ? params.title : '系统提示',
				width     :    410,
				height    :    'auto',
				html      :    '<div style="overflow:visible;text-align:center; background:white;height:'+height+';width:auto;"><div class="msgbox_'+params.style+'" style="overflow:visible;"></div><h4><b>'+params.html+'</b></h4></div>' ,
				Btn       :    true, 
				btnName   :    params.btnName || ['确定','取消'],
				middleBtn :    true,     
				okHandle  :    function(){
								if($("#remarksH") && $("#user_remarks")){
									$("#remarksH").val($("#user_remarks").val());
								}

									if(params.okHandle){
										params.okHandle();
									}
                                    dialogBox.close();

                }
			});
		
		dialogBox.show();
	};

})();
