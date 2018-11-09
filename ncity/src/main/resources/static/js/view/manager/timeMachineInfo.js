setTimeout(function() {
	var height = $(document.body).height();
	$("#img").height(height - 135);
	$("#docker").height(height);
}, 100);

$(function() {
	var id = $("#id").val();
	$.ajax({
		url : "/view/manager/queryChainById?id=" + id,
		dataType : "json",
		success : function(data) {
			var description = data.data.data.description;
			var files = data.data.data.files;
			var html = "";
			for (i = 0; i < files.length; i++) {
				html += '<div class="layui-col-md4 img">' +
					'<img src="' + files[i].filePath + '" onclick="openImg(this)">' +
					'</div>';
			}
			$("#desc").html(description);
			$("#img").html(html);
		}
	});
})

function openImg(obj) {
	$("#photo").attr("src", $(obj).attr("src"));
	$("#photo").css("display", "");
	$("#span").css("display", "none");
}