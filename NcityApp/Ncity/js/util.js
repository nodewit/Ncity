// var _IPGLOBAL = "http://192.168.0.18:8222/";//服务器IP
var _IPGLOBAL = "http://192.168.0.109:8222/";//本机IP
// var _IPGLOBAL = "http://21765a466b.51mypc.cn:47863/"; //外网IP
//时间转换为指定格式
function getMyDate(str) {
	var oDate = new Date(str),
		oYear = oDate.getFullYear(),
		oMonth = oDate.getMonth() + 1,
		oDay = oDate.getDate(),
		oHour = oDate.getHours(),
		oMin = oDate.getMinutes(),
		oSen = oDate.getSeconds(),
		oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSen); //最后拼接时间  
	return oTime;
};
//补0操作
function getzf(num) {
	if (parseInt(num) < 10) {
		num = '0' + num;
	}
	return num;
}

//格式化录音时间
var timeToStr = function(ts) {
	if (isNaN(ts)) {
		return "--:--:--";
	}
	var h = parseInt(ts / 3600);
	var m = parseInt((ts % 3600) / 60);
	var s = parseInt(ts % 60);
	return (getzf(h) + ":" + getzf(m) + ":" + getzf(s));
};


var t = 0;
//自定义异步方法（统一各页面的异步格式）
function getDataByAjax(obj, callback) {
	var that = this;
	var type = obj.type || "";
	var url = obj.url || "";
	var data = obj.data || "";
	var token = obj.token || "";
	// var async = ("async" in obj) ? obj.async : true; //判断async是否存在。true：异步 false：同步
	var contentType = obj.contentType || "application/json;";
	
	$.ajax({
		type: type,
		url: _IPGLOBAL + url,
		data: data,
		// async: async,（修改双webview下拉刷新后，目前不需要了）
		contentType: contentType,
		beforeSend: function(xhr) {
			xhr.setRequestHeader("Token", token);
			xhr.setRequestHeader("UA", "");
		},
		success: function(result) {
			if(result.code == -1){
				plus.nativeUI.toast("服务器开小差了(>_<)，稍后再试一试吧。")
			}
			
			//          plus.nativeUI.toast(result.message+"["+result.code+"] \n 信息："+JSON.stringify(result.data));
			//          console.info(result.message+"["+result.code+"] \n 信息："+JSON.stringify(result.data));
			callback(result);
		},
		error: function(e, info, obj) {
			plus.nativeUI.toast("网络波动了，请稍后再试。")
			console.info("\n出错了：\n 参1：" + JSON.stringify(e) +
				"\n 参2：" + info +
				"\t 参3：" + JSON.stringify(obj));
		},
// 		timeout: 5000, //超时时间设置，单位毫秒
// 		complete: function(XMLHttpRequest, status) { //请求完成后最终执行参数
// 			console.info(JSON.stringify(status))
// 			if (status == 'timeout') { //超时,status还有success,error等值的情况
// 				testajax.abort(); //超时后停止异步
// 			}
// 		}
	});
}

//打开页面方法
function openPage(url, extras) { //extras:向打开页面传递的参数
	mui.openWindow({
		url: url,
		id: url,
		show: {
			autoShow: false
		},
		waiting: {
			autoShow: false
		},
		extras: extras
	});
}


//倒计时
function countDown(domObj) {
	var codeDisTime = 60; //验证码点击按钮失效时间
	var cd = setInterval(function() {
		domObj.innerHTML = --codeDisTime + "s";
		if (codeDisTime < 1) {
			codeDisTime = 60;

			domObj.innerHTML = "验证码";
			domObj.removeAttribute("disabled");
			clearInterval(cd);
		}
	}, 1000);
}
