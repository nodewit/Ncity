var table;
var layer;
var form;
layui.use([ 'layer', 'table', 'element', 'form' ], function() {
	table = layui.table;
	layer = layui.layer;
	form = layui.form;
	// 执行一个 table 实例
	table.render({
		elem : '#promise',
		height : 'full-90',
		where:{
			typeId : 2
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
			field : 'filePath',
			title : '音频',
			align : 'center',
			width : 380,
			templet: '#voice'
		} ] ]
	});
	
	//监听提交
	form.on('submit(form)', function(data){
		queryPromise(data.field.keyword);
		return false;
	});

});



function queryPromise(keyword) {
	table.reload('promise', {
		where : {
			keyword : keyword,
			typeId : 2
		},
		page : {
			curr : 1
		}
	});
}

