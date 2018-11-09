var table;
var layer;
var form;
layui.use([ 'layer', 'table', 'element', 'form' ], function() {
	table = layui.table;
	layer = layui.layer;
	form = layui.form;
	// 执行一个 table 实例
	table.render({
		elem : '#suggest',
		height : 'full-90',
		where:{
			typeId : 2
		},
		url : '/view/manager/querySuggestByPage',
		page : true, // 开启分页
		cols : [ [ // 表头
		{
			field : 'name',
			title : '用户名称',
			width : 120,
		}, {
			field : 'content',
			title : '反馈内容',
			width : 620
		}, {
			field : 'create_time',
			title : '创建时间',
			width : 160,
			templet : function(d) {
				var date = new Date(d.create_time);
				return date.format('yyyy-MM-dd hh:mm:ss');
			}
		} ] ]
	});
	
	//监听提交
	form.on('submit(form)', function(data){
		querySuggest(data.field.keyword,data.field.type);
		return false;
	});

});



function querySuggest(keyword,type) {
	table.reload('suggest', {
		where : {
			keyword : keyword,
			type : type,
			typeId : 2
		},
		page : {
			curr : 1
		}
	});
}

