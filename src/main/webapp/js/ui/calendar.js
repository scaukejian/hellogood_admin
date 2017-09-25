var months = new Array("一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一",
		"十二");
var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
var days = new Array("日", "一", "二", "三", "四", "五", "六");
var classTemp;
var today = new getToday();
var year = today.year;
var month = today.month;
var newCal;

function getDays(month, year) {
	if (1 == month)
		return ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? 29
				: 28;
	else
		return daysInMonth[month];
}

function getToday() {
	this.now = new Date();
	this.year = this.now.getFullYear();
	this.month = this.now.getMonth();
	this.day = this.now.getDate();
}

function Calendar() {
	newCal = new Date(year, month, 1);
	today = new getToday();
	var day = -1;
	var startDay = newCal.getDay();
	var endDay = getDays(newCal.getMonth(), newCal.getFullYear());
	var daily = 0;
	if ((today.year == newCal.getFullYear())
			&& (today.month == newCal.getMonth())) {
		day = today.day;
	}
	var caltable = document.all.caltable.tBodies.calendar;
	var intDaysInMonth = getDays(newCal.getMonth(), newCal.getFullYear());

	for ( var intWeek = 0; intWeek < caltable.rows.length; intWeek++)
		for ( var intDay = 0; intDay < caltable.rows[intWeek].cells.length; intDay++) {
			var cell = caltable.rows[intWeek].cells[intDay];
			var montemp = (newCal.getMonth() + 1) < 10 ? ("0" + (newCal
					.getMonth() + 1)) : (newCal.getMonth() + 1);
			if ((intDay == startDay) && (0 == daily)) {
				daily = 1;
			}
			var daytemp = daily < 10 ? ("0" + daily) : (daily);
			var d = "<" + newCal.getFullYear() + "-" + montemp + "-" + daytemp
					+ ">";
			if (day == daily)
				cell.className = "DayNow";
			else if (intDay == 6)
				cell.className = "DaySat";
			else if (intDay == 0)
				cell.className = "DaySun";
			else
				cell.className = "Day";
			if ((daily > 0) && (daily <= intDaysInMonth)) {
				cell.innerText = daily;
				cell.setAttribute("onclick", "javascript:showDay("+newCal.getFullYear()+","+(newCal.getMonth() + 1)+","+daily+");");
				daily++;
			} else {
				cell.className = "CalendarTD";
				cell.innerText = "";
			}
		}
	document.all.year.value = year;
	document.all.month.value = month + 1;
}

function subMonth() {
	if ((month - 1) < 0) {
		month = 11;
		year = year - 1;
	} else {
		month = month - 1;
	}
	Calendar();
}

function addMonth() {
	if ((month + 1) > 11) {
		month = 0;
		year = year + 1;
	} else {
		month = month + 1;
	}
	Calendar();
}

function setDate() {
	if (document.all.month.value < 1 || document.all.month.value > 12) {
		$.Prompt("月的有效范围在1-12之间!");
		return;
	}
	year = Math.ceil(document.all.year.value);
	month = Math.ceil(document.all.month.value - 1);
	Calendar();
}

function buttonOver() {
	/*var useragent = navigator.userAgent;// IE和火狐兼容问题
	var type=1;
	if(useragent.indexOf("FireFox")||useragent.indexOf("Chrome")){
		type=2;
	}else{
		type=1;
	}*/
	if (!isIE()) {
		var obj = arguments.callee.caller.arguments[0].target.parentNode;
		obj.style.cssText = "background-color:#FFFFFF";
	} else {
		var obj = window.event.srcElement;
		obj.runtimeStyle.cssText = "background-color:#FFFFFF";
	}
}

function buttonOut() {
	/*var useragent = navigator.userAgent;// IE和火狐兼容问题
	var type=1;
	if(useragent.indexOf("FireFox")||useragent.indexOf("Chrome")){
		type=2;
	}else{
		type=1;
	}
	alert(type);*/
	if (!isIE()) {
		var obj = arguments.callee.caller.arguments[0].target.parentNode;
		window.setTimeout(function() {
			obj.style.cssText = "";
		}, 300);
	} else {
		var obj = window.event.srcElement;
		window.setTimeout(function() {
			obj.runtimeStyle.cssText = "";
		}, 300);
	}
}

//默认大图显示今天
function onloadShow(){
	newCal = new Date(year, month, 1);
	today = new getToday();
	var day = -1;
	if ((today.year == newCal.getFullYear())
			&& (today.month == newCal.getMonth())) {
		day = today.day;
	}
	showDay(newCal.getFullYear(),(newCal.getMonth()+1),day);
}
//window.onload=onloadShow;

//点击大图显示日期
function showDay(year,month,day){
	document.all.YearBig.innerText=year;
	document.all.MonthBig.innerText=month;
	document.all.BigDay.innerText=day;
}


//判断是否为IE
function isIE() { // ie
	//if (window.navigator.userAgent.toLowerCase().indexOf("MSIE") >= 1)
		if(navigator.appName.indexOf("Explorer") > -1)
		return true;
	else
		return false;
}
//让firefox支持innerText
if (!isIE()) { 
	//alert("not ie");
	HTMLElement.prototype.__defineGetter__("innerText", function() {
		var anyString = "";
		var childS = this.childNodes;
		for ( var i = 0; i < childS.length; i++) {
			if (childS[i].nodeType == 1)
				anyString += childS[i].tagName == "BR" ? '\n'
						: childS[i].textContent;
			else if (childS[i].nodeType == 3)
				anyString += childS[i].nodeValue;
		}
		return anyString;
	});
	HTMLElement.prototype.__defineSetter__("innerText", function(sText) {
		this.textContent = sText;
	});
}
