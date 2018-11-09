var form;
var layer;
var index;
var nodes;
layui.use([ 'form', 'tree','layer' ], function() {
	form = layui.form;
	layer = layui.layer;
	
	
	function queryOrg(){
		$.ajax({ 
			url: "/view/system/queryOrg",
			async:false,
			dataType:"json",
			success: function(data){
				$("#tree").empty();
				$("#parentOrgId").val('');
				$("#parentOrgName").val('');
				$("#order_").val(1);
				$("#orgPath").val('');
				$("#orgName").val('');
				$("#orgId").val('');
				$("#orgShortName").val('');
				layui.tree({
					elem : '#tree',
					nodes : data,
					click: function(node){
						nodes = node;
						console.log(node);
					    if(node.level == 0){
					    	$("#add").css("display","");
					    	$("#save").css("display","");
					    	$("#del").css("display","none");
					    }else{
					    	$("#add").css("display","");
					    	$("#save").css("display","");
					    	$("#del").css("display","");
					    }
					    if(node.id != '001'){
					    	$.ajax({ 
								url: "/view/system/queryOrgByCode",
								data:{"orgCode":node.id},
								async:false,
								dataType:"json",
								success: function(data){
									$("#parentOrgId").val(data.parent_org_code);
									$("#parentOrgName").val(data.parentName);
									$("#order_").val(data.order_);
									$("#orgPath").val(data.org_path);
									$("#orgName").val(data.org_name);
									$("#orgId").val(data.org_code);
									$("#orgShortName").val(data.short_name);
								}
						    });
					    }else{
					    	$("#parentOrgId").val('');
							$("#parentOrgName").val('');
							$("#order_").val(1);
							$("#orgPath").val(node.orgPath);
							$("#orgName").val(node.name);
							$("#orgId").val(node.id);
							$("#orgShortName").val(node.orgShortName);
					    }
					}
				});
			}
		});
	}
	
	queryOrg();
	
	form.on('submit(*)', function(data){
		var index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
	    var json = data.field;
	    var url = "/view/system/updateOrg";
	    if(json['orgId'] == null || json['orgId'] == ""){
	    	url = "/view/system/addOrg";
	    }
	    $.ajax({ 
			url: url,
			data:json,
			async:false,
			success: function(data){
				layer.close(index);
				if(data==0){
					layer.msg("保存成功");
					queryOrg();
				}else{
					layer.msg("保存失败");
				}
			}
	    });
	    return false;
	});
	
	
	$("#add").click(function(){
		var id = $("#orgId").val();
		var name = $("#orgName").val();
		$("#parentOrgId").val(id);
		$("#parentOrgName").val(name);
		$("#order_").val(1);
		$("#orgPath").val('');
		$("#orgName").val('');
		$("#orgId").val('');
		$("#orgShortName").val('');
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
					var id = $("#orgId").val();
					$.ajax({ 
						url: "/view/system/delOrg",
						data:{
							"orgCode":id
						},
						async:false,
						success: function(data){
							layer.close(index);
							if(data==0){
								layer.msg("删除成功");
								queryOrg();
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
					var id = $("#orgId").val();
					$.ajax({ 
						url: "/view/system/delMenu",
						data:{
							"orgCode":id
						},
						async:false,
						success: function(data){
							layer.close(index);
							if(data==0){
								layer.msg("删除成功");
								queryOrg();
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
