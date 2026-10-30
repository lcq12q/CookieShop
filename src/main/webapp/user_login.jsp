<%--
  Created by IntelliJ IDEA.
  User: Miao
  Date: 2026/10/13
  Time: 09:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<title>用户登录</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/simpleCart.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<link rel="stylesheet" href="layer/theme/default/layer.css" type="text/css">
</head>
<body>
	<!--header-->
<!--header-->
	<%@include file="head.jsp"%>
<!--//header-->
	<!--//header-->
	<!--account-->
	<div class="account">
		<div class="container">
			<div class="register">
				<%
					// 如果request中存在attribute名为msg的数据，则输出div中的数据
					String msg = (String) request.getAttribute("msg");
					if (msg != null && !msg.isEmpty()) {
				%>
				<div class="alert alert-danger"><%=msg%></div>
				<%
					}
				%>
				<form action="user_login" method="post">
					<div class="register-top-grid">
						<h3>用户登录</h3>
						<div class="input">
							<span>用户名/邮箱 <label style="color:red;">*</label></span>
							<input type="text" name="ue" placeholder="请输入用户名" value="admin" required="required">
						</div>
						<div class="input">
							<span>密码 <label style="color:red;">*</label></span>
							<input type="password" name="password" placeholder="请输入密码"  value="admin"  required="required">
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="register-but text-center">
						<input type="submit" value="提交">
						<div class="clearfix"> </div>
					</div>
				</form>
				<div class="clearfix"> </div>
			</div>
	    </div>
	</div>
	<!--//account-->
	<!--footer-->
<!--footer-->
	<%@include file="foot.jsp"%>
<!--//footer-->
	<!--//footer-->
</body>
</html>
<script type="application/javascript" language="JavaScript">
	$(document).ready(function () {
		// 监听登录表单提交事件
		$("form").on("submit", function (e) {
			e.preventDefault()  // 注释表单默认刷新跳转
			// 序列化表单数据
			const form = $(this).serialize()
			// 开启layer加载层
			const lodingIndex = layer.load(2)
			// 发送ajax异步请求
			$.ajax({
				url:"user_login",
				type:"POST",
				data:form,
				dataType:"json",
				success:function(res){
					// 关闭加载层
					layer.close(lodingIndex)
					// 将返回结果转为数字状态码
					if (res.code === 1){
						layer.msg(res.msg,{
							icon: 1,    // 成功图标
							time: 1500
						}, function () {
							// if (res.isAdmin) {
							// 	window.location.href = 'admin/index.jsp'
							// } else {
							// 	window.location.href = 'index.jsp'
							// }
							window.location.href = 'index.jsp'
						})
					} else if (res.code ===  -1){
						layer.msg("账号或密码不能为空",{
							icon: 2,    // 错误失败图标
							time: 2000
						})
					} else {
						layer.msg(res.msg || "账号或密码错误",{
							icon: 2,    // 错误失败图标
							time: 2000
						})
					}
				},error:function(){
					layer.close(lodingIndex)
					layer.msg("网络异常，请稍后重试",{
						icon: 2,
						time: 2000
					})
				}
			})
		})
	})
</script>