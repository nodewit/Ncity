var table;
var layer;
var form;
layui.use([ 'layer', 'table', 'element', 'form' ], function() {
	table = layui.table;
	layer = layui.layer;
	form = layui.form;
	// 执行一个 table 实例
	table.render({
		elem : '#integral',
		height : 'full-90',
		url : '/view/manager/queryIntegralByPage',
		page : true, // 开启分页
		cols : [ [ // 表头
		{
			field : 'name',
			title : '用户名称'
		}, {
			field : 'type',
			title : '类型',
			templet : function(d) {
				var type = d.type;
				switch(type){
				case 1:
					return "签到";
				    break;
				case 2:
					return "邀请好友";
				    break;
				case 3:
					return "新用户注册";
				    break;
				case 4:
					return "发布时光机";
				    break;
				case 5:
					return "发布一诺千金";
				    break;
				case 6:
					return "点赞";
				    break;
				case 7:
					return "评论";
				    break;
				}
			}
		}, {
			field : 'integral',
			title : '获得灵力'
		}, {
			field : 'create_time',
			title : '创建时间',
			templet : function(d) {
				var date = new Date(d.create_time);
				return date.format('yyyy-MM-dd hh:mm:ss');
			}
		} ] ]
	});
	
	//监听提交
	form.on('submit(form)', function(data){
		queryIntegral(data.field.keyword,data.field.type);
		return false;
	});

});



function queryIntegral(keyword,type) {
	table.reload('integral', {
		where : {
			keyword : keyword,
			type : type
		},
		page : {
			curr : 1
		}
	});
}

