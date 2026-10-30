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
<html class="x-admin-sm">
<head lang="zh-CN">
	<meta charset="UTF-8">
	<title>欢迎页面-X-admin2.2</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="../css/font.css">
	<link rel="stylesheet" href="../css/xadmin.css">
	<script type="text/javascript" src="../js/jquery1.12.4.js"></script>
	<script src="../lib/layui/layui.js" charset="utf-8"></script>
	<script type="text/javascript" src="../js/xadmin.js"></script>
	<!--[if lt IE 9]>
	<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
	<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	<link rel="stylesheet" href="../css/bootstrap.css"/>
	<style>
		/* 实时搜索联想下拉框样式 */
		.search-suggest-box {
			position: absolute;
			top: 38px;
			left: 0;
			right: 0;
			background: #fff;
			border: 1px solid #e6e6e6;
			border-radius: 2px;
			box-shadow: 0 2px 4px rgba(0,0,0,.12);
			z-index: 9999;
			display: none;
			max-height: 200px;
			overflow-y: auto;
		}
		.search-suggest-box li {
			padding: 8px 12px;
			cursor: pointer;
			font-size: 12px;
			color: #333;
			border-bottom: 1px solid #f6f6f6;
		}
		.search-suggest-box li:hover {
			background-color: #f2f2f2;
		}
		.content-iframe {
			width: 100%;
			height: calc(100vh - 80px);
			border: none;
			display: block;
		}
	</style>
</head>

<body>
<%@include file="nav.jsp"%>
<div class="x-nav">
	<span class="layui-breadcrumb">
		<a href="index.jsp">首页</a>
		<a><cite>用户管理</cite></a>
		<a><cite>用户列表</cite></a>
    </span>
	<a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" onclick="location.reload()" title="刷新">
		<i class="layui-icon layui-icon-refresh" style="line-height:30px"></i>
	</a>
</div>
<div class="layui-fluid">
	<div class="layui-row layui-col-space15">
		<div class="layui-col-md12">
			<div class="layui-card">
				<div class="layui-card-body ">
					<form class="layui-form layui-col-space5">
						<div class="layui-inline layui-show-xs-block" style="position:relative;">
							<input type="text" name="keyword" placeholder="请输入用户名/邮箱/收货人" autocomplete="off" class="layui-input">
							<ul id="searchSuggest" class="search-suggest-box"></ul>
						</div>
						<div class="layui-inline layui-show-xs-block">
							<button class="layui-btn" lay-submit="" lay-filter="search">
								<i class="layui-icon">&#xe615;</i>
							</button>
						</div>
					</form>
				</div>
				<div class="layui-card-body ">
					<table class="layui-hide" id="test" lay-filter="test"></table>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
<!-- 顶部工具栏模板 -->
<script type="text/html" id="toolbarDemo">
	<div class="layui-btn-container">
		<button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delAll">
			<i class="layui-icon">&#xe640;</i>批量删除
		</button>
		<button class="layui-btn layui-btn-sm" onclick="xadmin.open('添加用户','./member-edit.html',620,440)">
			<i class="layui-icon">&#xe654;</i>添加
		</button>
		<button class = "layui-btn layui-btn-sm" lay-event = "getCheckData" > 获取选中行数据 </button>
		<button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button >
		<button class = "layui-btn layui-btn-sm" lay-event = "isAll" > 验证是否全选</button>
	</div>
</script>

<!-- 状态列自定义模板 -->
<script type="text/html" id="statusTpl">
<!--	<span>[值: {{d.isvalidate}}]</span>-->
	{{# if(d.isvalidate == 1 || d.isvalidate === true){ }}
		<span class="layui-btn layui-btn-normal layui-btn-mini">已启用</span>
	{{# } else { }}
		<span class="layui-btn layui-btn-disabled layui-btn-mini">已停用</span>
	{{# } }}
</script>

<!-- 右侧操作列模板 -->
<script type="text/html" id="barDemo">
	{{# if(d.isvalidate == 1){ }}
		<!-- 当前是启用状态，点击后是要执行“停用” -->
		<a onclick="member_stop(this,'{{d.id}}')" href="javascript:;" title="停用" style="margin-right: 10px;">
			<i class="layui-icon">&#xe601;</i>
		</a>
	{{# } else { }}
		<a onclick="member_stop(this,'{{d.id}}')" href="javascript:;" title="启用" style="margin-right: 10px;">
			<i class="layui-icon">&#xe62f;</i>
		</a>
	{{# } }}
	<a title="编辑" onclick="xadmin.open('编辑','member-edit.html?id={{d.id}}',600,400)" href="javascript:;" style="margin-right: 10px;">
		<i class="layui-icon">&#xe642;</i>
	</a>
	<a title="修改密码" onclick="xadmin.open('重置密码','member-password.html?id={{d.id}}',600,400)" href="javascript:;" style="margin-right: 10px;">
		<i class="layui-icon">&#xe631;</i>
	</a>
	<a title="删除" lay-event="del" href="javascript:;">
		<i class="layui-icon">&#xe640;</i>
	</a>
</script>
<script>
	// 全局变量：记录当前输入的搜索关键字
	var currentKeyword = ''

	// 文本高亮处理函数
	function highlightText(text, keyword) {
		if (!text) return ''
		if (!keyword) return text
		// 转义正则特殊字符，避免报错
		var escapedKeyword = keyword.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&')
		var reg = new RegExp('(' + escapedKeyword + ')', 'gi')
		// 替换为带有高亮样式（橙红色文字、浅橙背景）的 span 标签
		return String(text).replace(reg, '<span style="color: #FF5722; font-weight: bold; background-color: #fff3e0; padding: 0 2px; border-radius: 2px;">$1</span>')
	}

	// 全局 AJAX 拦截器：统一处理后端返回的 401 未登录状态
	$.ajaxSetup({
		complete: function(xhr, textStatus) {
			try {
				var res = JSON.parse(xhr.responseText)
				if (res.code === 401) {
					layer.msg(res.msg, {icon: 2, time: 1500}, function() {
						window.location.href = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 1)) +'/user_login.jsp'
					})
				}
			} catch (e) {
				// 忽略非 JSON 响应
			}
		}
	})

	/*用户-停用*/
	function member_stop(obj,id){
		var title = $(obj).attr('title'),   // 获取当前按钮的 title ("停用" 或 "启用")
			// 如果当前按钮显示“停用”，说明当前用户是“启用”状态，点击后我们要把它改成停用(0)
			// 如果当前按钮显示“启用”，说明当前用户是“停用”状态，点击后我们要把它改成启用(1)
			targetValidate = (title === '停用') ? 0 : 1,
			actionText = (title === '停用') ? '停用' : '启用'
		layer.confirm('确认要' + title + '吗？',function(index){
			$.ajax({
				url: './user_update_validate',
				type: 'post',
				data: {
					id: id,
					isvalidate: targetValidate
				},
				dataType: 'json',
				success: function(res) {
					if (res.code === 0) {
						layer.msg(res.msg || (actionText + '成功！'), {icon: 1, time: 1000}, function() {
							if (title === '启用') {
								$(obj).attr('title','停用')
								$(obj).find('i').html('&#xe62f;')
								// 改变表格对应状态列显示
								$(obj).parents("tr").find(".td-status").find('span').removeClass('layui-btn-disabled').html('已启用')
								layer.msg('已启用!',{icon: 6,time:1000})
							} else {
								$(obj).attr('title','启用')
								$(obj).find('i').html('&#xe601;')
								$(obj).parents("tr").find(".td-status").find('span').addClass('layui-btn-disabled').html('已停用')
								layer.msg('已停用!',{icon: 5,time:1000})
							}
							// 核心：操作成功后直接重新加载表格，让 Layui 依据最新状态自动渲染图标和文字
							layui.table.reload('test')
						})

					} else {
						layer.msg(res.msg || "操作失败",{icon:2})
					}
				},error: function(xhr,status,error) {
					console.error("AJAX 错误状态: " + status, "错误信息: " + error, "响应内容: " + xhr.responseText)
					layer.msg("请求状态更新接口失败：" + (xhr.status === 404 ? "接口不存在(404)" : "服务器内部错误(500)"), {icon: 2})
				}
			})
		})
	}

	layui.use(['laydate', 'form', 'table'], function() {
		var form = layui.form
		var table = layui.table

		// 1. 初始化渲染表格
		table.render({
			elem: '#test',
			url: './user_list',
			toolbar: '#toolbarDemo',
			id: 'test',
			page: true,
			parseData: function (res) {
				return {
					"code": res.code,
					"msg": res.msg,
					"count": res.count || res.total,
					"data": res.data || res.list
				}
			},
			cols: [[
				{type: 'checkbox', fixed: 'left'},
				{field: 'id', width: 80, sort: true, title: 'ID'},
				{field: 'username', width: 120, sort: true, title: '用户名', templet: function (d) {return highlightText(d.username, currentKeyword)}},
				{field: 'email', width: 150, title: '邮箱', templet: function (d) {return highlightText(d.email, currentKeyword)}},
				{field: 'name', width: 100, title: '收货人', templet: function (d) {return highlightText(d.name, currentKeyword)}},
				{field: 'phone', width: 130, title: '手机', templet: function (d) {return highlightText(d.phone, currentKeyword)}},
				{field: 'address', width: 200, title: '地址', templet: function (d) {return highlightText(d.address, currentKeyword)}},
				{field: 'isvalidate', width: 120, templet: '#statusTpl', title: '状态'},
				{fixed: 'right', width: 180, align: 'center', toolbar: '#barDemo', title: '操作'}
			]],done: function(res) {
				if (res.code === 401) {
					layer.msg(res.msg, {icon: 2, time: 1500}, function() {
						window.location.href = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 1)) +'/user_login.jsp'
					})
				}
			}
		})

		// 1. 头工具栏事件（包含批量删除、获取选中行等）
		table.on('toolbar(test)', function(obj) {
			var checkStatus = table.checkStatus(obj.config.id)
			switch (obj.event) {
				case 'delAll':
					var data = checkStatus.data
					if (data.length === 0) {
						layer.msg("请选择要删除的数据", {icon: 2})
						return
					}
					var ids = []
					for (var i = 0; i < data.length; i++) {
						ids.push(data[i].id)
					}
					layer.confirm('确认要删除选中的 ' + data.length + " 行吗？", function(index) {
						$.ajax({
							url: './user_batch_delete',
							type: 'post',
							data: { ids: ids.join(",") },
							dataType: 'json',
							success: function(res) {
								if (res.code === 0) {
									layer.msg('删除成功', {icon: 1, time: 1000}, function() {
										table.reload('test')
									})
								} else {
									layer.msg(res.msg || "批量删除失败", {icon: 2})
								}
							},
							error: function() {
								layer.msg("请求批量删除接口失败", {icon: 2})
							}
						})
					})
					break
				case 'getCheckData':
					var data = checkStatus.data
					layer.alert(JSON.stringify(data))
					break
				case 'getCheckLength':
					var data = checkStatus.data
					layer.msg('选中了：' + data.length + ' 个')
					break
				case 'isAll':
					layer.msg(checkStatus.isAll ? '全选' : '未全选')
					break
			}
		})

		var searchTimer = null
		$('input[name="keyword"]').on('input', function() {
			var val = $(this).val().trim()
			var $suggestBox = $('#searchSuggest')

			if (!val) {
				$suggestBox.hide().empty()
				return
			}

			// 防抖处理
			clearTimeout(searchTimer)
			searchTimer = setTimeout(function() {
				$.ajax({
					url: './user_list',
					type: 'get',
					data: {keyword: val},
					dataType: 'json',
					success: function(res) {
						var list = res.data || res.list || (Array.isArray(res) ? res : [])
						if (list.length > 0) {
							var html = ''
							var maxShow = Math.min(list.length,5)
							for (var i = 0; i < maxShow; i++) {
								var item = list[i]
								var displayText = item.username || item.email || item.name || item.address || item.phone || ''
								if (displayText) {
									var highlightedItem = highlightText(displayText,val)
									html += '<li data-val="' + displayText + '">' + highlightedItem + '</li>'
								}
							}
							if (html) {
								$suggestBox.html(html).show()
							} else {
								$suggestBox.hide().empty()
							}
						} else {
							$suggestBox.hide().empty()
						}
					},error: function() {
						$suggestBox.hide().empty()
					}
				})
			},0)
		})

		// 点击下拉提示线时自动填入并触发搜索
		$(document).on('click','#searchSuggest li', function() {
			var selectedVal = $(this).attr('data-val')
			$('input[name="keyword"]').val(selectedVal)
			$('#searchSuggest li').hide()

			// 触发搜索
			currentKeyword = selectedVal
			table.reload('test',{
				url: './user_list',
				where: {keyword: currentKeyword},
				page: {curr: 1}
			})
		})

		// 点击页面其它地方隐藏下拉提示框
		$(document).on('click', function(e) {
			if (!$(e.target).closest('input[name="keyword"],#searchSuggest').length) {
				$('#searchSuggest').hide()
			}
		})

		// 2. 监听搜索表单提交
		$(document).on('click', 'button[lay-filter="search"]', function() {
			currentKeyword = $('input[name="keyword"]').val() ? $('input[name="keyword"]').val().trim() : ''
			table.reload('test', {
				url: './user_list', // 明确指定请求接口
				where: {
					keyword: currentKeyword // 将关键字传给后端
				},
				page: {
					curr: 1 // 搜索后重置从第一页开始（若有分页）
				}
			})
			return false // 阻止表单默认提交跳转
		})

		// 3. 监听行工具事件（单行删除）
		table.on('tool(test)', function(obj) {
			var data = obj.data
			if (obj.event === 'del') {
				layer.confirm('确认要删除此用户吗？', function(index) {
					$.ajax({
						url: './user_delete',
						type: 'post',
						data: { id: data.id },
						dataType: 'json',
						success: function(res) {
							if (res.code === 0) {
								obj.del()
								layer.msg('已删除!', {icon: 1, time: 1000})
							} else {
								layer.msg(res.msg || "删除失败", {icon: 2})
							}
						},
						error: function() {
							layer.msg("请求删除接口失败", {icon: 2})
						}
					})
				})
			}
		})

		// 4. 监听单元格编辑
		table.on('edit(test)', function(obj) {
			var value = obj.value,
				data = obj.data,
				field = obj.field
			layer.msg('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value)
		})
	})
</script>
</html>