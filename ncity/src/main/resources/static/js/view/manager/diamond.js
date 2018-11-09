var table;
var layer;
var form;
layui.use([ 'layer', 'table', 'element', 'form' ], function() {
	table = layui.table;
	layer = layui.layer;
	form = layui.form;
	// 执行一个 table 实例
	table.render({
		elem : '#diamond',
		height : 'full-90',
		url : '/view/manager/queryDiamondByPage',
		page : true, // 开启分页
		cols : [ [ // 表头
		{
			field : 'name',
			title : '用户名称'
		}, {
			field : 'diamond',
			title : '获得灵钻'
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
		queryDiamond(data.field.keyword);
		return false;
	});

});



function queryDiamond(keyword,type) {
	table.reload('diamond', {
		where : {
			keyword : keyword
		},
		page : {
			curr : 1
		}
	});
}

