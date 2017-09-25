	//全局变量，触摸开始位置
	var startX = 0, startY = 0;
	i = null;
	// touchstart事件
	function touchSatrtFunc(evt) {
		try {
			var touch = evt.touches[0]; // 获取第一个触点
			var x = Number(touch.pageX); // 页面触点X坐标
			var y = Number(touch.pageY); // 页面触点Y坐标
			// 记录触点初始位置
			startX = x;
			startY = y;
		} catch (e) {
			console.log('touchSatrtFunc：' + e.message);
		}
	}
	
	// touchmove事件，这个事件无法获取坐标
	function touchMoveFunc(evt) {
		try {
			// evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等
			var touch = evt.touches[0]; // 获取第一个触点
			var x = Number(touch.pageX); // 页面触点X坐标
			var y = Number(touch.pageY); // 页面触点Y坐标
			// 判断滑动方向
			var sum = x - startX;
			if (sum < 0) {
				var parentLi = i.parent("div").parent("div").next();
				//if (parentLi.length == 0) parentLi = $(".gallery li:first-child");
				parentLi.children("div").find("a").trigger("click");
			} else if (sum > 0) {
				var parentLi = i.parent("div").parent("div").prev();
				//if (parentLi.length == 0) parentLi = $(".gallery li:last-child");
				parentLi.children("div").find("a").trigger("click");
			}
		} catch (e) {
			console.log('touchMoveFunc：' + e.message);
		}
	}
	// 绑定事件
	function bindEvent() {
		document.getElementById("zoom").addEventListener('touchstart',
				touchSatrtFunc, false);
		document.getElementById("zoom").addEventListener('touchmove',
				touchMoveFunc, false);
	}
	// 判断是否支持触摸事件
	function isTouchDevice() {
		try {
			document.createEvent("TouchEvent");
			bindEvent(); // 绑定事件
		} catch (e) {
			console.log("不支持TouchEvent事件！" + e.message);
		}
	}

	function showPhoto(){
		$("#zoom").remove();
		$("body").append('<div id="zoom"><a class="close"></a><div class="content loading"></div></div>');
		var zoomDiv = $("#zoom"),
		contentDiv = $("#zoom .content"),
		r = false,
		windowWidth = $(window).width(),
		windowHeight = $(window).height();
		zoomDiv.hide();
		i = null;
		// 绑定事件
		$("#zoom .close").on("click", function(obj){
			if (obj) obj.preventDefault();
			r = false;
			i = null;
			zoomDiv.hide();
			$("body").removeClass("zoomed");
			contentDiv.empty()
		});
		
		// 键盘事件
		$(document).keydown(function(obj) {
			if (!i) return;
			if (obj.which == 38 || obj.which == 40) obj.preventDefault();
			if (obj.which == 27) {
				if (obj) obj.preventDefault();
				r = false;
				i = null;
				zoomDiv.hide();
				$("body").removeClass("zoomed");
				contentDiv.empty()
			};
			if (obj.which == 37 /*&& !i.hasClass("zoom")*/) {
				var parentLi = i.parent("div").parent("div").prev();
				parentLi.children("div").find("a").trigger("click");
			};
			if (obj.which == 39 /*&& !i.hasClass("zoom")*/) {
				var parentLi = iparent("div").parent("div").next();
				parentLi.children("div").find("a").trigger("click");
			}
		});
		
		//if ($(".gallery div div  a").length == 1) $(".gallery div div a")[0].addClass("zoom");
		$(".gallery div div a").on("click", function(obj){
			if (obj) obj.preventDefault();
			var aObj = $(this),
				url = aObj.attr("href");
			if (!url) return;
			var imgobj = $(new Image).hide();
			//$("#zoom .previous, #zoom .next").show();
			//if (aObj.hasClass("zoom")) $("#zoom").hide();
			if (!r) {
				r = true;
				zoomDiv.show();
				$("body").addClass("zoomed")
			}
			contentDiv.html(imgobj).delay(500).addClass("loading");
			imgobj.load(function(){
				var imageObj = $(this),
				borderWidth = parseInt(contentDiv.css("borderLeftWidth")),
				reallyWidth = windowWidth - borderWidth * 2,
				reallyHeight = windowHeight - borderWidth * 2,
				imgWidth = imageObj.width(),
				imgHeight = imageObj.height();
				if (imgWidth == contentDiv.width() && imgWidth <= reallyWidth && imgHeight == contentDiv.height() && imgHeight <= reallyHeight) {
					imageObj.show();
					contentDiv.removeClass("loading");
					return
				}
				if (imgWidth > reallyWidth || imgHeight > reallyHeight) {
					var height = reallyHeight < imgHeight ? reallyHeight : imgHeight,
						width = reallyWidth < imgWidth ? reallyWidth : imgWidth;
					if (height / imgHeight <= width / imgWidth) {
						imageObj.width(imgWidth * height / imgHeight);
						imageObj.height(height)
					} else {
						imageObj.width(width);
						imageObj.height(imgHeight * width / imgWidth)
					}
				}
					
				contentDiv.animate({
					width: imageObj.width(),
					height: imageObj.height(),
					marginTop: -(imageObj.height() / 2) - r,
					marginLeft: -(imageObj.width() / 2) - r
				}, 200, function() {
					imageObj.show();
					contentDiv.removeClass("loading");
				})
			});
			imgobj.attr("src", url);
			i = aObj
			isTouchDevice();
		});

		$(window).on("resize", function(){
			windowWidth = $(window).width();
			windowHeight = $(window).height()
		});

		$(window).on("mousewheel DOMMouseScroll", function(e) {
			if (!i) return;
			e.stopPropagation();
			e.preventDefault();
			e.cancelBubble = false
		})	
	}
