<%--
  Created by IntelliJ IDEA.
  User: Miao
  Date: 2026/9/11
  Time: 08:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<title>类目列表</title>
	<meta charset="utf-8"/>
	<link rel="stylesheet" href="css/bootstrap.css"/>
	<link rel="stylesheet" href="../css/font.css">
	<link rel="stylesheet" href="../css/xadmin.css">
	<script type="text/javascript" src="../js/jquery1.12.4.js"></script>
	<script type="text/javascript" src="../lib/layui/layui.js" charset="utf-8"></script>
	<script type="text/javascript" src="../js/xadmin.js"></script>
	<!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
	<!--[if lt IE 9]>
	<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
	<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>
<body>
<div class="container-fluid">
	<%@include file="nav.jsp"%>
	<br>
	<div class="form-inline">
		<input type="text" class="form-control" id="input_name" name="name" placeholder="输入类目名称" required="required" style="width: 500px">
		<button type="button" class="btn btn-warning" id="addBtn">添加类目</button>
	</div>
	<br/>
	<br>
	<!--msg不为空则显示-->
	<c:if test="${not empty msg}">
		<div class="alert alert-success">${msg}</div>
	</c:if>
	<table class="table table-bordered table-hover">

		<tr>
			<th width="5%">ID</th>
			<th width="10%">名称</th>
			<th width="10%">操作</th>
		</tr>
		<c:forEach items="${typeList}" var="type">
		<tr>
			<td>
				<p>${type.id}</p>
			</td>
			<td>
				<p>${type.name}</p>
			</td>
			<td>
				<a class="btn btn-primary" href="type_edit.jsp?id=${type.id}&name=${type.name}">修改</a>
				<button type="button" class="btn btn-danger deleteBtn" data-id="${type.id}">删除</button>
			</td>
		</tr>
		</c:forEach>
	</table>
</div>
</body>
</html>
<script>
	layui.use(['layer'], function() {
		var layer = layui.layer

		// 3. 监听表单提交（异步更新）
		$("#addBtn").click(function(e) {
			e.preventDefault() // 阻止默认行为，避免页面跳转
			var name = $("#input_name").val().trim()
			if (!name) {
				layer.msg("类目名称不能为空", {icon: 2})
				return false
			}
			console.log("提交类目的数据：", name)

			$.ajax({
				url: './type_add',
				type: 'POST',
				data: { name: name },
				dataType: 'json',
				success: function(res) {
					if (res.code === 0 || res.success) {
						layer.alert("添加成功！", {
							icon: 6
						}, function(index) {
							layer.close(index)
							location.reload() // 💡 确定后自动刷新页面，实时渲染出新类目
						})
					} else {
						layer.msg(res.msg || "操作失败", { icon: 2 })
						// layer.msg(res.msg,{ icon: 2 })
					}
				},
				error: function(e) {
					layer.msg("请求后端接口失败", { icon: 2 })
				}
			})
			return false
		})
		// 💡 2. 异步删除分类逻辑（新增）
		$(document).on('click', '.deleteBtn', function(e) {
			e.preventDefault() // 阻止默认行为，避免页面跳转
			var id = $(this).attr("data-id")

			// 使用 Layer 弹出确认框
			layer.confirm('确定要删除这个分类吗？', { icon: 3, title: '提示' }, function(index) {
				layer.close(index)

				$.ajax({
					url: './type_delete',
					type: 'POST', // 对应 AdminTypeDelServlet 的 doPost
					data: { id: id },
					dataType: 'json',
					success: function(res) {
						if (res.code === 0 || res.success) {
							layer.msg("删除成功！", { icon: 6, time: 1000 }, function() {
								location.reload() // 实时刷新页面回显最新列表
							})
						} else {
							layer.msg(res.msg || "删除失败", { icon: 5 })
						}
					},
					error: function() {
						layer.msg("请求后端接口失败", { icon: 2 })
					}
				})
			})
			return false
		})
	})
</script>