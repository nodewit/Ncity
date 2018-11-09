//播放音频 相关属性
var p = null; //音频播放对象
var pi = null; //音频播放定时器对象
var ep = document.getElementById("play"); //播放界面
var pt = document.getElementById('ptime'); //播放时间
var ps = document.getElementById('schedule'); //进度条中的进度

//播放录音
function startPlay(url) {
    console.info("进入")
	if(!url){
		plus.nativeUI.toast("路径为空")
	}
	ep.style.display = "block"; //设置播放界面显示
	var pp = document.getElementById("progress"); //获取进度条容器(未显示时，获取的可见长度始终为0)

	var L = pp.clientWidth; //获取可见宽度

	p = plus.audio.createPlayer(url); //创建音频播放对象
	p.play(function() {
		console.info("播放完成");
		// 播放完成,完成进度条
		pt.innerText = timeToStr(d) + '/' + timeToStr(d);
		ps.style.webkitTransition = 'all 0.3s linear';
		ps.style.width = L + 'px';
		stopPlay();
	}, function(e) {
		plus.nativeUI.toast('播放音频文件"' + url + '"失败：' + e.message)
	});

	//获取总时长
	var d = p.getDuration();
	if (!d) {
		pt.innerText = '00:00:00/' + timeToStr(d);
	}

	pi = setInterval(function() {
		if (!d) { // 兼容无法及时获取总时长的情况
			d = p.getDuration();
		}
		var c = p.getPosition(); //当前播放时间
		if (!c) { // 兼容无法及时获取当前播放位置的情况
			return;
		}
		console.info("当前播放时间："+c);
		pt.innerText = timeToStr(c) + '/' + timeToStr(d);
		var pct = Math.round(L * c / d); //进度条变化
		if (pct < 8) {
			pct = 8;
		}
		ps.style.width = pct + 'px';
	}, 1000);
}

var dowPath = "_doc/downloads/";//下载路径

//检测是否已经存在该文件
function downloadCheck(dataHref){
	var fileName = dataHref.substr(dataHref.lastIndexOf("/")+1);//获取文件名
	plus.io.resolveLocalFileSystemURL(dowPath+fileName,function(){
		console.info("文件已存在，直接播放");
		startPlay(dowPath+fileName);//存在则直接播放
	},function(e){
		console.info("文件不存在，下载后播放");
		downloadMp3toLocalByUrl(dataHref);//先下载在播放
	})
}
//下载音频（解决ios无法网络播放问题）
function downloadMp3toLocalByUrl(loadUrl) {
	plus.nativeUI.showWaiting("正在缓冲...");
	var dtask = plus.downloader.createDownload(loadUrl, {
		filename:dowPath//保存路径
	}, function(d, status) {
		plus.nativeUI.closeWaiting();
		if (status == 200) {
			console.info("下载成功，播放");
			startPlay(d.filename);
		} else {
			//下载失败
			console.log("下载失败status=" + status);
		}
	});
	//启动下载任务
	dtask.start();
}

// 停止播放
function stopPlay() {
	clearInterval(pi);
	pi = null;
	setTimeout(resetPlay, 500);
	// 操作播放对象
	if (p) {
		p.stop();
		p = null;
	}
}

// 重置播放页面内容
function resetPlay() {
	ep.style.display = 'none';
	ps.style.width = '8px';
	ps.style.webkitTransition = 'all 1s linear';
	pt.innerText = '00:00:00/00:00:00';
}