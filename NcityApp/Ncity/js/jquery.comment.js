(function($){
	function crateCommentInfo(obj){
		if(typeof(obj.time) == "undefined" || obj.time == ""){
			obj.time = getMyDate(new Date().getTime());
		}
		
		var el = "<div class='comment-info'><header><img src='"+obj.img+"'></header><div class='comment-right'><h3 data-id='"+obj.id+"'>"+obj.replyName+"</h3>"
				+"<div class='comment-content-header'><span><i class='glyphicon glyphicon-time'></i>"+obj.time+"</span>";
		
		if(typeof(obj.address) != "undefined" && obj.browse != ""){
			el =el+"<span><i class='glyphicon glyphicon-map-marker'></i>"+obj.address+"</span>";
		}
		el = el+"</div><p class='content'>"+obj.content+"</p><div class='comment-content-footer'><div class='row'><div class='col-md-10'>";
		
		if(typeof(obj.osname) != "undefined" && obj.osname != ""){
			el =el+"<span><i class='glyphicon glyphicon-pushpin'></i> 来自:"+obj.osname+"</span>";
		}
		
		if(typeof(obj.browse) != "undefined" && obj.browse != ""){
			el = el + "<span><i class='glyphicon glyphicon-globe'></i> "+obj.browse+"</span>";
		}
		
		el = el + "</div><div class='col-md-2'><span class='reply-btn'>回复</span></div></div></div><div class='reply-list'>";
		if(obj.replyBody && obj.replyBody != "" && obj.replyBody.length > 0){
			var arr = obj.replyBody;
			for(var j=0;j<arr.length;j++){
				var replyObj = arr[j];
				el = el+createReplyComment(replyObj);
			}
		}
		el = el+"</div></div></div>";
		return el;
	}
    
	
	//返回每个回复体内容
	function createReplyComment(reply){
        if(typeof(reply.time) == "undefined" || reply.time == ""){
        	reply.time = getMyDate(new Date().getTime());
        }
		var replyEl = "<div class='reply'><div><a href='javascript:void(0)' class='replyname' data-id='"+reply.id+"'>"+reply.replyName+"</a>:<a href='javascript:void(0)'>@"+reply.beReplyName+"</a><span>"+reply.content+"</span></div>"
						+ "<p><span>"+reply.time+"</span> <span class='reply-list-btn'><!--回复--></span></p></div>";
		return replyEl;
	}
	function replyClick(el){
		el.parent().parent().append("<div class='replybox'><textarea placeholder='来说几句吧......' class='mytextarea' ></textarea><span class='send'>发送</span></div>");
		
		$(".mytextarea")[0].focus();
	}
	
	
	$.fn.addCommentList=function(options){
		var defaults = {
			data:[],
			add:""
		}
		var option = $.extend(defaults, options);
		//加载数据
		if(option.data.length > 0){
			var dataList = option.data;
			var totalString = "";
			for(var i=0;i<dataList.length;i++){
				var obj = dataList[i];
				var objString = crateCommentInfo(obj);
				totalString = totalString+objString;
			}
			$(this).append(totalString);
		}
		
		//添加新数据
		if(option.add != ""){
			obj = option.add;
			var str = crateCommentInfo(obj);
			$(this).prepend(str);
		}
	}
	
	//回复按钮点击事件
	$(".comment-list").on("click",".reply-list-btn, .reply-btn",function(){
		if($(this).parent().parent().find(".replybox").length > 0){
			console.info("进入1")
			$(".commentbox").css("display","block");
			$(".mui-content").css("margin-bottom","49px")
			$(".replybox").remove();
		}else{
			console.info("进入2")
			$(".commentbox").css("display","none");
			$(".mui-content").css("margin-bottom","0")
			$(".replybox").remove();
			replyClick($(this));
		}
	});
	
	
	$(".comment-list").on("click", ".send", function(){
		console.info("啊");
		var content = $(this).prev().val();
		if(content == ""){
			plus.nativeUI.toast("不能回复空内容");
			return;
		}
		
		$(".commentbox").css("display","block");
		$(".mui-content").css("margin-bottom","49px")
		
		var parentEl = $(this).parents(".comment-right")//.parent().parent().parent();//.comment-right
		var obj = new Object();
		obj.replyName = localStorage.getItem("userName");//回复人名字
		//获取被回复人名字
		if($(this).parent().parent().hasClass("reply")){//.row 或 .reply
			console.log("1111");
			obj.beReplyName = $(this).parent().parent().find("a:first").text();
            obj.id = $(this).parent().parent().find("a:first").attr("data-id");
		}else{
			console.log("2222");
			obj.beReplyName = parentEl.find("h3").text();
            obj.id = parentEl.find("h3").attr("data-id");
		}
		obj.content=content;
        
        //向服务器提交回复
        sendReply(obj);
        
		var replyString = createReplyComment(obj);
		$(".replybox").remove();
		parentEl.find(".reply-list").append(replyString);
		//.find(".reply-list-btn:last").click(function(){alert("不能回复自己");});
	});
	
})(jQuery);

//异步提交评论(保存回复至服务器)
function sendReply(obj){
    // if(typeof obj == "undefined") return;
    var toId = obj.id || 0;
    console.info(toId);
    getDataByAjax({
        type: 'POST',
        url: 'user/comment',
        data: JSON.stringify({
            "uuid": localStorage.getItem("loginInfo"),
            "typeId": self.dataTypeid,//self为页面对象//1:时光机、2：一诺千金
            "objectId": self.dataId,
            "toId": toId,
            "comment":obj.content,
        }),
    }, function(result) {
        
    });
}

//发送评论 点击事件
$("#comment").click(function() {
    if ($("#content").val().length < 1) {
        plus.nativeUI.toast("不能回复空内容");
        return;
    }
    var obj = new Object();
    obj.img = "img/logo.png";
    obj.replyName = localStorage.getItem("userName");
    obj.content = $("#content").val();
    $("#content").val(""); //清空回复内容
    $(".comment-list").addCommentList({
        data: [],
        add: obj
    });
    //向服务器提交回复
    sendReply(obj);
});

// 初始化评论
function initComment(){
    getDataByAjax({
        type: 'GET',
        url: 'user/queryCommentListByPage?uuid=' + localStorage.getItem("loginInfo"),
        data: {
            "pageNumber": 1,
            "pageSize": 10,
            "type": self.dataTypeid,//self为页面对象
            "objectId": self.dataId
        },
    }, function(result) {
        if(result.data && result.data.length == 0){
            return;
        }
        //初始化评论数据
        var arr = [];
        
        $.each(result.data, function(k, v) {
            var comment = {};
            var replyList = [];
            comment.img = "img/logo.png";
            comment.id = v.id;
            comment.replyName = v.name;
            comment.content = v.comment;
            comment.time = getMyDate(v.createTime);

            $.each(v.commentList, function(k2, v2) {
                var reply = {};
                reply.img = "img/logo.png";
                reply.id = v2.id;
                reply.replyName = v2.name;
                reply.beReplyName = v.name; //被回复人
                reply.content = v2.comment;
                reply.time = getMyDate(v2.createTime);

                replyList.push(reply);
            });
            comment.replyBody = replyList;

            arr.push(comment);
        });
        
        //显示评论
        $(".comment-list").addCommentList({
            data: arr,
            add: ""
        });
    });
}
//初始化评论结束