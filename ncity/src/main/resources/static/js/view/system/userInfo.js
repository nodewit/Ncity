var table;
var layer;
layui.use([ 'layer', 'table', 'element' ], function() {
	table = layui.table;
	layer = layui.layer;
	// 执行一个 table 实例
	table.render({
		elem : '#user',
		height : 'full-90',
		url : '/view/system/queryUser',
		page : true, // 开启分页
		cols : [ [ // 表头
		{
			fixed : 'left',
			type : 'checkbox'
		}, {
			field : 'login_id',
			title : '登录ID',
			width : 80,
			fixed : 'left'
		}, {
			field : 'name',
			title : '用户名称',
			width : 160
		}, {
			field : 'sex',
			title : '性别',
			width : 80,
			templet : function(d) {
				if (d.sex == 1) {
					return '男';
				} else if (d.sex == 0) {
					return '女';
				}
			}
		}, {
			field : 'login_time',
			title : '最后登录时间',
			width : 220,
			templet : function(d) {
				var date = new Date(d.create_time);
				return date.format('yyyy-MM-dd h:m:s');
			}
		}, {
			field : 'count',
			title : '登录次数',
			width : 100
		},  {
			field : 'create_time',
			title : '创建时间',
			width : 220,
			templet : function(d) {
				var date = new Date(d.create_time);
				return date.format('yyyy-MM-dd h:m:s');
			}
		},{
			fixed : 'right',
			title : '操作',
			width : 240,
			align : 'center',
			toolbar : '#tools'
		} ] ]
	});

	// 监听工具条
	table.on('tool(tools)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
		var data = obj.data // 获得当前行数据
		, layEvent = obj.event; // 获得 lay-event 对应的值
		if ('edit' == layEvent) {
			addUser(data.id);
		} else if ('del' == layEvent) {
			del(data.id);
		} else if ('reset' == layEvent) {
			reset(data.id);
		}
	});

});

function queryUser() {
	var keyword = $("#keyword").val();
	table.reload('user', {
		where : {
			keyword : keyword
		},
		page : {
			curr : 1
		}
	});
}
var index;
function addUser(userId) {
	index = layer.open({
		type : 2,
		title : "用户信息",
		content : '/view/system/editUser?userId=' + userId
	});
	layer.full(index);
}

function close() {
	layer.close(index);
}

function del(userId) {
	layer.open({
		type : 1,
		content : '<div style="padding: 20px 80px;">确定删除记录?</div>',
		btn : [ '确定', '取消' ],
		yes : function(index, layero) {
			$.ajax({
				url : "/view/system/deleteUser",
				data : {
					"ids" : userId
				},
				dataType : "text",
				success : function(data) {
					if (data == 0) {
						layer.close(index);
						layer.msg("删除成功！");
						queryUser();
					} else {
						layer.msg("删除失败！");
					}
				},
				error : function() {
				}
			});
		}
	});
}

function reset(userId) {
	layer.open({
		type : 1,
		content : '<div style="padding: 20px 80px;">确定重置密码?</div>',
		btn : [ '确定', '取消' ],
		yes : function(index, layero) {
			$.ajax({
				url : "/view/system/resetPassword",
				data : {
					"uid" : userId
				},
				dataType : "text",
				success : function(data) {
					if (data == 0) {
						layer.close(index);
						layer.msg("重置成功！新密码：123456");
					} else {
						layer.msg("重置失败！");
					}
				},
				error : function() {
				}
			});
		}
	});
}

/**
 * 获取选中数据
 */
function getDatas() {
	var checkStatus = table.checkStatus('user');
	var data = checkStatus.data;
	var userId = "";
	for (var i = 0; i < data.length; i++) {
		userId += data[i].id;
		if (i < data.length - 1) {
			userId += ",";
		}
	}
	if (data.length != 0) {
		del(userId);
	}
}