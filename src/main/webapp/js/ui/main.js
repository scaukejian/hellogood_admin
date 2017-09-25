
$(document).ready(
		function() {
			jQuery.jq51menu = function(menuboxid, submenu) {
				var menuboxli = $(menuboxid + " li");
				var menuboxa = $(menuboxid + " li a");
				menuboxli.eq(0).find(submenu).show();
				menuboxa.eq(0).addClass("over");
				menuboxli.find("a:first").attr("href", "javascript:;");
				menuboxli.click(function() {
					$(this).addClass("thismenu").find(submenu).show().end()
							.siblings("li").removeClass("thismenu").find(
									submenu).hide();
				});
				menuboxa.click(function() {
					menuboxli.find("a").removeAttr("class");
					$("#message").show(); // loading
					$("#mainFrame").attr("src",$(this).attr("data"));
					 //iframe 加载页面
					$("#mainFrame").hide();
					$(this).addClass("over");
					$("#modelname").text($(this).text());
				});
			};
			$("#mainFrame").load(function() {
				$("#message").hide();
				$("#mainFrame").show();
			});
			//调用方法，可重用
			$.jq51menu("#menubox", "li.submenu");
		});



///自适应高度
 function resizeFrame() {
  try{
	$('#mainFrameContainer').height(document.documentElement.clientHeight-137);
  }catch(e){}
 }
 $(window).load(function() {
  resizeFrame();
 });
 $(window).resize(function() {
  resizeFrame();
 });

//折合tree
	$(document).on('click','.MiddleFrame',function(){
		$('.left-part').hide();
		$('.rightCon').css('marginLeft','10px');
		$('.MiddleFrame').attr('class','tree-sj');
	});
	
	$(document).on('click','.tree-sj',function(){
		$('.left-part').show();
		$('.rightCon').css('marginLeft','210px');
		$('.tree-sj').attr('class','MiddleFrame');
	});



//日历控件
     //$('#datepicker,#datepicker1').datepicker();
//	$(function(){
//		$('.datepicker').datepicker();
//	});


//----双---------
//	$(function () {
//			var nowTemp = new Date();
//			var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
//			 
//			var checkin = $('#dpd1').datepicker({
//			  onRender: function(date) {
//				return date.valueOf() < now.valueOf() ? 'disabled' : '';
//			  }
//			}).on('changeDate', function(ev) {
//			  if (ev.date.valueOf() > checkout.date.valueOf()) {
//				var newDate = new Date(ev.date)
//				newDate.setDate(newDate.getDate() + 1);
//				checkout.setValue(newDate);
//			  }
//			  checkin.hide();
//			  $('#dpd2')[0].focus();
//			}).data('datepicker');
//			
//			var checkout = $('#dpd2').datepicker({
//			  onRender: function(date) {
//				return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
//			  }
//			}).on('changeDate', function(ev) {
//			  checkout.hide();
//			}).data('datepicker');
//        });
	







