(function(){
	/**
	 * 分页工具组件 
	 * 
	 * @param config{
	 *            renderTo   : '要追加到dom节点的id', 
	 *            toal       : '数据总数', 
	 *            pageLength : '显示的长度，如果为7就只显示1~7长度的分页值'
	 *            pageSize   : '每页显示的数据条数'，
	 *            subHandler : '分页提交的回调函数'
	 *       }
	 *       
	 * @author fb
	 */
	window.hellogood.ui.page = function(params){
		var confs = {
				renderTo      :  'body',
				width         :  '100%',
				toal          :  0,
				maxButtons    :  6,
				pageSize 	  :  20,
				subHandler    : function(){}
		};
		var conf = $.extend(confs, params);
		var version = null;
		var $render = null;
		var currPge = 1;
		var pageToal = null; 
		var $input = null;     //input输入框
		var $ul = null;        //点击数字标签
		var pageInfo = null;   //页数和当前页信息

		var init = function(){
			version = "version".concat(new Date().getTime());
			conf.toal = 0;
			currPge = 1;
			pageToal = null; 
			pageInfo = null; 
			$render = $('#'.concat(conf.renderTo));
			createHtml();
			initEventListen();
			renderInit(currPge);
		};
		
		
		var createHtml = function(){
			
			$render.html('<ul class="pagination"> ' + 
					     	'<li><span>每页' + 
					     	'<select id="'+ getId('select') +'"> ' +
				     	 	'<option value="1000000">全部</option>' +
					       	'<option value="5">5</option>' +
					       	'<option value="10">10</option>' +
					       	'<option value="20">20</option>' +
					       	'<option value="50">50</option>' +
					       	'<option value="100">100</option>' +
					    '</select>' +
				     	'条</span></li> ' + 
				     	'<li><span id="'+getId('pageInfo')+'">共0条&nbsp;第1/0页</span></li> ' +
				     	'<li><a id="'+getId('paging_frist')+'"  href="javascript:void(0);"> 首页 </a></li>' + 
				     	'<li><a id="'+getId('paging_up')+'"  href="javascript:void(0);"> 上一页 </a></li>' + 
				     	'<li id="'+getId('paging_aClick')+'"></li>' + 
				     	'<li><a id="'+getId('paging_next')+'"  href="javascript:void(0);"> 下一页 </a></li>' + 
				     	'<li><a id="'+getId('paging_last')+'" href="javascript:void(0);"> 尾页</a></li>' +
			         	'<li><input type="text" title="跳转页" class="form-control" id="'+getId('paging_input')+'"></li>' +
				     	'<li><a title="跳转页" href="javascript:void(0);" id="'+getId('paging_go')+'" >Go</a></li>'+
				     '</ul>');
			
			$input = $('#' + getId('paging_input'));
			$ul = $('#' + getId('paging_aClick'));
			
			if(!conf.total || conf.total == 0) {
				$render.hide();
			}
			
			pageInfo = $('#' + getId('pageInfo'));
		};
		
		var createNumDom = function(){
			var  start = Math.max(1, currPge - parseInt(conf.maxButtons/2));
			var  end = Math.min(pageToal, start + conf.maxButtons - 1);
			start = Math.max(1, end - conf.maxButtons + 1);
			
			for(var i = start; i <= end; i++){
				createAdom(i);
			};
		};
		
		var createAdom = function(index){
			if (currPge == index) {
				$ul.append('<a style="background-color: #3FABFB;border-color: #3F91DD;">'+currPge+'</a>');
			} else {
				$ul.append('<a href="javascript:void(0);" id="'+getId('a_' + index)+'">'+index+'</a>');
			}
		};
		
		var initEventListen = function(){
			//select选择
			$('#'+getId('select')).val(conf.pageSize).unbind('change').bind('change',function(e){
				if(this.value != conf.pageSize) {
					conf.pageSize = new Number(this.value);
					gotoPage(1);
				}
			});
			
			
			//首页
			$('#'+getId('paging_frist')).unbind('click').bind('click',function(e){
				if(currPge != 1) {
					gotoPage(1);
				}
			});
			
			//上一页
			$('#'+getId('paging_up')).unbind('click').bind('click',function(e){
				if(currPge > 1) {
					gotoPage(currPge - 1);
				}
			});
			
			//下一页
			$('#'+getId('paging_next')).unbind('click').bind('click',function(e){
				if(currPge < pageToal) {
					gotoPage(currPge + 1);
				}
			});
			
			//尾页
			$('#'+getId('paging_last')).unbind('click').bind('click',function(e){
				if(currPge != pageToal) {
					gotoPage(pageToal);
				}
			});

			//只能输入数字
			$input.unbind('keyup').bind('keyup',function(e){
				this.value = this.value.replace(/\D/g,'');
			}).focus(function(){
				$input.select();
			});
			
			//go 按钮
			$('#'+getId('paging_go')).unbind('click').bind('click',function(e){
				var value = $input.val();
				
				if (value > pageToal || value < 1 || value == currPge) {
					return ;
				}
				
				gotoPage(value);
			});

		};
		
		var numAEventListen = function(){
			$('a[id^="'+getId('a_')+'"]').unbind('click').bind('click',function(e){
				var page = this.id.split('_')[1];
				gotoPage(page);
				return false;
			});
		};
		
		var gotoPage = function(page){
			conf.subHandler.call(null, {currNo:page, pageSize : conf.pageSize});
			renderInit(page);
		};
		
		var renderInit = function(page){
			pageToal = Math.ceil(conf.toal/conf.pageSize);    //总页数
			currPge = new Number(page);
			$ul.html('');
			createNumDom();
			numAEventListen();
			pageInfo.html('共'+ conf.toal +'条&nbsp;&nbsp;第'+ currPge +'/'+ pageToal +'页');
		};
		
		var getId = function(srcId){
			return version.concat(srcId);
		};
		
		var render = function(total,page){
			if(conf.toal != total || page == 1) {
				if(!total || total == 0) {
					conf.toal = 0;
					$render.hide();
					return;
				} else {
					$render.show();
				}
				
				conf.toal = total;
				currPge = page ? page : 1;
				pageToal = Math.ceil(conf.toal/conf.pageSize);    //总页数
				$('#' + getId('paging_total')).html(pageToal);
				renderInit(currPge);
			}
		};
		
		return {
			render   :  render,
			init     :    init
		};
	};
})();