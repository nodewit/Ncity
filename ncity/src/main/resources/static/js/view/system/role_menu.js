var form;
var layer;
var index;
var xtree;
var nodeid;
layui.use([ 'form', 'tree','layer' ], function() {
	form = layui.form;
	layer = layui.layer;
	
	function roleTree(){
		$.ajax({ 
			url: "/view/system/queryRoleTree",
			async:false,
			dataType:"json",
			success: function(data){
				$("#tree").empty();
				$("#save").css("display","none");
				$("#xtree").empty();
				layui.tree({
					elem : '#tree',
					nodes : data,
					click: function(node){
						if(node.id != 0){
							$("#save").css("display","");
							$("#xtree").empty();
							menuTree(node.id);
						}else{
							$("#save").css("display","none");
							$("#xtree").empty();
						}
					}
				});
			}
		});
	}
	
	function menuTree(id){
		$.ajax({ 
			url: "/view/system/queryMenuRole",
			async:false,
			data:{
				"rid":id
			},
			dataType:"json",
			success: function(data){
				xtree = new layuiXtree({
				      elem: 'xtree', 
				      form: form,
				      data: data    
				});
				nodeid = id;
			}
		});
	}
	
	roleTree();
	
	form.on('submit(*)', function(data){
		var checkeds = xtree.GetChecked();
		var menuids = "";
		var parents = [];
		for(var i=0;i<checkeds.length;i++){
			var parent = xtree.GetParent(checkeds[i].value);
			if(parent != null){
				var val = $(parent).val();
				parents.push(val);
				menuids += checkeds[i].value+",";
			}
		}
		parents = unique(parents);
		var top = [];
		for(var i=0;i<parents.length;i++){
			var parent = xtree.GetParent(parents[i]);
			if(parent != null){
				var val = $(parent).val();
				top.push(val);
				menuids += parents[i] +",";
			}
		}
		top = unique(top);
		for(var i=0;i<top.length;i++){
			var parent = xtree.GetParent(top[i]);
			if(parent != null){
				var val = $(parent).val();
				top.push(val);
				menuids += top[i] +",";
			}
		}
		var index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
		$.ajax({ 
			url: "/view/system/saveMenuRole",
			async:false,
			data:{
				"menuids":menuids,
				"rid":nodeid
			},
			success: function(data){
				layer.close(index);
				if(data==0){
					layer.msg("保存成功");
					roleTree();
				}else{
					layer.msg("保存失败");
				}
			}
		});
		return false;
	});
	
	$("body").on("mousedown", ".layui-tree a", function() {
		$(".layui-tree a cite").css('color', '#000000');
		$(this).find("cite").css('color', '#179361');
	});
	
});

function unique(arr) {
	var result = [];
	for (var i = 0;i < arr.length; i++) {
		if(result.length == 0){
			result.push(arr[i]);
		}
		for (var j = 0, len = result.length; j < len; j++) {
			if (arr[i] != result[j] && j==(result.length-1)) { 
				result.push(arr[i]);
			}
		}
	}
	console.log(result);
	return result;
}