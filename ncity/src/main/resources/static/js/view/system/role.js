var table;
var layer;
layui.use([ 'layer', 'table', 'element' ], function() {
	table = layui.table;
	layer = layui.layer;
	// 执行一个 table 实例
	table.render({
		elem : '#role',
		height : 'full-90',
		url : '/view/system/queryRole',
		page : true, // 开启分页
		cols : [ [ // 表头
		{
			fixed : 'left',
			type : 'checkbox'
		}, {
			field : 'id',
			title : 'ID',
			width : 80,
			fixed : 'left'
		}, {
			field : 'role_name',
			title : '角色名称',
			width : 160
		}, {
			field : 'description',
			title : '描述',
			width : 260
		}, {
			field : 'create_time',
			title : '创建时间',
			width : 260,
			templet : function(d) {
				var date = new Date(d.create_time);
				return date.format('yyyy-MM-dd h:m:s');
			}
		}, {
			fixed : 'right',
			title : '操作',
			width : 300,
			align : 'center',
			toolbar : '#tools'
		} ] ]
	});

	// 监听工具条
	table.on('tool(tools)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
		var data = obj.data // 获得当前行数据
		, layEvent = obj.event; // 获得 lay-event 对应的值
		if ('edit' == layEvent) {
			addRole(data.id)
		} else if ('del' == layEvent) {
			del(data.id);
		}
	});
	
	
});

function queryRole() {
	var keyword = $("#keyword").val();
	table.reload('role', {
		where : {
			keyword : keyword
		},
		page : {
			curr : 1
		}
	});
}
var index;
function addRole(roleId) {
	index = layer.open({
		type : 2,
		title : "角色信息",
		content : '/view/system/editRole?roleId=' + roleId
	});
	layer.full(index);
}

function close() {
	layer.close(index);
}

function del(roleId) {
	if (roleId == 1) {
		layer.open({
			type : 1,
			content : '<div style="padding: 20px 80px;">超级管理员的角色不能删除。</div>',
			btn : [ '确定' ]
		});
	} else {
		layer.open({
			type : 1,
			content : '<div style="padding: 20px 80px;">确定删除记录?</div>',
			btn : [ '确定', '取消' ],
			yes : function(index, layero) {
				$.ajax({
					url : "/view/system/deleteRole",
					data : {
						"rid" : roleId
					},
					dataType : "text",
					success : function(data) {
						if(data==0){
							layer.close(index);
							layer.msg("删除成功！");
							queryRole();
						}else{
							layer.msg("删除失败！");
						}
					},
					error : function() {
					}
				});
			}
		});
	}
}

/**
 * 获取选中数据
 */
function getDatas(){
	var checkStatus = table.checkStatus('role');
	var data = checkStatus.data;
	var roleId = "";
	for(var i=0;i<data.length;i++){
		roleId += data[i].id;
		if(i<data.length-1){
			roleId += ",";
		}
	}
	if(data.length != 0){
		del(roleId);
	}
}