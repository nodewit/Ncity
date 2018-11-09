var table;
var layer;
var form;
layui.use([ 'layer', 'table', 'element', 'form' ], function() {
	table = layui.table;
	layer = layui.layer;
	form = layui.form;
	// 执行一个 table 实例
	table.render({
		elem : '#timeMachine',
		height : 'full-90',
		where:{
			typeId : 1
		},
		url : '/view/manager/queryChainByPage',
		page : true, // 开启分页
		cols : [ [ // 表头
		{
			field : 'name',
			title : '用户名称',
			width : 120,
		}, {
			field : 'type',
			title : '类型',
			width : 120,
			templet : function(d) {
				if (d.type == 1) {
					return '公开';
				} else if (d.type == 2) {
					return '私密';
				}
			}
		}, {
			field : 'address',
			width : 550,
			title : '区块地址'
		}, {
			field : 'likeNum',
			width : 120,
			title : '点赞数量'
		}, {
			field : 'commentNum',
			width : 120,
			title : '评论数量'
		}, {
			field : 'create_time',
			title : '创建时间',
			width : 160,
			templet : function(d) {
				var date = new Date(d.create_time);
				return date.format('yyyy-MM-dd hh:mm:ss');
			}
		},{
			fixed : 'right',
			title : '操作',
			align : 'center',
			width : 80,
			toolbar : '#tools'
		} ] ]
	});
	
	//监听提交
	form.on('submit(form)', function(data){
		queryTimeMachine(data.field.keyword,data.field.type);
		return false;
	});

	// 监听工具条
	table.on('tool(tools)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
		var data = obj.data // 获得当前行数据
		, layEvent = obj.event; // 获得 lay-event 对应的值
		if ('query' == layEvent) {
			getTimeMachineInfo(data.id);
		}
	});

});



function queryTimeMachine(keyword,type) {
	table.reload('timeMachine', {
		where : {
			keyword : keyword,
			type : type,
			typeId : 1
		},
		page : {
			curr : 1
		}
	});
}
var index;

function getTimeMachineInfo(id){
	index = layer.open({
		type : 2,
		title : "时光机信息",
		content : '/view/manager/timeMachineInfo?id=' + id
	});
	layer.full(index);
}

function close() {
	layer.close(index);
}
