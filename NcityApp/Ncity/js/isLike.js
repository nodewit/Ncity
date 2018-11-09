//查看详情
$(".mui-scroll").on("tap","[data-id]",function(){
    var dataId = this.getAttribute("data-id");
    var dataHref = this.getAttribute("data-href");
    var dataTypeid = $("[data-typeid]").attr("data-typeid");//类型
    openPage(dataHref,{"dataId":dataId,"dataTypeid":dataTypeid});
});

//点赞
$(".mui-scroll").on("tap","[data-typeid]",function(){
    // var that = this;
    var isLike = (this.innerText =="点赞") ? 1 :0;//1-点赞 0-取消
    var dataType = this.getAttribute("data-typeid");//类型
    var dataId = $(this).parent().parent().find("[data-id]").attr("data-id");//id
    
    var likeEl = $(this).parent().find(".likeNum");
    var likeNum = parseInt(likeEl.html());//喜欢的总数
    
    getDataByAjax({
        type: 'GET',
        url: 'user/like?uuid=' + localStorage.getItem("loginInfo"),
        data: {
            "like": isLike,
            "typeId": dataType,
            "objectId":dataId
        },
    }, function(result) {
        console.info("点赞成功--进入")
        if(result.code == 0){}
    });
    
    if(isLike){
        $(this).attr("class","yLike");
        this.innerText = "已点赞";
        likeEl.html(++likeNum)
        plus.nativeUI.toast("点赞成功");
    }else{
        $(this).attr("class","");
        this.innerText = "点赞";
        likeEl.html(--likeNum)
        plus.nativeUI.toast("取消点赞")
    }
});