<%--
  Created by IntelliJ IDEA.
  User: Miao
  Date: 2026/10/13
  Time: 09:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>用户注册</title>
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
				<form action="user_register" method="post">
					<div class="register-top-grid">
						<h3>注册新用户</h3>
						<div class="input">
							<span>用户名 <label style="color:red;">*</label></span>
							<input type="text" name="username" placeholder="请输入用户名" required="required">
						</div>
						<div class="input">
							<span>邮箱 <label style="color:red;">*</label></span>
							<input type="text" name="email" placeholder="请输入邮箱" required="required">
						</div>
						<div class="input">
							<span>密码 <label style="color:red;">*</label></span>
							<input type="password" name="password" placeholder="请输入密码" required="required">
						</div>
						<div class="input">
							<span>收货人<label></label></span>
							<input type="text" name="name" placeholder="请输入收货">
						</div>
						<div class="input">
							<span>收货电话<label></label></span>
							<input type="text" name="phone" placeholder="请输入收货电话">
						</div>
						<div class="input">
							<span>收货地址<label></label></span>
							<input type="text" name="address" placeholder="请输入收货地址">
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
<script type="text/javascript">
	$(document).ready(function() {
		// 监听表单提交事件
		$("form").on("submit", function(e) {
			e.preventDefault()  // 组织表单默认的刷新跳转

			// 序列化表单数据
			const form = $(this).serialize()
			// 开启layer加载层
			const lodingIndex = layer.load(2)
			// 发送ajax异步请求
			$.ajax({
				url:"user_register",
				type:"POST",
				data:form,
				success:function(res){
					// 关闭加载层
					layer.close(lodingIndex)
					// 将后端返回的文本转为数字状态码
					const code = Number(res.trim())

					if (code == 1){
						layer.msg("注册成功",{
							icon: 1,    // 成功图标
							time: 1500
						}, function () {
							window.location.href = 'user_login.jsp'
						})
					} else if (code == -1){
						layer.msg("用户名或邮箱已存在",{
							icon: 2,    // 错误失败图标
							time: 2000
						})
					} else if (code == -2){
						layer.msg("必填项不能为空",{
							icon: 2,    // 错误失败图标
							time: 2000
						})
					} else {
						layer.msg("注册失败，请稍后重试!",{
							icon: 2,
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