var form;
var layer;
var index;
var nodes;
layui.use([ 'form', 'tree','layer' ], function() {
	form = layui.form;
	layer = layui.layer;
	
	function queryMenu(){
		$.ajax({ 
			url: "/view/system/queryMenu",
			async:false,
			dataType:"json",
			success: function(data){
				$("#tree").empty();
				$("#parentId").val('');
				$("#parentName").val('');
				$("#order_").val(1);
				$("#url").val('');
				$("#name").val('');
				$("#id").val('');
				$("#icon").html("&#xe614;");
				layui.tree({
					elem : '#tree',
					nodes : data,
					click: function(node){
						nodes = node;
					    if(node.level == "0"){
					    	$("#add").css("display","");
					    	$("#save").css("display","");
					    	$("#del").css("display","none");
					    	$("#reset").css("display","none");
					    }else  if(node.level == "1"){
					    	$("#add").css("display","");
					    	$("#save").css("display","");
					    	$("#del").css("display","");
					    	$("#reset").css("display","");
					    }else  if(node.level == "2"){
					    	$("#add").css("display","none");
					    	$("#save").css("display","");
					    	$("#del").css("display","");
					    	$("#reset").css("display","");
					    }
					    if(node.id != 0){
					    	$.ajax({ 
								url: "/view/system/queryMenuById",
								data:{"mid":node.id},
								async:false,
								dataType:"json",
								success: function(data){
									$("#parentId").val(data.parent_id);
									$("#parentName").val(data.parentName);
									$("#order_").val(data.order_);
									$("#name").val(data.name);
									$("#url").val(data.url);
									$("#id").val(data.id);
									$("#icon").html(data.icon);
								}
						    });
					    }else{
					    	$("#parentId").val('');
							$("#parentName").val('');
							$("#order_").val(1);
							$("#url").val('');
							$("#name").val(node.name);
							$("#id").val(node.id);
							$("#icon").html("&#xe614;");
					    }
					}
				});
			}
		});
	}
	
	queryMenu();
	
	
	form.on('submit(*)', function(data){
		var index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
		var code = $("#icon").html();
	    var json = data.field;
	    json['icon'] = code;
	    var url = "/view/system/updateMenu";
	    if(json['id'] == null || json['id'] == ""){
	    	url = "/view/system/addMenu";
	    }
	    $.ajax({ 
			url: url,
			data:json,
			async:false,
			success: function(data){
				layer.close(index);
				if(data==0){
					layer.msg("保存成功");
					queryMenu();
				}else{
					layer.msg("保存失败");
				}
			}
	    });
	    return false;
	});
	
	$("#iconBtn").click(function(){
		index = layer.open({
			type : 2,
			title : "图标信息",
			content : '/view/system/icon'
		});
		layer.full(index);
	});
	
	$("#add").click(function(){
		var id = $("#id").val();
		var name = $("#name").val();
		$("#parentId").val(id);
		$("#parentName").val(name);
		$("#order_").val(1);
		$("#url").val('');
		$("#name").val('');
		$("#id").val('');
		$("#icon").html("&#xe614;");
		$("#add").css("display","none");
		$("#del").css("display","none");
	});
	
	$("#del").click(function(){
		if(nodes.children.length > 0 ){
			layer.open({
				type: 1, 
				content: '<div style="text-align: center;width: 300px;padding-top: 20px;">确定删除此节点以及所有子节点？</div>',
				btn : [ '确定', '取消' ],
				yes : function(index, layero) {
					layer.close(index);
					var index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
					var id = $("#id").val();
					$.ajax({ 
						url: "/view/system/delMenu",
						data:{
							"mid":id
						},
						async:false,
						success: function(data){
							layer.close(index);
							if(data==0){
								layer.msg("删除成功");
								queryMenu();
							}else{
								layer.msg("删除失败");
							}
						}
				    });
				}
			});
		}else{
			layer.open({
				type: 1, 
				content: '<div style="padding: 20px 80px;">确定删除此节点？</div>',
				btn : [ '确定', '取消' ],
				yes : function(index, layero) {
					layer.close(index);
					var index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
					var id = $("#id").val();
					$.ajax({ 
						url: "/view/system/delMenu",
						data:{
							"mid":id
						},
						async:false,
						success: function(data){
							layer.close(index);
							if(data==0){
								layer.msg("删除成功");
								queryMenu();
							}else{
								layer.msg("删除失败");
							}
						}
				    });
				}
			});
		}
	});
	
	$("body").on("mousedown", ".layui-tree a", function() {
		$(".layui-tree a cite").css('color', '#000000');
		$(this).find("cite").css('color', '#179361');
	});
});

function setIcon(code){
	$("#icon").html(code);
}

function close(){
	layer.close(index);
}