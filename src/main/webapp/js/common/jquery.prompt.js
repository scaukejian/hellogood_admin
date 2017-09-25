/**
 *  消息弹出框，如有需要，如有美工mm大力支持，可提供带图标消息提示
 * 
 */
(function($){
		$.extend({
			Prompt:function(con,t,ops){
				var PromptBox = {
					defaults : {
						name     :	"T"+ new Date().getTime() + Math.floor(Math.random()*10+1) + '_Promptbc',
						content  :"调皮了，居然不传提示语!",							//弹出层的内容(text文本、容器ID名称、URL地址、Iframe的地址)
						width    : 200,								 	            //弹出层的宽度
						height   : 70,							
						time     : 2000,                                            //设置自动关闭时间
						bg       :false
					},
					timer:{
						stc:null,
						clear:function(){
							if(this.st)clearTimeout(this.st);
							if(this.stc)clearTimeout(this.stc);
							}
					},
					config:function(def){
						this.defaults = $.extend(this.defaults,def);
					},
					created:false,
					create : function(op){
						this.created=true;
						var ops = $.extend({},this.defaults,op);
						this.element = $("<div class='Prompt_floatBoxBg' id='fb"+ops.name+"'></div><div class='Prompt_floatBox' id='"+ops.name+"'><div class='content'></div></div>");
						$("body").prepend(this.element);
						this.blank = $("#fb"+ops.name);						    //遮罩层对象
						this.content = $("#"+ops.name+" .content");				//弹出层内容对象
						this.dialog = $("#"+ops.name);						    //弹出层对象
						if ($.browser && ($.browser.version == "6.0") && !$.support.style) {//判断IE6
							this.blank.css({height:$(document).height(),width:$(document).width()});
						}
					},
					show:function(op){
						this.dialog.show();
						var ops = $.extend({},this.defaults,op);
						this.content.css({'min-width':ops.width});
						this.content.html(ops.content);
						var Ds = {
								   width:this.content.outerWidth(true),
								   height:this.content.outerHeight(true)
								   };
						if(ops.bg){
							this.blank.show();
							this.blank.animate({opacity:"0.10"},"normal");		
						}else{
							this.blank.remove();
							this.blank.css({opacity:"0"});
						}
						this.dialog.css({
										width:Ds.width,
										height:Ds.height,
										left:(($(document).width())/2-(parseInt(Ds.width)/2)-5)+"px",
										top:((window.screen.height-parseInt(Ds.height))/2)+"px"
										//去掉计算滚动条位置
										//top:(($(window).height()-parseInt(Ds.height))/2+$(document).scrollTop())+"px"
										});
						if ($.isNumeric(ops.time)&&ops.time>0){//自动关闭
							this.timer.clear();
							var DB = this;
							this.timer.stc = setTimeout(function (){			
								DB.close();
							},ops.time);	
						}
					},
					close:function(){
						var DB = this;
							DB.blank.animate({opacity:"0.0"},
											 "normal",
											 function(){
												DB.blank.remove();
												DB.dialog.remove();	
											  });		
							DB.timer.clear();
					},
					fastClose:function(){
						var DB = this;
						DB.blank.remove();
						DB.dialog.remove();		
						DB.timer.clear();
					}
				};
				
				// 新建前销毁所有弹出框
				$("div[id$='_Promptbc']").remove();
				// 创建弹出框
				PromptBox.create(ops);
				
				if($.isPlainObject(con)){
					if(con.close){
						PromptBox.close();
					}else if (con.fastClose){
						PromptBox.fastClose();
					} else {
						PromptBox.config(con);
					}
				} else {
					ops = $.extend({},PromptBox.defaults,ops,{time:t});
					ops.content = con||ops.content;
					PromptBox.show(ops);
				}
				
				return PromptBox;
			}
		});  	 
})(jQuery);